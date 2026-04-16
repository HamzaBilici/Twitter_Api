package com.hbilici.twitter_api.repository;

import com.hbilici.twitter_api.entity.Comment;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Set<Comment> findByTweetOrderByDateDesc(Tweet tweet);
    Set<Comment> findByUserOrderByDateDesc(User user);
    List<Comment> findByTweetId(Long tweetId);

}
