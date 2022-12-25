package com.example.api.response;

import com.example.api.model.Book;
import lombok.Data;

import java.util.List;

@Data
public class GetBooksResponse {

    private List<Book> books;

}
