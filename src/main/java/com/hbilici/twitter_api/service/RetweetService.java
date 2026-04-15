package com.hbilici.twitter_api.service;

public interface RetweetService {
    void toggleRetweet(Long tweetId, String userEmail);
    long getRetweetCount(Long tweetId);
}