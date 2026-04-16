package com.hbilici.twitter_api.controller;

import com.hbilici.twitter_api.dto.response.RetweetResponse;
import com.hbilici.twitter_api.dto.response.TweetResponse;
import com.hbilici.twitter_api.entity.Retweet;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.service.RetweetService;
import com.hbilici.twitter_api.service.TweetService;
import com.hbilici.twitter_api.util.RetweetMapper;
import com.hbilici.twitter_api.util.TweetMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/retweet")
public class RetweetController {

    @Autowired
    private  RetweetService retweetService;
    @Autowired
    private  TweetService tweetService;

    @PostMapping("/{tweetId}")
    public ResponseEntity<RetweetResponse> toggleRetweet(@PathVariable Long tweetId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return retweetService.toggleRetweet(tweetId, email)
                .map(retweet -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(RetweetMapper.toResponse(retweet)))
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/count/{tweetId}")
    public long getRetweetCount(@PathVariable Long tweetId) {
        return retweetService.getRetweetCount(tweetId);
    }
}