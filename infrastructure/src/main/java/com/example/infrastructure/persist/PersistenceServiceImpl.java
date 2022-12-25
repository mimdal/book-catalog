package com.example.infrastructure.persist;

import com.example.domain.exception.BookCatalogException;
import com.example.domain.model.BookModel;
import com.example.domain.model.BookSearchModel;
import com.example.domain.model.BookSearchModelResult;
import com.example.domain.persist.PersistenceService;
import com.example.infrastructure.persist.entity.Book;
import com.example.infrastructure.persist.mapping.InfrastructureMapping;
import com.example.infrastructure.persist.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersistenceServiceImpl implements PersistenceService {

    private final BookRepository repository;

    private final InfrastructureMapping mapping;

    @Override
    public Optional<BookModel> findByIsbn(String isbn) {
        Optional<Book> bookEntity = repository.findByIsbnIgnoreCase(isbn);
        return getBookModel(bookEntity);
    }

    @Override
    public BookModel save(BookModel bookModel) {
        bookModel.setId(repository.save(mapping.toBook(bookModel)).getId());
        return bookModel;
    }

    @Override
    public Optional<BookModel> findById(Long id) {
        Optional<Book> bookEntity = repository.findById(id);
        return getBookModel(bookEntity);
    }

    @Override
    public Optional<BookModel> updateBook(BookModel book) {
        Optional<Book> bookEntity = repository.findById(book.getId());
        if (bookEntity.isPresent()) {
            mapping.update(bookEntity.get(), book);
            return getBookModel(bookEntity);
        } else {
            throw new BookCatalogException("Update failed, entity not found! entity id = " + book.getId(), 404);
        }
    }

    @Override
    public void deleteBook(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new BookCatalogException("Delete failed, entity not found! entity id = " + id, 404);
        }
    }

    private Optional<BookModel> getBookModel(Optional<Book> bookEntity) {
        return bookEntity.map(book -> mapping.toBookModel(bookEntity.get()));
    }

    @Override
    public BookSearchModelResult findAllBooks(BookSearchModel searchModel) {
        List<Book> books = repository.findByBookTitleAndAuthorName(
                searchModel.getBookTitle(), searchModel.getAuthorName(),
                PageRequest.of(searchModel.getPage(), searchModel.getSize())
        );
        return mapping.toBookSearchModelResult(books);
    }



}
