package com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private final Instant timestamp = Instant.now();
    private final String path;
    private final int status;
    private final String error;
    private final String message;
    private final List<String> errors;

    public ApiError(String path, int status, String error, String message, List<String> errors) {
        this.path = path;
        this.status = status;
        this.error = error;
        this.message = message;
        this.errors = errors;
    }

    public Instant getTimestamp() { return timestamp; }
    public String getPath() { return path; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public List<String> getErrors() { return errors; }
}
