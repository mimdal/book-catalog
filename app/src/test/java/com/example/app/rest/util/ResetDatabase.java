package com.example.app.rest.util;

import com.example.infrastructure.persist.repository.BookRepository;

public class ResetDatabase {

    public static void deleteBookEntities(BookRepository repository) {
        repository.deleteAllInBatch();
    }
}
