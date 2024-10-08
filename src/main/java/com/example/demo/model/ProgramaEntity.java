package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "programa")
public class ProgramaEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nombre", nullable = false, unique = true)
        private String nombre;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "facultad_id", nullable = false)
        private FacultadEntity facultad;


}
