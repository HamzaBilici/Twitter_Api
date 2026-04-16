package com.hbilici.twitter_api.dto.response;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        LocalDateTime date,
        UserResponse user
) {}