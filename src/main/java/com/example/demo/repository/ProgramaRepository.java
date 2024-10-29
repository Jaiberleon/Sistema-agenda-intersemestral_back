package com.example.demo.repository;

import com.example.demo.dto.interfazdto.FacultadDto;
import com.example.demo.dto.interfazdto.ProgramaDTO;
import com.example.demo.model.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramaRepository extends JpaRepository<ProgramEntity,Long> {

    @Query(value = "SELECT p.id as id, p.nombre as nombre  FROM programa p WHERE facultad_id = :facultadid" ,nativeQuery = true)
    List<ProgramaDTO> findByFacultadId (@Param("facultadid") Long facultadId);

    @Query(value = "SELECT f.facultad_id as facultadId, f.name as name   FROM facultad f",nativeQuery = true)
    List<FacultadDto> finAllFacultad ();
}
