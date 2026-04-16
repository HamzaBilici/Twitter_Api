package com.hbilici.twitter_api.controller;

import com.hbilici.twitter_api.dto.request.CommentRequest;
import com.hbilici.twitter_api.dto.response.CommentResponse;
import com.hbilici.twitter_api.entity.Comment;
import com.hbilici.twitter_api.service.CommentService;
import com.hbilici.twitter_api.util.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private  CommentService commentService;


    @PostMapping("/{tweetId}")
    public CommentResponse save(@PathVariable Long tweetId, @RequestBody CommentRequest request) {
        String activeUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment savedComment = commentService.save(tweetId, activeUserEmail, request.text());
        return CommentMapper.toResponse(savedComment);
    }

    @GetMapping("/{tweetId}")
    public List<CommentResponse> findByTweet(@PathVariable Long tweetId) {
        return commentService.findByTweet(tweetId).stream()
                .map(CommentMapper::toResponse)
                .toList();
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long commentId) {
        String activeUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.delete(commentId, activeUserEmail);
    }
}