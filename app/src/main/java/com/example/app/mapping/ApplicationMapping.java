package com.example.app.mapping;

import com.example.api.request.Author;
import com.example.api.request.NewBookRequest;
import com.example.api.response.NewBookResponse;
import com.example.domain.model.AuthorModel;
import com.example.domain.model.BookModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ApplicationMapping {

    @Mapping(target = "id", ignore = true)
    BookModel toBookModel(NewBookRequest request);

    AuthorModel toAuthorModel(Author author);

    NewBookResponse toNewBookResponse(BookModel response);

}
