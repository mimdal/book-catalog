package com.example.api.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotEmpty;

@Data
public class Author {

    @NotEmpty
    private String name;

    private String bio;

}