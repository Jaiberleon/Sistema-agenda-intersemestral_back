package com.example.demo.repository;

import com.example.demo.model.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultadRepository  extends JpaRepository<FacultyEntity, Long> {

}
