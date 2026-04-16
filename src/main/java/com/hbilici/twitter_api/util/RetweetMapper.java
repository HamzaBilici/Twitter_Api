package com.hbilici.twitter_api.util;

import com.hbilici.twitter_api.dto.response.RetweetResponse;
import com.hbilici.twitter_api.entity.Retweet;


public class RetweetMapper {
    public static RetweetResponse toResponse(Retweet retweet) {
        return new RetweetResponse(
                retweet.getId(),
                retweet.getDate(),
                UserMapper.toUserResponse(retweet.getUser()),
                TweetMapper.toResponse(retweet.getTweet())
        );
    }
}
