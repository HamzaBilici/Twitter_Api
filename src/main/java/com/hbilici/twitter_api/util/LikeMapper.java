package com.hbilici.twitter_api.util;

import com.hbilici.twitter_api.dto.response.LikeResponse;
import com.hbilici.twitter_api.entity.Like;
import com.hbilici.twitter_api.entity.User;

public class LikeMapper {

    public static LikeResponse toLikeResponse(Like like) {
        return new LikeResponse(
                like.getId(),
                like.getDate(),
                UserMapper.toUserResponse(like.getUser()),
                TweetMapper.toResponse(like.getTweet())
        );
    }
}
