package com.github.integration.service;

import com.github.integration.model.UserDetails;

public interface GithubIntegrationService {
    UserDetails getUserDetails(String userName);
}
