package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.ResponseDto;
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
    public ResponseDto<UserEntity> registerUser (@Validated @RequestBody UserRequestDto user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginRequestDto loginRequest) throws Exception {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
