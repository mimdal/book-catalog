package com.example.app.rest;

import com.example.api.request.NewBookRequest;
import com.example.api.request.UpdateBookRequest;
import com.example.api.response.NewBookResponse;
import com.example.api.response.UpdateBookResponse;
import com.example.app.mapping.ApplicationMapping;
import com.example.domain.model.BookModel;
import com.example.domain.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

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
    public ResponseEntity<UpdateBookResponse> updateBook(@PathVariable Integer id, @Valid @RequestBody UpdateBookRequest request) {

        service.updateBook(mapping.toBookModel(id, request));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
