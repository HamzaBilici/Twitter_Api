package com.hbilici.twitter_api.controller;

import com.hbilici.twitter_api.dto.request.TweetRequest;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.repository.UserRepository;
import com.hbilici.twitter_api.service.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
@AllArgsConstructor
public class TweetController {

    private final TweetService tweetService;
    private final UserRepository userRepository; // Şimdilik basitçe user'ı bulmak için

    @PostMapping
    public Tweet save(@RequestBody TweetRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tweet tweet = new Tweet();
        tweet.setContent(request.content());
        tweet.setUser(user);

        return tweetService.save(tweet);
    }

    @GetMapping
    public List<Tweet> findAll() {
        return tweetService.findAll();
    }
}