package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.dto.request.CommentRequest;
import com.hbilici.twitter_api.dto.response.CommentResponse;
import com.hbilici.twitter_api.entity.Comment;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.exception.BadRequestException;
import com.hbilici.twitter_api.exception.NotFoundException;
import com.hbilici.twitter_api.repository.CommentRepository;
import com.hbilici.twitter_api.repository.TweetRepository;
import com.hbilici.twitter_api.repository.UserRepository;
import com.hbilici.twitter_api.util.CommentMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public CommentResponse findById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Yorum bulunamadı: " + commentId));


        return CommentMapper.toResponse(comment);

    }

    @Override
    @Transactional
    public CommentResponse save(Long tweetId, String email, CommentRequest request) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet bulunamadı: " + tweetId));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı: " + email));

        if (request.getText().length() > 140) { // Burası 140 mı?
            throw new BadRequestException("Yorum 140 karakterden fazla olamaz");
        }
        Comment comment = new Comment();
        comment.setContent(request.getText());
        comment.setTweet(tweet);
        comment.setUser(user);

        return CommentMapper.toResponse(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponse> findByTweet(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet bulunamadı: " + tweetId));

        return commentRepository.findByTweetId(tweet.getId()).stream()
                .map(CommentMapper::toResponse)
                .toList();
    }


    @Override
    @Transactional
    public void delete(Long commentId, String email) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Yorum bulunamadı! ID: " + commentId));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı: " + email));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Sadece kendi yorumunuzu silebilirsiniz!");
        }

        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public CommentResponse update(Long commentId, String email, CommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Yorum bulunamadı! ID: " + commentId));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı: " + email));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Sadece kendi yorumunuzu güncelleyebilirsiniz!");
        }

        if (request.getText() == null || request.getText().isBlank()) {
            throw new BadRequestException("Yorum içeriği boş olamaz!");
        }

        comment.setContent(request.getText());
        Comment updatedComment = commentRepository.save(comment);
        return CommentMapper.toResponse(updatedComment);
    }
}