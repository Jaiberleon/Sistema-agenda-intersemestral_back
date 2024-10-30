package com.example.demo.service.imp;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.model.FacultyEntity;
import com.example.demo.model.ProgramEntity;
import com.example.demo.model.RoleEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.FacultadRepository;
import com.example.demo.repository.ProgramaRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenUtil;

import com.example.demo.service.UserService;
import com.example.demo.utils.ConstanResponses;
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
    public ResponseDto<UserEntity> registerUser(UserRequestDto user) {
        try {
            if (userRepository.findByIdentification(user.getIdentificacion()).isPresent() || user.getIdentificacion().isEmpty()) {
                return new ResponseDto<>(null, HttpStatus.BAD_REQUEST.value(), ConstanResponses.VALIDATE_THE_IDENTIFICATION_NUMBER_IS_ALREADY_REGISTERED);
            }
            if (userRepository.findByEmail(user.getEmail()).isPresent() || user.getEmail().isEmpty()) {
                return new ResponseDto<>(null, HttpStatus.BAD_REQUEST.value(), ConstanResponses.EMAIL_ALREADY_REGISTER);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserEntity users = new UserEntity();
            users.setFirstName(user.getNombre());
            users.setLastName(user.getApellido());
            users.setIdentification(user.getIdentificacion());
            users.setEmail(user.getEmail());
            users.setPassword(user.getPassword());
            Optional<RoleEntity> rol = roleRepository.findById(user.getRol());
            Optional<FacultyEntity> facultad = facultadRepository.findById(user.getFacultad());

            if (user.getPrograma() != null) {
                Optional<ProgramEntity> programa = programaRepository.findById(user.getPrograma());
                ProgramEntity programaEntity = programa.get();
                users.setProgram(programaEntity);
            }
            if (rol.isPresent() && facultad.isPresent() ) {
                RoleEntity roleEntity = rol.get();
                FacultyEntity facultadEntity = facultad.get();
                users.setRoles(new HashSet<>(Collections.singletonList(roleEntity)));
                users.setFaculty(facultadEntity);
                userRepository.save(users);
            } else {
                throw new EntityNotFoundException(ConstanResponses.ONE_OR_MORE_REGISTERS_NOT_FOUND);
            }return new ResponseDto<>(users, HttpStatus.CREATED.value(), ConstanResponses.OK);

        } catch (Exception e) {
            return new ResponseDto<>(null, HttpStatus.BAD_REQUEST.value(), ConstanResponses.SERVICE_ERROR);
        }

    }

    @Override
    public ResponseDto<String> login(String email, String password) throws Exception {
        try {
            UserEntity user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                return new ResponseDto<>(null, HttpStatus.NOT_FOUND.value(), ConstanResponses.USER_NOT_FOUND);
            }
            if (user.getRoles().isEmpty()) {
                return new ResponseDto<>(null, HttpStatus.FORBIDDEN.value(), ConstanResponses.VALIDATE_ROL);
            }
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.INCORRECT_PASSWORD);
            }
            String token = jwtTokenUtil.generateToken(user);
            return new ResponseDto<>(token,HttpStatus.OK.value(),ConstanResponses.OK);
        } catch (Exception e) {
            return new ResponseDto<>(null, HttpStatus.BAD_REQUEST.value(), ConstanResponses.SERVICE_ERROR);
        }

    }

}
