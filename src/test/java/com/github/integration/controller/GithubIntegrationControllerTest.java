package com.github.integration.controller;

import com.github.integration.exception.GithubException;
import com.github.integration.model.Repository;
import com.github.integration.model.UserDetails;
import com.github.integration.service.GithubIntegrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GithubIntegrationController.class)
class GithubIntegrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubIntegrationService githubIntegrationService;

    @Test
    void getUserDetails_success() throws Exception {

        // given
        String username = "octocat";

        Repository repo = Repository.builder()
                .name("repo1")
                .url("https://github.com/octocat/repo1")
                .build();

        UserDetails userDetails = UserDetails.builder()
                .userName(username)
                .displayName("The Octocat")
                .avatar("avatar-url")
                .geoLocation("San Francisco")
                .email("octo@example.com")
                .url("https://github.com/octocat")
                .createdAt("Tue, 25 Jan 2011 18:44:36 GMT")
                .repos(List.of(repo))
                .build();

        when(githubIntegrationService.getUserDetails(username))
                .thenReturn(userDetails);


        // when / then
        mockMvc.perform(get("/api/integration/user-details")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_name").value(username))
                .andExpect(jsonPath("$.display_name").value("The Octocat"))
                .andExpect(jsonPath("$.avatar").value("avatar-url"))
                .andExpect(jsonPath("$.geo_location").value("San Francisco"))
                .andExpect(jsonPath("$.email").value("octo@example.com"))
                .andExpect(jsonPath("$.url").value("https://github.com/octocat"))
                .andExpect(jsonPath("$.created_at").value("Tue, 25 Jan 2011 18:44:36 GMT"))
                .andExpect(jsonPath("$.repos[0].name").value("repo1"))
                .andExpect(jsonPath("$.repos[0].url").value("https://github.com/octocat/repo1"));
    }


    @Test
    void getUserDetails_error_missingUsername() throws Exception {

        mockMvc.perform(get("/api/integration/user-details"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getUserDetails_error_usernameDoesntExist() throws Exception {

        String username = "username_DOES_NOT_EXIST";

        when(githubIntegrationService.getUserDetails(username))
                .thenThrow(new GithubException("User not found", "404", "Try different username"));

        mockMvc.perform(get("/api/integration/user-details")
                        .param("username", username))
                .andExpect(status().isBadRequest());
    }
}