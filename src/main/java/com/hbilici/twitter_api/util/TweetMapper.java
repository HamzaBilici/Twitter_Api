package com.hbilici.twitter_api.util;

import com.hbilici.twitter_api.dto.response.TweetResponse;
import com.hbilici.twitter_api.dto.response.UserResponse;
import com.hbilici.twitter_api.entity.Tweet;

public class TweetMapper {

    public static TweetResponse toResponse(Tweet tweet) {
        return new TweetResponse(
                tweet.getId(),
                tweet.getContent(),
                tweet.getDate(),
                UserMapper.toUserResponse(tweet.getUser()),
                tweet.getLikes().size(),
                tweet.getRetweets().size(),
                tweet.getComments().size()
        );
    }
}
