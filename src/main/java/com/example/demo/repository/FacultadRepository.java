package com.example.demo.repository;

import com.example.demo.model.FacultadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultadRepository  extends JpaRepository<FacultadEntity, Long> {

}