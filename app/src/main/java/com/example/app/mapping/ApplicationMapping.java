package com.example.app.mapping;

import com.example.api.model.Author;
import com.example.api.request.GetBooksRequest;
import com.example.api.request.NewBookRequest;
import com.example.api.request.UpdateBookRequest;
import com.example.api.response.GetBooksResponse;
import com.example.api.response.NewBookResponse;
import com.example.domain.model.AuthorModel;
import com.example.domain.model.BookModel;
import com.example.domain.model.BookSearchModel;
import com.example.domain.model.BookSearchModelResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ApplicationMapping {

    @Mapping(target = "id", ignore = true)
    BookModel toBookModel(NewBookRequest request);

    BookModel toBookModel(Long id, UpdateBookRequest request);

    AuthorModel toAuthorModel(Author author);

    NewBookResponse toNewBookResponse(BookModel response);

    GetBooksRequest toGetBooksRequest(Integer page, Integer size, String bookTitle, String authorName);

    BookSearchModel toBookSearchModel(GetBooksRequest request);

    GetBooksResponse toGetBooksResponse(BookSearchModelResult books);

}
