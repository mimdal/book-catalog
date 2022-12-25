package com.example.api.model;

import lombok.Data;

import java.util.Set;

@Data
public class Book {

    private String title;

    private String summary;

    private String isbn;

    private Set<Author> authors;

}
