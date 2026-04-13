package com.hbilici.twitter_api.repository;

import com.hbilici.twitter_api.entity.Retweet;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetweetRepository  extends JpaRepository<Retweet,Long> {

    boolean existsByUserAndTweet(User user, Tweet tweet);
    Optional<Retweet> findByUserAndTweet(User user, Tweet tweet);
    long countByTweet(Tweet tweet);
}
