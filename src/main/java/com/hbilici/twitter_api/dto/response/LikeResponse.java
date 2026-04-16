package com.hbilici.twitter_api.dto.response;

import java.time.LocalDateTime;

public record LikeResponse(
        Long id,
        LocalDateTime date,
        UserResponse user,
        TweetResponse tweet
) {}