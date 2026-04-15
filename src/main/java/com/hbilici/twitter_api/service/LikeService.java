package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.entity.Like;
import java.util.List;

public interface LikeService {
    void toggleLike(Long tweetId, String userEmail);
    long getLikeCount(Long tweetId);
}