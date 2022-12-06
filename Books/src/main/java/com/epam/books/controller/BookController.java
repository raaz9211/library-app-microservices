package com.epam.books.controller;

import com.epam.books.data.BookDTO;
import com.epam.books.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;


    @Operation(description = "Get all book")
    @ApiResponse(responseCode = "202",description = "Successfully fetched")
    @GetMapping("books/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("id") int id) {
        return new ResponseEntity<>(bookService.get(id), HttpStatus.ACCEPTED);
    }

    @Operation(description = "Get book")
    @ApiResponse(responseCode = "202",description = "Successfully fetched")
    @ApiResponse(responseCode = "400",description = "not Found")
    @GetMapping("books")
    public ResponseEntity<List<BookDTO>> getAllBook() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.ACCEPTED);
    }


    @Operation(description = "Create book")
    @ApiResponse(responseCode = "201",description = "Successfully created")
    @ApiResponse(responseCode = "400",description = "Not created")
    @PostMapping("books")
    public ResponseEntity<BookDTO> addBook(@RequestBody @Valid BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.add(bookDTO), HttpStatus.CREATED);
    }


    @Operation(description = "Delete book")
    @ApiResponse(responseCode = "204",description = "Successfully deleted")
    @ApiResponse(responseCode = "400",description = "Not deleted")
    @DeleteMapping("books/{id}")
    public ResponseEntity<Integer> deleteBook(@PathVariable("id") int id) {
        BookDTO bookDTO = bookService.get(id);
        bookService.delete(bookDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(description = "Update book")
    @ApiResponse(responseCode = "201",description = "Successfully updated")
    @ApiResponse(responseCode = "400",description = "Not updated")
    @PutMapping("books/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") int id, @RequestBody @Valid BookDTO bookDTO) {
        bookService.get(id);
        bookDTO.setId(id);
        bookService.add(bookDTO);
        return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);


    }
}
