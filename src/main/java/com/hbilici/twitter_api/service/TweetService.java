package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.dto.request.TweetRequest;
import com.hbilici.twitter_api.dto.response.TweetResponse;
import com.hbilici.twitter_api.entity.Tweet;
import java.util.List;

    public interface TweetService {
        TweetResponse save(TweetRequest request, String email);
        List<TweetResponse> findAll();
        TweetResponse findById(Long id);
        TweetResponse update(Long id, TweetRequest request, String email);
        void delete(Long id, String email);
        List<TweetResponse> findByUserId(Long userId);
    }
