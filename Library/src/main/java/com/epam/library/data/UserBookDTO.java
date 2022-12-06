package com.epam.library.data;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Getter
@Setter
public class UserBookDTO {

    @NotEmpty(message = "username should not be empty")
    String username;
    @NotEmpty(message = "name should not be empty")
    String name;
    @NotEmpty(message = "email should not be empty")
    String email;
    List<BookDTO> bookDTOS;




}
