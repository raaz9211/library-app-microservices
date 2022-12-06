package com.epam.users.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    String username;
    String name;
    String email;

}
