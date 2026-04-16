package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.entity.Retweet;

import java.util.Optional;

public interface RetweetService {
    Optional<Retweet> toggleRetweet(Long tweetId, String userEmail);
    long getRetweetCount(Long tweetId);
}