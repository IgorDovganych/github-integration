package com.github.integration.mapper;

import com.github.integration.model.UserDetails;
import com.github.integration.response.UserDetailsResponse;

public class UserDetailsMapper {

    public static UserDetailsResponse toResponse(UserDetails userDetails) {
        if (userDetails != null) {
            return UserDetailsResponse.builder()
                    .user_name(userDetails.getUserName())
                    .display_name(userDetails.getDisplayName())
                    .avatar(userDetails.getAvatar())
                    .geo_location(userDetails.getGeoLocation())
                    .email(userDetails.getEmail())
                    .url(userDetails.getUrl())
                    .created_at(userDetails.getCreatedAt())
                    .repos(userDetails.getRepos())
                    .build();
        }
        return null;
    }
}
