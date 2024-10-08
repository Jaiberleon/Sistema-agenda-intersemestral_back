package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AgendaValidDto {
    @Column(name = "id")
    private Long id;
    @Column(name = "programa")
    private String programa;
    @Column(name = "facultad")
    private String facultad;
    @Column(name = "aprobacion_director_programa")
    private Boolean aprobacionDirectorPrograma;
    @Column(name = "aprobacion_decano")
    private Boolean aprobacionDecano;
    @Column(name = "aprobacion_vicerrector")

    private Boolean aprobacionVicerrector;
    @Column(name ="fecha_creacion" )
    private String fechaCreacion;
}
