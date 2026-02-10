package com.github.integration.service;

import com.github.integration.exception.GithubException;
import com.github.integration.model.UserDetails;

public interface GithubIntegrationService {

    /**
     * Fetches GitHub user profile and repositories for the given username.
     *
     * @param username GitHub username
     * @return aggregated user details including profile and repositories
     * @throws GithubException if user is not found, rate limited, or GitHub returns an error
     */
    UserDetails getUserDetails(String username) throws GithubException;
}
