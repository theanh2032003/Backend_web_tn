package com.example.demo.controller;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.mapper.impl.UserMapper;
import com.example.demo.security.jwt.JwtFilter;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.ERole;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.security.jwt.JwtProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserMapper mapper;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        if(loginRequest.getUsername() == null || loginRequest.getPassword() == null){
            return new ResponseEntity<>("vui lòng nhập đủ các trường",HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtProvider.createJwt(authentication);

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) throws Exception {

        boolean existUserByUserName = userService.findByUserName(registerRequest.getUsername()).isPresent();

        if(registerRequest.getUsername() == null || registerRequest.getEmail() == null || registerRequest.getPassword() ==null){
            return new ResponseEntity<>("vui lòng nhập đủ các trường",HttpStatus.BAD_REQUEST);
        }

        if(existUserByUserName){
            return new ResponseEntity<>("username đã tồn tại",HttpStatus.BAD_REQUEST);
        }
        boolean existUserByEmail = userService.findByEmail(registerRequest.getEmail()).isPresent();

        if(existUserByEmail){
            return new ResponseEntity<>("email đã tồn tại",HttpStatus.BAD_REQUEST);

        }


        Role role;

        if (registerRequest.getRole() == null || registerRequest.getRole().equalsIgnoreCase("user")) {
            role = roleRepo.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("User Role not found"));
        } else {
            role = roleRepo.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Admin Role not found"));
        }

        User saveUser =  userService.save(new User(registerRequest.getUsername(), registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()), role));


        return new ResponseEntity<>(mapper.mapTo(saveUser),HttpStatus.CREATED);
    }
}