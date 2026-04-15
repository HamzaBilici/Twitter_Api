package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.entity.Comment;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.repository.CommentRepository;
import com.hbilici.twitter_api.repository.TweetRepository;
import com.hbilici.twitter_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private  CommentRepository commentRepository;
    @Autowired
    private  TweetRepository tweetRepository;
    @Autowired
    private  UserRepository userRepository;

    @Override
    public Comment save(Long tweetId, String userEmail, String content) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setTweet(tweet);

        return commentRepository.save(comment);
    }

    @Override
    public void delete(Long commentId, String userEmail) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("You can only delete your own comments!");
        }

        commentRepository.delete(comment);
    }

    @Override
    public Set<Comment> findByTweet(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));
        return commentRepository.findByTweetOrderByDateDesc(tweet);
    }
}