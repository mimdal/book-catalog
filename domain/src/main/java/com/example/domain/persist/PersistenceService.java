package com.example.domain.persist;

import com.example.domain.model.BookModel;
import com.example.domain.model.BookSearchModel;
import com.example.domain.model.BookSearchModelResult;

import java.util.Optional;

public interface PersistenceService {

    Optional<BookModel> findByIsbn(String isbn);

    BookModel save(BookModel book);

    Optional<BookModel> findById(Long id);

    Optional<BookModel> updateBook(BookModel book);

    void deleteBook(Long id);

    BookSearchModelResult findAllBooks(BookSearchModel searchModel);

}
