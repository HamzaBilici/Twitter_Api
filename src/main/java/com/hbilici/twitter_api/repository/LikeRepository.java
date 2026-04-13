package com.hbilici.twitter_api.repository;

import com.hbilici.twitter_api.entity.Like;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

    boolean existsByUserAndTweet(User user, Tweet tweet);
    Optional<Like> findByUserAndTweet(User user, Tweet tweet);
    long countByTweet(Tweet tweet);
}
