package com.hbilici.twitter_api.controller;

import com.hbilici.twitter_api.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{tweetId}")
    public void toggleLike(@PathVariable Long tweetId) {
        String activeUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        likeService.toggleLike(tweetId, activeUserEmail);
    }

    @GetMapping("/count/{tweetId}")
    public long getLikeCount(@PathVariable Long tweetId) {
        return likeService.getLikeCount(tweetId);
    }
}