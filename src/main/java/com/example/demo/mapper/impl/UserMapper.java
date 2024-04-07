package com.example.demo.mapper.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.mapper.ObjectMapper;
import com.example.demo.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements ObjectMapper<User,UserDto> {

    @Override
    public UserDto mapTo(User user) {
        return new UserDto(user);
    }

    @Override
    public User mapFrom(UserDto userDto) {
        return null;
    }
}
