package com.example.demo.service;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.UserEntity;

import java.util.List;

public interface AdminService {
    ResponseDTO<List<UserEntity>> getUsers ();
}
