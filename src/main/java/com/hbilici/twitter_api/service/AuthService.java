package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.entity.User;

public interface AuthService {
    public User register(String firstName, String lastName, String email, String password);
}
