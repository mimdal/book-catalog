package com.example.domain.model;

import lombok.Data;

@Data
public class BookSearchModel {

    private Integer page;

    private Integer size;

    private String bookTitle;

    private String authorName;

}
