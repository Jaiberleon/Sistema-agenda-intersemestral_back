package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.model.UserEntity;
import com.example.demo.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/admin")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "API para la gestión de usuarios en la administración")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('Admin')")
    @Operation(
            summary = "Obtiene todos los usuarios",
            description = "Este endpoint devuelve una lista de todos los usuarios registrados en el sistema. Solo accesible para administradores."
    )
    public ResponseDto<List<UserEntity>> getUsers() {
        return adminService.getUsers();
    }
}