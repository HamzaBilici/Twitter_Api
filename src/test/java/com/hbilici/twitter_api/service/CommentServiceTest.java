package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.dto.request.CommentRequest;
import com.hbilici.twitter_api.dto.response.CommentResponse;
import com.hbilici.twitter_api.entity.Comment;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.exception.BadRequestException;
import com.hbilici.twitter_api.repository.CommentRepository;
import com.hbilici.twitter_api.repository.TweetRepository;
import com.hbilici.twitter_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private User commentOwner;
    private User tweetOwner;
    private Tweet targetTweet;
    private Comment mockComment;

    @BeforeEach
    void setUp() {
        commentOwner = new User();
        commentOwner.setId(1L);
        commentOwner.setEmail("commenter@test.com");

        tweetOwner = new User();
        tweetOwner.setId(2L);
        tweetOwner.setEmail("author@test.com");

        targetTweet = new Tweet();
        targetTweet.setId(100L);
        targetTweet.setUser(tweetOwner);

        mockComment = new Comment();
        mockComment.setId(500L);
        mockComment.setContent("Test yorumu");
        mockComment.setUser(commentOwner);
        mockComment.setTweet(targetTweet);
    }

    @Test
    @DisplayName("Tweete başarıyla yorum eklenmeli")
    void shouldSaveCommentSuccessfully() {
        CommentRequest request = new CommentRequest("Harika tweet!");
        when(tweetRepository.findById(100L)).thenReturn(Optional.of(targetTweet));
        when(userRepository.findByEmail("commenter@test.com")).thenReturn(Optional.of(commentOwner));
        when(commentRepository.save(any(Comment.class))).thenReturn(mockComment);

        CommentResponse response = commentService.save(100L, "commenter@test.com", request);

        assertNotNull(response);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    @DisplayName("Başkası yorumu güncellemeye çalışırsa hata fırlatmalı")
    void shouldThrowExceptionWhenOthersTryToUpdateComment() {
        CommentRequest updateRequest = new CommentRequest("Değiştirilmiş yorum");
        String maliciousUser = "hacker@test.com";

        when(commentRepository.findById(500L)).thenReturn(Optional.of(mockComment));

        User stranger = new User();
        stranger.setId(99L);
        when(userRepository.findByEmail(maliciousUser)).thenReturn(Optional.of(stranger));

        // When & Then
        assertThrows(BadRequestException.class, () ->
                commentService.update(500L, maliciousUser, updateRequest)
        );
    }

    @Test
    @DisplayName("140 karakterden uzun yorumlar hata fırlatmalı")
    void shouldThrowExceptionWhenCommentIsTooLong() {
        // Given
        String longText = "a".repeat(141);
        CommentRequest request = new CommentRequest(longText);

        // Hata almadan ilerleyebilmesi için bu mockları eklemelisin:
        when(tweetRepository.findById(100L)).thenReturn(Optional.of(targetTweet));
        when(userRepository.findByEmail("commenter@test.com")).thenReturn(Optional.of(commentOwner));

        // When & Then
        assertThrows(BadRequestException.class, () ->
                commentService.save(100L, "commenter@test.com", request)
        );
    }
}