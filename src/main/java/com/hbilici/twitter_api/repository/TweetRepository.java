package com.hbilici.twitter_api.repository;

import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TweetRepository extends JpaRepository<Tweet,Long> {

    List<Tweet> findAllByUserId(Long userId);
}
