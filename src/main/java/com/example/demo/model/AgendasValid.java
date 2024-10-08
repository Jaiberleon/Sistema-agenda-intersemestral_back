package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agendas_valid")
public class AgendasValid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "archivo", columnDefinition = "LONGBLOB")
    private byte[] archivo;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Column(name = "aprobacion_decano")
    private Boolean aprobacionDecano;

    @Column(name = "aprobacion_director_programa")
    private Boolean aprobacionDirectorPrograma;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity docente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programa_id", nullable = false)
    private ProgramaEntity programa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id", nullable = false)
    private FacultadEntity facultad;
}

