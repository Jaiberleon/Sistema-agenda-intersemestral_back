package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
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
    public ResponseDTO<UserEntity> registerUser (@Validated @RequestBody UserEntity user){
        return userService.registerUser(user);
    }
}
