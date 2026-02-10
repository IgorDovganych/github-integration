package com.github.integration.response;

import com.github.integration.model.Repository;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDetailsResponse {
    private String user_name;
    private String display_name;
    private String avatar;
    private String geo_location;
    private String email;
    private String url;
    private String created_at;
    private List<Repository> repos;
}
