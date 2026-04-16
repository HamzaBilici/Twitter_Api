package com.hbilici.twitter_api.util;

import com.hbilici.twitter_api.dto.response.UserResponse;
import com.hbilici.twitter_api.entity.User;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
