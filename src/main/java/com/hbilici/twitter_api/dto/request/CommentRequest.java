package com.hbilici.twitter_api.dto.request;

public record CommentRequest(String text) {
    public String getText() {
        return text;
    }
}