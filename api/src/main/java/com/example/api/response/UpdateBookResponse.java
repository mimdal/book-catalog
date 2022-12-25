package com.example.api.response;

import com.example.api.model.Author;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateBookResponse {

    private Long id;

    private String title;

    private String summary;

    private String isbn;

    private Set<Author> authors;

}
