package com.example.app.rest;

import com.example.api.request.Author;
import com.example.api.request.NewBookRequest;
import com.example.infrastructure.persist.entity.Book;
import com.example.infrastructure.persist.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"classpath:application-test.properties"})
class BookResourceITest {

    @Autowired private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();


    @Test
    @DisplayName("Given: web server starts, and database created by liquibase script " +
            "When: send a request to add new book " +
            "Then: the new book persist and 201 status code send to the client")
    void testNewBookTheNewBookPersisted() throws Exception {
        String author = "Margaret Mitchell";
        String isbn = "978-1416548942";
        String bookName = "Gone with the Wind";

        NewBookRequest request = new NewBookRequest();
        request.setIsbn(isbn);
        Author margaretMitchell = new Author();
        margaretMitchell.setName(author);
        request.setAuthors(new HashSet<>(Collections.singletonList(margaretMitchell)));
        request.setTitle(bookName);
        request.setSummary("summary");
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/books")
                .contentType("application/json")
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status()
                        .isCreated());

        Optional<Book> book = bookRepository.findByIsbnIgnoreCase(isbn);
        assertThat(book.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Given: web server starts, and database created by liquibase script and a book is saved" +
            "When: send a request to add book that is saved in database " +
            "Then: the book does not persist and 409 status code (conflict) send to the client")
    void testNewBookTheBookPersistedBefore() throws Exception {
        String author = "Alexandra Ripley";
        String isbn = "123-1234567890";
        String bookName = "Scarlett";
        String summary = "Scarlett is a 1991 novel by Alexandra Ripley, written as a sequel to Margaret Mitchell's 1936 novel, " +
                "Gone with the Wind. The book debuted on The New York Times Best Seller list.";

        Book scarlett = new Book();
        scarlett.setTitle(bookName);
        scarlett.setIsbn(isbn);
        scarlett.setSummary(summary);
        com.example.infrastructure.persist.entity.Author alexandraRipley = new com.example.infrastructure.persist.entity.Author();
        alexandraRipley.setName(author);
        scarlett.setAuthors(new HashSet<>(Collections.singletonList(alexandraRipley)));
        bookRepository.save(scarlett);

        NewBookRequest request = new NewBookRequest();
        request.setIsbn(isbn);
        Author margaretMitchell = new Author();
        margaretMitchell.setName(author);
        request.setAuthors(new HashSet<>(Collections.singletonList(margaretMitchell)));
        request.setTitle(bookName);
        request.setSummary("another summary");

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/books")
                .contentType("application/json")
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status()
                        .isConflict());

        Optional<Book> book = bookRepository.findByIsbnIgnoreCase(isbn);
        assertThat(book.get().getSummary()).isEqualTo(summary);
    }

}