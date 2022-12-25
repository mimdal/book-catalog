package com.example.api.request;

import lombok.Data;

@Data
public class GetBooksRequest {

    private Integer page;

    private Integer size;

    private String bookTitle;

    private String authorName;

}
