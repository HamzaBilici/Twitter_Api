package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.dto.request.TweetRequest;
import com.hbilici.twitter_api.dto.response.TweetResponse;
import com.hbilici.twitter_api.entity.Like;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.exception.BadRequestException;
import com.hbilici.twitter_api.exception.NotFoundException;
import com.hbilici.twitter_api.repository.LikeRepository;
import com.hbilici.twitter_api.repository.TweetRepository;
import com.hbilici.twitter_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;
    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LikeServiceImpl likeService;

    private User user;
    private Tweet tweet;
    private Like like;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("hamza@test.com");

        tweet = new Tweet();
        tweet.setId(10L);

        like = new Like();
        like.setUser(user);
        like.setTweet(tweet);
    }

    @Test
    @DisplayName("Tweet daha önce beğenilmemişse yeni beğeni eklenmeli")
    void shouldAddLikeWhenNotLikedBefore() {
        // Given
        when(userRepository.findByEmail("hamza@test.com")).thenReturn(Optional.of(user));
        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));
        when(likeRepository.findByUserAndTweet(user, tweet)).thenReturn(Optional.empty());

        likeService.toggleLike(10L, "hamza@test.com");

        verify(likeRepository, times(1)).save(any(Like.class));
        verify(likeRepository, never()).delete(any(Like.class));
    }

    @Test
    @DisplayName("Tweet zaten beğenilmişse beğeni geri çekilmeli")
    void shouldRemoveLikeWhenAlreadyLiked() {
        when(userRepository.findByEmail("hamza@test.com")).thenReturn(Optional.of(user));
        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));
        when(likeRepository.findByUserAndTweet(user, tweet)).thenReturn(Optional.of(like));

        likeService.toggleLike(10L, "hamza@test.com");

        verify(likeRepository, times(1)).delete(like);
        verify(likeRepository, never()).save(any(Like.class));
    }
}