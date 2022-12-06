package com.epam.users;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Users", version = "1.0", description = "User App"))
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class);

	}

}
