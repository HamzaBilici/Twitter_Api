package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.repository.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    @Override
    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet findById(Long id) {
        return tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet not found with id: " + id));
    }

    @Override
    public void delete(Long id, String userEmail) {
        Tweet tweet = findById(id);

        if (!tweet.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("You are not authorized to delete this tweet!");
        }

        tweetRepository.delete(tweet);
    }

    @Override
    public Set<Tweet> findByUser(Long userId) {
        return null;
    }
}