package com.example.api.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class UpdateBookRequest {

    @NotEmpty
    private String title;

    private String summary;

    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$",
            message = "checking the validity of ISBN failed, it can be in either the older ISBN-10 or the current " +
                    "ISBN-13 format. (e.g. 978-3-16-148410-0, 0-596-00681-0)")
    private String isbn;

    @NotNull
    private Set<Author> authors;

}
