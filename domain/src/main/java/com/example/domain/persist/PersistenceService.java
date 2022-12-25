package com.example.domain.persist;

import com.example.domain.model.BookModel;

import java.util.Optional;

public interface PersistenceService {

    Optional<BookModel> findByIsbn(String isbn);

    BookModel save(BookModel book);

    Optional<BookModel> findById(Long id);

    Optional<BookModel> updateBook(BookModel book);

}
