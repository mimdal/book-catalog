package com.example.domain.model;

import lombok.Data;

import java.util.Set;

@Data
public class BookModel {

    private Long id;

    private String title;

    private String summary;

    private String isbn;

    private Set<AuthorModel> authors;

}
