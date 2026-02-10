package com.github.integration.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GithubException extends RuntimeException{
    private String message;
    private String errorCode;
    private String hint;
    
}
