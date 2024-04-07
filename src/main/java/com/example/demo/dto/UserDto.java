package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String role;


    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole().getName().toString();
    }
}
