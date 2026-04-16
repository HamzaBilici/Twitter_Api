package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.entity.Like;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.repository.LikeRepository;
import com.hbilici.twitter_api.repository.TweetRepository;
import com.hbilici.twitter_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Override
    @Transactional
    public Optional<Like> toggleLike(Long tweetId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı"));

        Optional<Like> existingLike = likeRepository.findByUserAndTweet(user, tweet);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return Optional.empty();
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setTweet(tweet);
            return Optional.ofNullable(likeRepository.save(like));
        }
    }

    @Override
    public long getLikeCount(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));
        return likeRepository.countByTweet(tweet);
    }
}