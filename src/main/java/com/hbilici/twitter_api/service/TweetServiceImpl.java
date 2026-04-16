package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.dto.request.TweetRequest;
import com.hbilici.twitter_api.dto.response.TweetResponse;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.exception.BadRequestException;
import com.hbilici.twitter_api.exception.NotFoundException;
import com.hbilici.twitter_api.repository.TweetRepository;
import com.hbilici.twitter_api.repository.UserRepository;
import com.hbilici.twitter_api.util.TweetMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public TweetResponse save(TweetRequest request, String email) {
        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new BadRequestException("Tweet içeriği boş olamaz!");
        }
        if (request.getContent().length() > 280) {
            throw new BadRequestException("Tweet 280 karakterden uzun olamaz!");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı: " + email));

        Tweet tweet = new Tweet();
        tweet.setContent(request.getContent());
        tweet.setUser(user);

        Tweet savedTweet = tweetRepository.save(tweet);
        return TweetMapper.toResponse(savedTweet);
    }

    @Override
    public List<TweetResponse> findAll() {
        return tweetRepository.findAll().stream()
                .map(TweetMapper::toResponse)
                .toList();
    }

    @Override
    public TweetResponse findById(Long id) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tweet bulunamadı! ID: " + id));

        return TweetMapper.toResponse(tweet);
    }

    @Override
    @Transactional
    public TweetResponse update(Long id, TweetRequest request, String email) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tweet bulunamadı"));

        if (!tweet.getUser().getEmail().equals(email)) {
            throw new BadRequestException("Sadece kendi tweetinizi güncelleyebilirsiniz!");
        }

        tweet.setContent(request.getContent());
        return TweetMapper.toResponse(tweetRepository.save(tweet));
    }

    @Override
    @Transactional
    public void delete(Long id, String email) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tweet bulunamadı"));

        if (!tweet.getUser().getEmail().equals(email)) {
            throw new BadRequestException("Sadece kendi tweetinizi silebilirsiniz!");
        }

        tweetRepository.delete(tweet);
    }

    @Override
    public List<TweetResponse> findByUserId(Long userId) {
        List<Tweet> tweets = tweetRepository.findAllByUserId(userId);
        return tweets.stream()
                .map(TweetMapper::toResponse)
                .toList();
    }

}