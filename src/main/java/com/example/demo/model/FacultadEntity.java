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
@Table(name = "facultad")

public class FacultadEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultadId;

    @Column(nullable = false, unique = true)
    private String Name;



}