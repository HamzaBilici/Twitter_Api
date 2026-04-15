package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.entity.Tweet;
import java.util.List;
import java.util.Set;

public interface TweetService {
    Tweet save(Tweet tweet);
    List<Tweet> findAll();
    Tweet findById(Long id);
    void delete(Long id, String userEmail);
    Set<Tweet> findByUser(Long userId);
}