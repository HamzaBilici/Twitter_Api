package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.entity.Like;
import java.util.List;
import java.util.Optional;

public interface LikeService {
    Optional<Like> toggleLike(Long tweetId, String userEmail);
    long getLikeCount(Long tweetId);
}