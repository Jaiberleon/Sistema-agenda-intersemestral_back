package com.example.demo.service.imp;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.model.FacultadEntity;
import com.example.demo.model.ProgramaEntity;
import com.example.demo.model.RoleEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.FacultadRepository;
import com.example.demo.repository.ProgramaRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenUtil;

import com.example.demo.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private FacultadRepository facultadRepository;
    private JwtTokenUtil jwtTokenUtil;
    private ProgramaRepository programaRepository;

    @Override
    public ResponseDTO<UserEntity> registerUser(UserRequestDto user) {
        try {
            if (userRepository.findByIdentificacion(user.getIdentificacion()).isPresent() || user.getIdentificacion().isEmpty()) {
                return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "Validar el numero de identificacion ya esta registrado");
            }
            if (userRepository.findByEmail(user.getEmail()).isPresent() || user.getEmail().isEmpty()) {
                return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "El correo ya esta registrado, intente con otra opcion");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserEntity users = new UserEntity();
            users.setNombre(user.getNombre());
            users.setApellido(user.getApellido());
            users.setIdentificacion(user.getIdentificacion());
            users.setEmail(user.getEmail());
            users.setPassword(user.getPassword());
            Optional<RoleEntity> rol = roleRepository.findById(user.getRol());
            Optional<FacultadEntity> facultad = facultadRepository.findById(user.getFacultad());
            Optional<ProgramaEntity> programa = programaRepository.findById(user.getPrograma());
            if (rol.isPresent() && facultad.isPresent() && programa.isPresent()) {
                RoleEntity roleEntity = rol.get();
                FacultadEntity facultadEntity = facultad.get();
                ProgramaEntity programaEntity = programa.get();

                users.setRoles(new HashSet<>(Collections.singletonList(roleEntity)));
                users.setFacultad(facultadEntity);
                users.setPrograma(programaEntity);

                userRepository.save(users);
            } else {
                throw new EntityNotFoundException("Uno o más registros no encontrados");
            }return new ResponseDTO<>(users, HttpStatus.CREATED.value(), "Registrado correctamente");

        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "Ocurrio un problema en el proceso de registro");
        }

    }

    @Override
    public ResponseDTO<String> login(String email, String password) throws Exception {
        try {
            UserEntity user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                return new ResponseDTO<>(null, HttpStatus.NOT_FOUND.value(), "Usuario no encontrado");
            }
            if (user.getRoles().isEmpty()) {
                return new ResponseDTO<>(null, HttpStatus.FORBIDDEN.value(), "No se le ha asignado un rol. Contacte al administrador.");
            }
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
