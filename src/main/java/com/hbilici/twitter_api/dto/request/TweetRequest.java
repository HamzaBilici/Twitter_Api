package com.hbilici.twitter_api.dto.request;

public record TweetRequest(String content) {
    public String getContent() {
        return content;
    }
}