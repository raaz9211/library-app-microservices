package com.epam.library.data;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
public class UserDTO {

    @NotEmpty(message = "username should not be empty")
    String username;
    @NotEmpty(message = "name should not be empty")
    String name;
    @NotEmpty(message = "email should not be empty")
    String email;




}
