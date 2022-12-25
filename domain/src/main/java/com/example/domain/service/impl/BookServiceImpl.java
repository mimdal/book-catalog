package com.example.domain.service.impl;

import com.example.domain.exception.BookCatalogException;
import com.example.domain.model.BookModel;
import com.example.domain.model.BookSearchModel;
import com.example.domain.model.BookSearchModelResult;
import com.example.domain.persist.PersistenceService;
import com.example.domain.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final PersistenceService persistenceService;

    @Override
    public BookModel newBook(BookModel book) {
        Optional<BookModel> queryResult = persistenceService.findByIsbn(book.getIsbn());
        if (queryResult.isPresent()) {
            throw new BookCatalogException("Entity was persisted before, entity id = " + queryResult.get().getId(), 409);
        } else {
            return persistenceService.save(book);
        }
    }

    @Override
    public BookModel updateBook(BookModel book) {
        return persistenceService.updateBook(book).get();
    }

    @Override
    public void deleteBook(Long id) {
        persistenceService.deleteBook(id);
    }

    @Override
    public BookSearchModelResult getBooks(BookSearchModel searchModel) {
        return persistenceService.findAllBooks(searchModel);
    }
}
