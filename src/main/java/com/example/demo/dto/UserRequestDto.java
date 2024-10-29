package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequestDto {
    private String identificacion;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Long rol;
    private Long facultad;
    private Long programa;
}
