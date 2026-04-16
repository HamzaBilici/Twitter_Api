package com.hbilici.twitter_api.controller;

import com.hbilici.twitter_api.dto.response.LikeResponse;
import com.hbilici.twitter_api.service.LikeService;
import com.hbilici.twitter_api.util.LikeMapper;
import com.hbilici.twitter_api.util.TweetMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{tweetId}")
    public ResponseEntity<LikeResponse> toggleLike(@PathVariable Long tweetId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return likeService.toggleLike(tweetId, email)
                .map(like -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(LikeMapper.toLikeResponse(like)))
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/count/{tweetId}")
    public long getLikeCount(@PathVariable Long tweetId) {
        return likeService.getLikeCount(tweetId);
    }
}