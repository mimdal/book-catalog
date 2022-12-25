package com.example.api.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class NewBookRequest {

    @NotEmpty
    private String title;

    private String summary;

    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$", message = "")
    private String isbn;

    @NotNull
    private Set<Author> authors;

}