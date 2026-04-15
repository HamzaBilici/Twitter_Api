package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.entity.Comment;
import java.util.Set;

public interface CommentService {
    Comment save(Long tweetId, String userEmail, String content);
    void delete(Long commentId, String userEmail);
    Set<Comment> findByTweet(Long tweetId);
}