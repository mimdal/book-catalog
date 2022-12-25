package com.example.infrastructure.persist.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORS", schema = "public")
@Data
public class Author {

    @Id
    @SequenceGenerator(name = "auth_seq_gen", sequenceName = "auth_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_seq_gen")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    private String bio;

}
