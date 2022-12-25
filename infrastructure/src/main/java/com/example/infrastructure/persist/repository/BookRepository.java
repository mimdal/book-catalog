package com.example.infrastructure.persist.repository;

import com.example.infrastructure.persist.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbnIgnoreCase(@NonNull String isbn);

}
