package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.entity.Retweet;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.repository.RetweetRepository;
import com.hbilici.twitter_api.repository.TweetRepository;
import com.hbilici.twitter_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RetweetServiceImpl implements RetweetService {

    private final RetweetRepository retweetRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Override
    @Transactional
    public void toggleRetweet(Long tweetId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        Optional<Retweet> existingRetweet = retweetRepository.findByUserAndTweet(user, tweet);

        if (existingRetweet.isPresent()) {
            retweetRepository.delete(existingRetweet.get());
        } else {
            Retweet retweet = new Retweet();
            retweet.setUser(user);
            retweet.setTweet(tweet);
            retweetRepository.save(retweet);
        }
    }

    @Override
    public long getRetweetCount(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));
        return retweetRepository.countByTweet(tweet);
    }
}