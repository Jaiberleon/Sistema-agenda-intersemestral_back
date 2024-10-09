package com.example.demo.repository;

import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByIdentificacion(String identificacion);
    Optional<UserEntity> findByEmail(String email);

    @Query(value= "Select r.role_name from user_roles ur INNER JOIN roles r ON r.role_id = ur.role_id where ur.user_id = :programaId" ,nativeQuery = true)
    String getRole (@Param("programaId") Long programaId);
 }
