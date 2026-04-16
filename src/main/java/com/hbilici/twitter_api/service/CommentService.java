package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.dto.request.CommentRequest;
import com.hbilici.twitter_api.dto.response.CommentResponse;
import com.hbilici.twitter_api.entity.Comment;

import java.util.List;
import java.util.Set;

public interface CommentService {
    CommentResponse save(Long tweetId, String email, CommentRequest request);
    List<CommentResponse> findByTweet(Long tweetId);
    void delete(Long commentId, String email);
    CommentResponse update(Long commentId, String email, CommentRequest request);
    CommentResponse findById(Long commentId);
}