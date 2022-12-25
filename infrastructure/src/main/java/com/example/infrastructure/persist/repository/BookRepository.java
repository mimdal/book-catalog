package com.example.infrastructure.persist.repository;

import com.example.infrastructure.persist.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbnIgnoreCase(@NonNull String isbn);

    @Query("select b from Book b left join b.authors a where " +
            "(:title is null or b.title like :title) or " +
            "(:name is null or a.name like :name)")
    List<Book> findByBookTitleAndAuthorName(@Param("title") @Nullable String title, @Nullable @Param("name") String name,
                                           Pageable pageable);

}
