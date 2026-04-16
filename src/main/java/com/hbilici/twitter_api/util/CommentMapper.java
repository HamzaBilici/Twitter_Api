package com.hbilici.twitter_api.util;

import com.hbilici.twitter_api.dto.response.CommentResponse;
import com.hbilici.twitter_api.dto.response.UserResponse;
import com.hbilici.twitter_api.entity.Comment;

public class CommentMapper {


    public static CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getDate(),
                new UserResponse(
                        comment.getUser().getId(),
                        comment.getUser().getFirstName(),
                        comment.getUser().getLastName(),
                        comment.getUser().getEmail()
                )
        );
    }

}
