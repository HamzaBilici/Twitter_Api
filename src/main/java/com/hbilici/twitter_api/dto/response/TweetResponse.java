package com.hbilici.twitter_api.dto.response;

import java.time.LocalDateTime;

public record TweetResponse(
        Long id,
        String content,
        LocalDateTime date,
        UserResponse user,
        Integer likeCount,
        Integer retweetCount,
        Integer commentCount
) {}