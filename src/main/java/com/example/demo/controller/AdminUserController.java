package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.mapper.impl.UserMapper;
import com.example.demo.model.ERole;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.request.ChangeInfoUserRequest;
import com.example.demo.request.EmailRequest;
import com.example.demo.service.impl.ExamServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private ExamServiceImpl examService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/all_users")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.findAll();
        List<UserDto> userDtos = users.stream().map(i -> new UserDto(i)).collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        Optional<User> user = userService.findById(userId);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto userDto = userMapper.mapTo(user.get());
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUserByEmail(@RequestBody EmailRequest emailRequest){
        Optional<User> user = userService.findByEmail(emailRequest.getEmail());
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto userDto = userMapper.mapTo(user.get());

        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId){
        Optional<User> user = userService.findById(userId);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(user.get());
        return new ResponseEntity<>("user đã bị xóa",HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserByEmail(@RequestBody EmailRequest emailRequest){
        Optional<User> user = userService.findByEmail(emailRequest.getEmail());
        if(user.isEmpty()){
            return new ResponseEntity<>("không tìm thấy email",HttpStatus.NOT_FOUND);
        }
        userService.delete(user.get());
        return new ResponseEntity<>("user đã bị xóa",HttpStatus.OK);
    }

    @PatchMapping("/change_user")
    public ResponseEntity<?> changeInfoUser(@RequestBody ChangeInfoUserRequest infoUser){
        Optional<User> user = userService.findById(infoUser.getUserId());
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(infoUser.getEmail() != null){
            user.get().setEmail(infoUser.getEmail());
        }

        if(infoUser.getUsername() != null){
            user.get().setUsername(infoUser.getUsername());
        }

        if(infoUser.getPassword() != null){
            user.get().setPassword(passwordEncoder.encode(infoUser.getPassword()));
        }


        Role role;
        if (infoUser.getRole() == null || infoUser.getRole().equalsIgnoreCase("user")) {
            role = roleRepo.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("User Role not found"));
        } else {
            role = roleRepo.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Admin Role not found"));
        }
        user.get().setRole(role);
        userService.save(user.get());
        return new ResponseEntity<>("Thông tin user đã được thay đổi",HttpStatus.OK);
    }



}
