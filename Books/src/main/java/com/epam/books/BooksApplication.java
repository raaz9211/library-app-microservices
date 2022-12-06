package com.epam.books;



import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Books", version = "1.0", description = "Book App"))
public class BooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class);

	}


}
