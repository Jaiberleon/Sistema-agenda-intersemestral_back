package com.example.demo.repository;

import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AgendasValidRepository extends JpaRepository<AgendasValid,Long> {

    @Query(value = "SELECT av.id AS id, av.file_name AS fileName, f.name AS faculty, p.name AS program, av.creation_date AS creationDate, av.dean_approval AS deanApproval, av.program_director_approval AS programDirectorApproval " +
            "FROM agendas_valid av " +
            "LEFT JOIN faculty f ON f.faculty_id = av.faculty_id " +
            "LEFT JOIN program p ON p.id = av.program_id " +
            "WHERE av.user_id = :userId " +
            "ORDER BY av.creation_date DESC", nativeQuery = true)
    List<IAgendaDto> findAllByuser(@Param("userId") Long userId);

    @Query(value = "SELECT av.id AS id, av.file_name AS fileName, f.name AS faculty, p.name AS program, av.creation_date AS creationDate, av.dean_approval AS deanApproval, av.program_director_approval AS programDirectorApproval " +
            "FROM agendas_valid av " +
            "LEFT JOIN faculty f ON f.faculty_id = av.faculty_id " +
            "LEFT JOIN program p ON p.id = av.program_id " +
            "WHERE av.program_id = :programId AND av.program_director_approval IS NULL " +
            "ORDER BY av.creation_date DESC", nativeQuery = true)
    List<IAgendaDto> findAllToDirector(@Param("programId") Long programId);

    @Query(value = "SELECT av.id AS id, av.file_name AS fileName, f.name AS faculty, p.name AS program, av.creation_date AS creationDate, av.dean_approval AS deanApproval, av.program_director_approval AS programDirectorApproval " +
            "FROM agendas_valid av " +
            "LEFT JOIN faculty f ON f.faculty_id = av.faculty_id " +
            "LEFT JOIN program p ON p.id = av.program_id " +
            "WHERE av.program_id = :programId AND av.program_director_approval IS NOT NULL " +
            "ORDER BY av.creation_date DESC", nativeQuery = true)
    List<IAgendaDto> findAllForHistoricDirector(@Param("programId") Long programId);

    @Query(value = "SELECT av.id AS id, av.file_name AS fileName, f.name AS faculty, p.name AS program, av.creation_date AS creationDate, av.dean_approval AS deanApproval, av.program_director_approval AS programDirectorApproval " +
            "FROM agendas_valid av " +
            "LEFT JOIN faculty f ON f.faculty_id = av.faculty_id " +
            "LEFT JOIN program p ON p.id = av.program_id " +
            "WHERE av.faculty_id = :deanId AND av.program_director_approval = 1 AND av.dean_approval IS NULL " +
            "ORDER BY av.creation_date DESC", nativeQuery = true)
    List<IAgendaDto> findAllForAproveDecano(@Param("deanId") Long deanId);

    @Query(value = "SELECT av.id AS id, av.file_name AS fileName, f.name AS faculty, p.name AS program, av.creation_date AS creationDate, av.dean_approval AS deanApproval, av.program_director_approval AS programDirectorApproval " +
            "FROM agendas_valid av " +
            "LEFT JOIN faculty f ON f.faculty_id = av.faculty_id " +
            "LEFT JOIN program p ON p.id = av.program_id " +
            "WHERE av.faculty_id = :deanId AND av.program_director_approval = 1 AND av.dean_approval IS NOT NULL " +
            "ORDER BY av.creation_date DESC", nativeQuery = true)
    List<IAgendaDto> findAllForHistoricoDecano(@Param("deanId") Long deanId);

}
