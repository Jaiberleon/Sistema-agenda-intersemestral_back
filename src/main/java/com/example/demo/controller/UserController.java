package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.model.UserEntity;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {
    private UserService userService;

    @PostMapping("/register")
    public ResponseDTO<UserEntity> registerUser (@Validated @RequestBody UserRequestDto user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseDTO<String> login(@RequestBody LoginRequest loginRequest) throws Exception {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
