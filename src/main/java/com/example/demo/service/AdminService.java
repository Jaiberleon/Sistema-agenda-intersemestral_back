package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.model.UserEntity;

import java.util.List;

public interface AdminService {
    ResponseDto<List<UserEntity>> getUsers ();
}
