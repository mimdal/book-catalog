package com.example.app.rest;

import com.example.api.request.Author;
import com.example.api.request.NewBookRequest;
import com.example.api.request.UpdateBookRequest;
import com.example.app.rest.util.ResetDatabase;
import com.example.infrastructure.persist.entity.Book;
import com.example.infrastructure.persist.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
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
public class BookResourceUpdateBookITest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @AfterEach
    public void resetDatabaseToInitialState() {
        ResetDatabase.deleteBookEntities(bookRepository);
    }


    @Test
    @DisplayName("Given: web server starts, and database created by liquibase script and a book catalog created " +
            "When: send a request to update book " +
            "Then: the update operation get done and 204 status code send to the client")
    void testUpdateBookTheBookIsUpdated() throws Exception {
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
        Book bookEntity = bookRepository.save(scarlett);

        String updateAuthor = "Update Alexandra Ripley";
        String updateIsbn = "999-9999999990";
        String updateBookName = "Update Scarlett";

        UpdateBookRequest request = new UpdateBookRequest();
        request.setIsbn(updateIsbn);
        Author margaretMitchell = new Author();
        margaretMitchell.setName(updateAuthor);
        request.setAuthors(new HashSet<>(Collections.singletonList(margaretMitchell)));
        request.setTitle(updateBookName);
        request.setSummary("update summary");

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/books/" + bookEntity.getId())
                .contentType("application/json")
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status()
                        .isNoContent());

        Optional<Book> book = bookRepository.findByIsbnIgnoreCase(updateIsbn);
        assertThat(book.get().getTitle()).isEqualTo(updateBookName);

    }

    @Test
    @DisplayName("Given: web server starts, and database created by liquibase script" +
            "When: send a request to update a book, but book ID was not persisted before " +
            "Then: the book does not updated and 404 status code (not found) send to the client")
    void testUpdateBookTheBookIdCouldNotFind() throws Exception {
        String author = "Alexandra Ripley";
        String isbn = "123-1234567890";
        String bookName = "Scarlett";
        String summary = "Scarlett is a 1991 novel by Alexandra Ripley, written as a sequel to Margaret Mitchell's 1936 novel, " +
                "Gone with the Wind. The book debuted on The New York Times Best Seller list.";

        UpdateBookRequest request = new UpdateBookRequest();
        request.setIsbn(isbn);
        Author margaretMitchell = new Author();
        margaretMitchell.setName(author);
        request.setAuthors(new HashSet<>(Collections.singletonList(margaretMitchell)));
        request.setTitle(bookName);
        request.setSummary(summary);

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/books/1000")
                .contentType("application/json")
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status()
                        .isNotFound());

        Optional<Book> book = bookRepository.findByIsbnIgnoreCase(isbn);
        assertThat(book.isPresent()).isFalse();
    }
}
