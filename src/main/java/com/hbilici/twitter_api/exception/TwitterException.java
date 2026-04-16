package com.hbilici.twitter_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TwitterException extends RuntimeException {
    private  HttpStatus httpStatus;

    public TwitterException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }
}