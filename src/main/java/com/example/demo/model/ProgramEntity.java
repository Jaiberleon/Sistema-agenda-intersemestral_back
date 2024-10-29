package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "program")
public class ProgramEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "program_id")
        private Long id;

        @NotNull(message = "Program name is required")
        @Column(name = "name", nullable = false, unique = true)
        private String name;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "faculty_id", nullable = false)
        private FacultyEntity faculty;
}
