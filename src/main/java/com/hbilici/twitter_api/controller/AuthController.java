package com.hbilici.twitter_api.controller;

import com.hbilici.twitter_api.dto.request.LoginRequest;
import com.hbilici.twitter_api.dto.request.RegisterRequest;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return authService.register(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.password()
        );
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return authService.login(request.email(), request.password());
    }
}