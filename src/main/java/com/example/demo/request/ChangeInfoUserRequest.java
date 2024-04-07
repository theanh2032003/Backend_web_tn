package com.example.demo.request;

import lombok.Data;

@Data
public class ChangeInfoUserRequest {
    private Long userId;

    private String username;

    private String email;

    private String password;

    private String role;
}
