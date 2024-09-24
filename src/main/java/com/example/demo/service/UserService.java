package com.example.demo.service;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.UserEntity;

public interface UserService {

    ResponseDTO<UserEntity> registerUser (UserEntity user);
}
