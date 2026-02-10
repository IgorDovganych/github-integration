package com.github.integration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Repository {
    private String name;
    private String url;
}
