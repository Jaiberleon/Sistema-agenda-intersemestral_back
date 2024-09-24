package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/admin")
@CrossOrigin("*")
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('Admin')")
    public ResponseDTO<List<UserEntity>> user (){
        return adminService.users();
    }
}
