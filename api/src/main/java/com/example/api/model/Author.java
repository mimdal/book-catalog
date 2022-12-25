package com.example.api.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Author {

    @NotEmpty
    private String name;

    private String bio;

}