package com.example.demo.service.imp;

import com.example.demo.dto.ResponseDto;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdminService;
import com.example.demo.utils.ConstanResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public ResponseDto<List<UserEntity>> getUsers() {
        try {
            List<UserEntity> users = userRepository.findAll();
            return new ResponseDto<>(users, HttpStatus.OK.value(), ConstanResponses.RESPONSE_CORRECT_GET_USERS_ADMIN);
        } catch (Exception e) {
            return new ResponseDto<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), ConstanResponses.RESPONSE_INCORRECT_GET_USERS_ADMIN + e.getMessage());
        }
    }
}
