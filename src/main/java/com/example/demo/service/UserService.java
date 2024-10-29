package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.model.UserEntity;

public interface UserService {

    ResponseDto<UserEntity> registerUser (UserRequestDto user);
    ResponseDto<String> login(String email, String password) throws Exception;
}
