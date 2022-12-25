package com.example.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookCatalogException extends RuntimeException {

    private HttpStatus status;

    public BookCatalogException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BookCatalogException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

}