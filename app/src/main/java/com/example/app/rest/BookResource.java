package com.example.app.rest;

import com.example.api.request.GetBooksRequest;
import com.example.api.request.NewBookRequest;
import com.example.api.request.UpdateBookRequest;
import com.example.api.response.GetBooksResponse;
import com.example.api.response.NewBookResponse;
import com.example.api.response.UpdateBookResponse;
import com.example.app.mapping.ApplicationMapping;
import com.example.domain.model.BookModel;
import com.example.domain.model.BookSearchModelResult;
import com.example.domain.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookResource {

    private final BookService service;

    private final ApplicationMapping mapping;

    @PostMapping
    public ResponseEntity<NewBookResponse> newBook(@Valid @RequestBody NewBookRequest request) {

        BookModel response = service.newBook(mapping.toBookModel(request));
        NewBookResponse newBookResponse = mapping.toNewBookResponse(response);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response).toUri())
                .body(newBookResponse);

    }

    @PutMapping(("/{id}"))
    public ResponseEntity<UpdateBookResponse> updateBook(@NotNull @PathVariable Long id,
                                                         @Valid @RequestBody UpdateBookRequest request) {

        service.updateBook(mapping.toBookModel(id, request));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(("/{id}"))
    public ResponseEntity<UpdateBookResponse> deleteBook(@NotNull @PathVariable Long id) {

        service.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<GetBooksResponse> getBooks(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "100", required = false) int size,
            @RequestParam(value = "book-title", required = false) String bookTitle,
            @RequestParam(value = "author-name", required = false) String authorName) {

        GetBooksRequest request = mapping.toGetBooksRequest(page, size, bookTitle, authorName);
        BookSearchModelResult result = service.getBooks(mapping.toBookSearchModel(request));
        GetBooksResponse response = mapping.toGetBooksResponse(result);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
