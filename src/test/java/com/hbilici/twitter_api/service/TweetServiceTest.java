package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.dto.request.TweetRequest;
import com.hbilici.twitter_api.dto.response.TweetResponse;
import com.hbilici.twitter_api.entity.Tweet;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.exception.BadRequestException;
import com.hbilici.twitter_api.exception.NotFoundException;
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
class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TweetServiceImpl tweetService;

    private User mockUser;
    private Tweet mockTweet;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("hamza@example.com");

        mockTweet = new Tweet();
        mockTweet.setId(10L);
        mockTweet.setContent("Test Tweet");
        mockTweet.setUser(mockUser);
    }

    @Test
    @DisplayName("Tweet başarıyla kaydedilmeli")
    void shouldSaveTweetSuccessfully() {
        TweetRequest request = new TweetRequest("Yeni Tweet");
        when(userRepository.findByEmail("hamza@example.com")).thenReturn(Optional.of(mockUser));
        when(tweetRepository.save(any(Tweet.class))).thenReturn(mockTweet);

        TweetResponse response = tweetService.save(request, "hamza@example.com");

        assertNotNull(response);
        assertEquals("Test Tweet", response.getContent());
        verify(tweetRepository, times(1)).save(any(Tweet.class));
    }

    @Test
    @DisplayName("280 karakterden uzun tweet fırlatılmalı")
    void shouldThrowExceptionWhenContentIsTooLong() {
        String longContent = "a".repeat(281);
        TweetRequest request = new TweetRequest(longContent);

        assertThrows(BadRequestException.class, () -> {
            tweetService.save(request, "hamza@example.com");
        });

        verify(tweetRepository, never()).save(any());
    }

    @Test
    @DisplayName("Tweet bulunamadığında NotFoundException fırlatılmalı")
    void shouldThrowNotFoundExceptionWhenTweetDoesNotExist() {
        when(tweetRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            tweetService.findById(99L);
        });
    }

    @Test
    @DisplayName("Kullanıcı bulunamadığında NotFoundException fırlatmalı")
    void shouldThrowNotFoundExceptionWhenUserNotFound() {
        TweetRequest request = new TweetRequest("İçerik");
        String email = "olmayan@kullanici.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tweetService.save(request, email));

        verify(tweetRepository, never()).save(any());
    }

    @Test
    @DisplayName("Başka bir kullanıcı tweet silmeye çalışırsa hata fırlatmalı")
    void shouldThrowExceptionWhenUserTriesToDeleteOthersTweet() {
        // Given
        Long tweetId = 10L;
        String maliciousUserEmail = "hacker@example.com";

        User owner = new User();
        owner.setEmail("owner@example.com");

        Tweet targetTweet = new Tweet();
        targetTweet.setUser(owner);

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(targetTweet));

        assertThrows(BadRequestException.class, () -> tweetService.delete(tweetId, maliciousUserEmail));

        verify(tweetRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Tüm tweetleri başarıyla getirmeli")
    void shouldReturnAllTweets() {
        Tweet mockTweet2 = new Tweet();
        mockTweet2.setId(11L);
        mockTweet2.setContent("İkinci Test Tweeti");
        mockTweet2.setUser(mockUser);

        List<Tweet> tweets = List.of(mockTweet, mockTweet2);
        when(tweetRepository.findAll()).thenReturn(tweets);

        List<TweetResponse> result = tweetService.findAll();

        assertEquals(2, result.size());
        assertEquals(10L, result.get(0).getId());
        assertEquals(11L, result.get(1).getId());
        verify(tweetRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Tweet içeriği başarıyla güncellenmeli")
    void shouldUpdateTweetSuccessfully() {
        TweetRequest updateRequest = new TweetRequest("Güncellenmiş içerik");
        when(tweetRepository.findById(10L)).thenReturn(Optional.of(mockTweet));
        when(tweetRepository.save(any(Tweet.class))).thenReturn(mockTweet);

        TweetResponse response = tweetService.update(10L, updateRequest, "hamza@example.com");

        assertNotNull(response);
        verify(tweetRepository).save(any(Tweet.class));
    }
}