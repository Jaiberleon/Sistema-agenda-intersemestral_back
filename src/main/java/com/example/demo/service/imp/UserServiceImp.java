package com.example.demo.service.imp;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenUtil;

import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    private JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseDTO<UserEntity> registerUser(UserEntity user) {
        try {
            if (userRepository.findByIdentificacion(user.getIdentificacion()).isPresent() || user.getIdentificacion().isEmpty()) {
                return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "Validar el numero de identificacion ya esta registrado");
            }
            if (userRepository.findByEmail(user.getEmail()).isPresent() || user.getEmail().isEmpty()) {
                return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "El correo ya esta registrado, intente con otra opcion");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new ResponseDTO<>(user, HttpStatus.CREATED.value(), "Registrado correctamente");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "Ocurrio un problema en el proceso de registro");
        }

    }

    @Override
    public ResponseDTO<String> login(String email, String password) throws Exception {
        try {
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new Exception("Usuario no encontrado"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new ResponseDTO<>(null,HttpStatus.BAD_REQUEST.value(), "Contraseña incorrecta");
            }
            String token = jwtTokenUtil.generateToken(user);
            return new ResponseDTO<>(token,HttpStatus.OK.value(),"Login exitoso");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "Ocurrio un problema en el proceso Logueo");
        }

    }

}
