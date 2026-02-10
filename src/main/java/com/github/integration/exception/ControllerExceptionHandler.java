package com.github.integration.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@ResponseBody
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(GithubException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleEntityNotFoundException(GithubException e) {

        log.error(e.getMessage());
        return ErrorResponse.builder()
                .message(e.getMessage())
                .code(e.getErrorCode())
                .hint(e.getHint())
                .build();
    }
}
