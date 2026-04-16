package com.hbilici.twitter_api.controller;

import com.hbilici.twitter_api.dto.request.TweetRequest;
import com.hbilici.twitter_api.dto.response.TweetResponse;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.repository.UserRepository;
import com.hbilici.twitter_api.service.TweetService;
import com.hbilici.twitter_api.util.TweetMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private  TweetService tweetService;

    @PostMapping
    public ResponseEntity<TweetResponse> save(@RequestBody TweetRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetService.save(request, email));
    }

    @GetMapping("/{id}")
    public TweetResponse findById(@PathVariable Long id) {
        return tweetService.findById(id);
    }

    @GetMapping("/findByUserId/{userId}")
    public List<TweetResponse> findByUserId(@PathVariable Long userId) {
        return tweetService.findByUserId(userId);
    }

    @PutMapping("/{id}")
    public TweetResponse update(@PathVariable Long id, @RequestBody TweetRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return tweetService.update(id, request, email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        tweetService.delete(id, email);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public List<TweetResponse> findAll() {
        return tweetService.findAll();
    }
}