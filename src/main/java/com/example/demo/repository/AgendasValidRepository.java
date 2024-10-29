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

    @Query(value = "select va.id as id, va.nombre_archivo as nombreArchivo, f.name as facultad, p.nombre as programa, va.fecha_creacion as fechaCreacion, va.aprobacion_decano as aprobacionDecano, va.aprobacion_director_programa as aprobacionDirectorPrograma from agendas_valid va LEFT JOIN facultad f ON f.facultad_id = va.facultad_id LEFT JOIN programa p ON p.id = va.programa_id  where va.user_id = :userid order by va.fecha_creacion desc" , nativeQuery = true)
    List<IAgendaDto> findAllByuser(@Param("userid") Long userId);


    @Query (value = "select va.id as id, va.nombre_archivo as nombreArchivo, f.name as facultad, p.nombre as programa, va.fecha_creacion as fechaCreacion, va.aprobacion_decano as aprobacionDecano, va.aprobacion_director_programa as aprobacionDirectorPrograma from agendas_valid va LEFT JOIN facultad f ON f.facultad_id = va.facultad_id LEFT JOIN programa p ON p.id = va.programa_id  where va.programa_id = :programaId and va.aprobacion_director_programa is null order by va.fecha_creacion desc", nativeQuery = true)
    List<IAgendaDto> findAllToDirector (@Param("programaId") Long programaId);

    @Query (value = "select va.id as id, va.nombre_archivo as nombreArchivo, f.name as facultad, p.nombre as programa, va.fecha_creacion as fechaCreacion, va.aprobacion_decano as aprobacionDecano, va.aprobacion_director_programa as aprobacionDirectorPrograma from agendas_valid va LEFT JOIN facultad f ON f.facultad_id = va.facultad_id LEFT JOIN programa p ON p.id = va.programa_id  where va.programa_id = :programaId and va.aprobacion_director_programa is not null order by va.fecha_creacion desc", nativeQuery = true)
    List<IAgendaDto> findAllForHistoricDirector (@Param("programaId") Long programaId);


    @Query(value = "select va.id as id, va.nombre_archivo as nombreArchivo, f.name as facultad, p.nombre as programa, va.fecha_creacion as fechaCreacion, va.aprobacion_decano as aprobacionDecano, va.aprobacion_director_programa as aprobacionDirectorPrograma from agendas_valid va LEFT JOIN facultad f ON f.facultad_id = va.facultad_id LEFT JOIN programa p ON p.id = va.programa_id  where va.facultad_id = :decanoId and va.aprobacion_director_programa =1 and va.aprobacion_decano is null  order by va.fecha_creacion desc", nativeQuery = true)
    List<IAgendaDto> findAllForAproveDecano (@Param("decanoId") Long decanoId);

    @Query(value = "select va.id as id, va.nombre_archivo as nombreArchivo, f.name as facultad, p.nombre as programa, va.fecha_creacion as fechaCreacion, va.aprobacion_decano as aprobacionDecano, va.aprobacion_director_programa as aprobacionDirectorPrograma from agendas_valid va LEFT JOIN facultad f ON f.facultad_id = va.facultad_id LEFT JOIN programa p ON p.id = va.programa_id  where va.facultad_id = :decanoId and va.aprobacion_director_programa =1 and va.aprobacion_decano is not null  order by va.fecha_creacion desc", nativeQuery = true)
    List<IAgendaDto> findAllForHistoricoDecano (@Param("decanoId") Long decanoId);

}
