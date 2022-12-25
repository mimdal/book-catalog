package com.example.domain.service;


import com.example.domain.model.BookModel;
import com.example.domain.model.BookSearchModel;
import com.example.domain.model.BookSearchModelResult;

public interface BookService {

    BookModel newBook(BookModel book);

    BookModel updateBook(BookModel toBookModel);

    void deleteBook(Long id);

    BookSearchModelResult getBooks(BookSearchModel searchModel);

}
