package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.dto.response.UserResponse;
import com.hbilici.twitter_api.entity.User;

public interface AuthService {
    UserResponse register(String firstName, String lastName, String email, String password);
    UserResponse login(String email, String password);
}
