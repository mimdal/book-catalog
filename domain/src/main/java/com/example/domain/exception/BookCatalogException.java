package com.example.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookCatalogException extends RuntimeException {

    private HttpStatus status;

    public BookCatalogException(String message, int statusCode) {
        super(message);
        this.status = HttpStatus.valueOf(statusCode);
    }

    public BookCatalogException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.valueOf(statusCode);
    }

}