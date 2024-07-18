package com.springboot.price.exception;

import org.springframework.http.HttpStatus;

public class BlogPriceException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public BlogPriceException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogPriceException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
