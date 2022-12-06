package com.epam.library.data;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
public class BookDTO {
    int id;
    @Size(min = 5, max = 20, message = "Title should be between 5 and 20 characters length")
    String name;
    @NotEmpty(message = "publisher should not be empty")
    String publisher;
    @NotEmpty(message = "author should not be empty")
    String author;

}
