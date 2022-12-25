package com.example.infrastructure.persist.mapping;

import com.example.domain.model.AuthorModel;
import com.example.domain.model.BookModel;
import com.example.domain.model.BookSearchModelResult;
import com.example.infrastructure.persist.entity.Author;
import com.example.infrastructure.persist.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InfrastructureMapping {

    @Mapping(target = "id", ignore = true)
    Book toBook(BookModel bookModel);

    @Mapping(target = "id", ignore = true)
    Author toAuthor(AuthorModel authorModel);

    BookModel toBookModel(Book bookEntity);

    void update(@MappingTarget Book bookEntity, BookModel bookModel);


    default BookSearchModelResult toBookSearchModelResult(List<Book> books) {
        BookSearchModelResult result = new BookSearchModelResult();
        List<BookModel> bookModels = books.stream().map(this::toBookModel).collect(Collectors.toList());
        result.setBooks(bookModels);
        return result;
    }


}
