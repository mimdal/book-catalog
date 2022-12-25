package com.example.domain.service.impl;

import com.example.domain.exception.BookCatalogException;
import com.example.domain.model.BookModel;
import com.example.domain.persist.PersistenceService;
import com.example.domain.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final PersistenceService persistenceService;

    @Override
    public BookModel newBook(BookModel book) {
        Optional<BookModel> queryResult = persistenceService.findByIsbn(book.getIsbn());
        if (queryResult.isPresent()) {
            throw new BookCatalogException("Entity was persisted before, entity id = " + queryResult.get().getId(),
                    HttpStatus.CONFLICT);
        } else {
            return persistenceService.save(book);
        }
    }

}
