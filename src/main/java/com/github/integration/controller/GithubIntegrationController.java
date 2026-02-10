package com.github.integration.controller;

import com.github.integration.model.UserDetails;
import com.github.integration.response.UserDetailsResponse;
import com.github.integration.service.GithubIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.github.integration.mapper.UserDetailsMapper.toResponse;
import static com.github.integration.utils.Constants.API_BASE;

@Slf4j
@RestController
@RequestMapping(value = API_BASE, produces = MediaType.APPLICATION_JSON_VALUE)
public class GithubIntegrationController {

    private final GithubIntegrationService githubIntegrationService;

    public GithubIntegrationController(GithubIntegrationService githubIntegrationService) {
        this.githubIntegrationService = githubIntegrationService;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user details from github")
    @GetMapping(value = "/user-details")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@RequestParam String username) {
        UserDetails userDetails = githubIntegrationService.getUserDetails(username);
        return ResponseEntity.ok(toResponse(userDetails));
    }
}
