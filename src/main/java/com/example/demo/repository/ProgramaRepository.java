package com.example.demo.repository;

import com.example.demo.dto.interfazdto.IFacultyDto;
import com.example.demo.dto.interfazdto.IProgramDto;
import com.example.demo.model.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramaRepository extends JpaRepository<ProgramEntity,Long> {

    @Query(value = "SELECT p.id AS id, p.name AS name FROM program p WHERE p.faculty_id = :facultyId", nativeQuery = true)
    List<IProgramDto> findByFacultadId (@Param("facultyId") Long facultadId);

    @Query(value = "SELECT f.faculty_id AS facultyId, f.name AS name FROM faculty f", nativeQuery = true)
    List<IFacultyDto> finAllFacultad ();
}
