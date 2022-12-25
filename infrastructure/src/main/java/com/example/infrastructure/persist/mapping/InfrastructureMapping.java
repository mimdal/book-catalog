package com.example.infrastructure.persist.mapping;

import com.example.domain.model.AuthorModel;
import com.example.domain.model.BookModel;
import com.example.infrastructure.persist.entity.Author;
import com.example.infrastructure.persist.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InfrastructureMapping {

    @Mapping(target = "id", ignore = true)
    Book toBook(BookModel bookModel);

    @Mapping(target = "id", ignore = true)
    Author toAuthor(AuthorModel authorModel);

    BookModel toBookModel(Book bookEntity);

    void update(@MappingTarget Book bookEntity, BookModel bookModel);

}
