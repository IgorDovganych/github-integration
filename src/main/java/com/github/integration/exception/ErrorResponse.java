package com.github.integration.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

    private final String message;
    private final String code;
    private final String hint;

    @Builder
    public ErrorResponse(String message, String code, String hint) {
        this.message = message;
        this.code = code;
        this.hint = hint;
    }
}
