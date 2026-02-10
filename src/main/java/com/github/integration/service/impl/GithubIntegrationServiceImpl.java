package com.github.integration.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.integration.exception.GithubException;
import com.github.integration.model.Repository;
import com.github.integration.model.UserDetails;
import com.github.integration.service.GithubIntegrationService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.integration.utils.Constants.GITHUB_URL;
import static com.github.integration.utils.DateUtil.formatGithubDate;

@Service
public class GithubIntegrationServiceImpl implements GithubIntegrationService {
    private final RestClient restClient;

    private final String USER_DETAILS_URL = "/users/{username}";
    private final String REPOS_URL = "/users/{username}/repos";

    private static final String HEADER_REMAINING = "X-RateLimit-Remaining";
    private static final String HEADER_RESET = "X-RateLimit-Reset";

    public GithubIntegrationServiceImpl() {
        this.restClient = RestClient.builder()
                .baseUrl(GITHUB_URL)
                .build();
    }

    
    @Override
    public UserDetails getUserDetails(String username) {
        JsonNode userDetailsJson = getUserDetailsJson(username);
        JsonNode reposJson = getReposJson(username);
        List<Repository> repos = getRepos(reposJson);

        String displayName = userDetailsJson.get("name").asText();
        String avatar = userDetailsJson.get("avatar_url").asText();
        String geoLocation = userDetailsJson.get("location").asText();
        String email = userDetailsJson.get("email").asText();
        String url = userDetailsJson.get("url").asText();
        String createdAt = userDetailsJson.get("created_at").asText();

        return UserDetails.builder()
                .userName(username)
                .displayName(displayName)
                .avatar(avatar)
                .geoLocation(geoLocation)
                .email(email)
                .url(url)
                .createdAt(formatGithubDate(createdAt))
                .repos(repos)
                .build();
    }

    private JsonNode getUserDetailsJson(String userName) {
        return restClient.get()
                .uri(USER_DETAILS_URL, userName)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        (req, res) -> {
                            throw new GithubException("GitHub user not found: " + userName, "404", "Try different userName");
                        })
                .onStatus(status -> status.value() == 403,
                        (req, res) -> {
                            throw rateLimitOrForbidden(res);
                        })
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new GithubException("GitHub server error", "500", "something went wrong on the github side");
                        })
                .body(JsonNode.class);
    }

    private JsonNode getReposJson(String userName) {
        return restClient.get()
                .uri(REPOS_URL, userName)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        (req, res) -> {
                            throw new GithubException("Repos not found for user: " + userName, "404", "try different userName");
                        })
                .onStatus(status -> status.value() == 403,
                        (req, res) -> {
                            throw rateLimitOrForbidden(res);
                        })
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new GithubException("GitHub server error", "500", "something went wrong on the github side");
                        })
                .body(JsonNode.class);
    }

    private GithubException rateLimitOrForbidden(ClientHttpResponse response) throws IOException {

        String remaining = response.getHeaders().getFirst(HEADER_REMAINING);

        if ("0".equals(remaining)) {

            String reset = response.getHeaders().getFirst(HEADER_RESET);
            long secondsLeft = secondsLeftToReset(reset);

            return new GithubException("GitHub rate limit exceeded.", "403", "Try again in " + secondsLeft + " seconds");
        }

        return new GithubException("GitHub access forbidden", "403", "Access denied");
    }

    private long secondsLeftToReset(String resetHeader) {

        if (resetHeader == null || resetHeader.isBlank()) {
            return 0L;
        }

        long resetTime = Long.parseLong(resetHeader);
        long now = System.currentTimeMillis() / 1000;

        return Math.max(0L, resetTime - now);
    }

    private List<Repository> getRepos(JsonNode reposJson) {

        if (reposJson == null || !reposJson.isArray()) {
            return List.of();
        }

        List<Repository> repos = new ArrayList<>();

        for (JsonNode node : reposJson) {
            Repository repo = Repository.builder()
                    .name(node.get("name").asText())
                    .url(node.get("html_url").asText())
                    .build();
            repos.add(repo);
        }

        return repos;
    }

}
