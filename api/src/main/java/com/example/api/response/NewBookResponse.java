package com.example.api.response;

import com.example.api.request.Author;
import lombok.Data;

import java.util.Set;

@Data
public class NewBookResponse {

    private Long id;

    private String title;

    private String summary;

    private String isbn;

    private Set<Author> authors;

}
