package com.hbilici.twitter_api.dto.response;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email
) {}