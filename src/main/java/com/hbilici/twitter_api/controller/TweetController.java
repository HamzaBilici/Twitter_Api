package com.hbilici.twitter_api.controller;

import com.hbilici.twitter_api.dto.request.TweetRequest;
import com.hbilici.twitter_api.dto.response.TweetResponse;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.repository.UserRepository;
import com.hbilici.twitter_api.service.TweetService;
import com.hbilici.twitter_api.util.TweetMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
@AllArgsConstructor
public class TweetController {

    private final TweetService tweetService;
    private final UserRepository userRepository; // Şimdilik basitçe user'ı bulmak için

    @PostMapping
    public TweetResponse save(@RequestBody TweetRequest tweetRequest) {
        String activeUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(activeUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequest.content());
        tweet.setUser(user);

        return TweetMapper.toResponse(tweetService.save(tweet));
    }
    @GetMapping
    public List<TweetResponse> findAll() {
        return tweetService.findAll().stream()
                .map(TweetMapper::toResponse)
                .toList();
    }
}