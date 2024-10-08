package com.example.demo.service;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.model.UserEntity;

public interface UserService {

    ResponseDTO<UserEntity> registerUser (UserRequestDto user);
    ResponseDTO<String> login(String email, String password) throws Exception;
}
