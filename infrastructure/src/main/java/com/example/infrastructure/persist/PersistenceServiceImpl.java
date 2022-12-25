package com.example.infrastructure.persist;

import com.example.domain.model.BookModel;
import com.example.domain.persist.PersistenceService;
import com.example.infrastructure.persist.entity.Book;
import com.example.infrastructure.persist.mapping.InfrastructureMapping;
import com.example.infrastructure.persist.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersistenceServiceImpl implements PersistenceService {

    private final BookRepository repository;

    private final InfrastructureMapping mapping;

    @Override
    public Optional<BookModel> findByIsbn(String isbn) {
        Optional<Book> bookEntity = repository.findByIsbnIgnoreCase(isbn);
        return bookEntity.map(book -> mapping.toBookModel(bookEntity.get()));
    }

    @Override
    public BookModel save(BookModel bookModel) {
        bookModel.setId(repository.save(mapping.toBook(bookModel)).getId());
        return bookModel;
    }

}
