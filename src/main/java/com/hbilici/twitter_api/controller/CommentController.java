package com.hbilici.twitter_api.controller;

import com.hbilici.twitter_api.dto.request.CommentRequest;
import com.hbilici.twitter_api.dto.response.CommentResponse;
import com.hbilici.twitter_api.entity.Comment;
import com.hbilici.twitter_api.service.CommentService;
import com.hbilici.twitter_api.util.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{tweetId}")
    public ResponseEntity<CommentResponse> save(
            @PathVariable Long tweetId,
            @RequestBody CommentRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.save(tweetId, email, request));
    }

    @GetMapping("/tweet/{tweetId}")
    public List<CommentResponse> findByTweet(@PathVariable Long tweetId) {
        return commentService.findByTweet(tweetId);
    }

    @GetMapping("/{commentId}")
    public CommentResponse findById(@PathVariable Long commentId) {
        return commentService.findById(commentId);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.delete(commentId, email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable Long id,
            @RequestBody CommentRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(commentService.update(id, email, request));
    }
}