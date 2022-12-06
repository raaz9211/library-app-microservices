package com.epam.library.controller;

import com.epam.library.client.BookClient;
import com.epam.library.client.UserClient;
import com.epam.library.data.BookDTO;
import com.epam.library.data.LibraryDTO;
import com.epam.library.data.UserBookDTO;
import com.epam.library.data.UserDTO;
import com.epam.library.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LibraryController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserClient userClient;

    @Autowired
    BookClient bookClient;
    ModelMapper modelMapper = new ModelMapper();


    @Autowired
    LibraryService libraryService;
//
//    @GetMapping("/library/books")
//    public ResponseEntity<List<BookDTO>> getAllBook() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> entity = new HttpEntity<String>(headers);
//        List<BookDTO> bookDTOS = restTemplate.exchange("http://localhost:9000/books", HttpMethod.GET, entity, List.class).getBody();
//        return new ResponseEntity<>(bookDTOS, HttpStatus.ACCEPTED);
//    }
//
//    @GetMapping("/library/books/{id}")
//    public ResponseEntity<BookDTO> getBook(@PathVariable("id") int id) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> entity = new HttpEntity<String>(headers);
//        BookDTO bookDTO = restTemplate.exchange("http://localhost:9000/books/" + id, HttpMethod.GET, entity, BookDTO.class).getBody();
//        return new ResponseEntity<>(bookDTO, HttpStatus.ACCEPTED);
//    }
//
//
//    @PostMapping("/library/books")
//    public ResponseEntity<BookDTO> addBook(@RequestBody @Valid BookDTO bookDTO) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<BookDTO> entity = new HttpEntity<>(bookDTO, headers);
//        BookDTO foundBookDTO = restTemplate.exchange("http://localhost:9000/books/", HttpMethod.POST, entity, BookDTO.class).getBody();
//
//        return new ResponseEntity<>(foundBookDTO, HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/library/books/{id}")
//    public ResponseEntity deleteBook(@PathVariable("id") int id) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> entity = new HttpEntity<String>(headers);
//        restTemplate.exchange("http://localhost:9000/books/" + id, HttpMethod.DELETE, entity, BookDTO.class).getBody();
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping("library/books/{id}")
//    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") int id, @RequestBody @Valid BookDTO bookDTO) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<BookDTO> entity = new HttpEntity<>(bookDTO, headers);
//        BookDTO foundbookDTO = restTemplate.exchange("http://localhost:9000/books/" + id, HttpMethod.PUT, entity, BookDTO.class).getBody();
//
//        return new ResponseEntity<>(foundbookDTO, HttpStatus.CREATED);
//
//    }



    @Operation(description = "Get all book")
    @ApiResponse(responseCode = "202",description = "Successfully fetched")
    @GetMapping("/library/books")
    public ResponseEntity<List<BookDTO>> getAllBook() {
        return new ResponseEntity<>(bookClient.getAllBook(), HttpStatus.ACCEPTED);
    }


    @Operation(description = "Get book")
    @ApiResponse(responseCode = "202",description = "Successfully fetched")
    @ApiResponse(responseCode = "400",description = "not Found")
    @GetMapping("/library/books/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("id") int id) {
        return new ResponseEntity<>(bookClient.getBook(id), HttpStatus.ACCEPTED);
    }


    @Operation(description = "Create book")
    @ApiResponse(responseCode = "201",description = "Successfully created")
    @ApiResponse(responseCode = "400",description = "Not created")
    @PostMapping("/library/books")
    public ResponseEntity<BookDTO> addBook(@RequestBody @Valid BookDTO bookDTO) {

        return new ResponseEntity<>(bookClient.addBook(bookDTO), HttpStatus.CREATED);
    }


    @Operation(description = "Delete book")
    @ApiResponse(responseCode = "204",description = "Successfully deleted")
    @ApiResponse(responseCode = "400",description = "Not deleted")
    @DeleteMapping("/library/books/{id}")
    public ResponseEntity<Integer> deleteBook(@PathVariable("id") int id) {
        bookClient.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(description = "Update book")
    @ApiResponse(responseCode = "201",description = "Successfully updated")
    @ApiResponse(responseCode = "400",description = "Not updated")
    @PutMapping("library/books/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") int id, @RequestBody @Valid BookDTO bookDTO) {
        bookClient.updateBook(id,bookDTO);
        return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);

    }



    @Operation(description = "Get all user")
    @ApiResponse(responseCode = "202",description = "Successfully fetched")
    @GetMapping("/library/users")
    public ResponseEntity<List<UserDTO>> getAllUser() {

        return new ResponseEntity<>(userClient.getAllUser(), HttpStatus.ACCEPTED);
    }


    @Operation(description = "Get user")
    @ApiResponse(responseCode = "202",description = "Successfully fetched")
    @ApiResponse(responseCode = "400",description = "not Found")
    @GetMapping("/library/users/{username}")
    ResponseEntity<UserBookDTO> getUser(@PathVariable String username) {
        UserBookDTO userBookDTO = modelMapper.map(userClient.getUser(username), UserBookDTO.class);

        List<LibraryDTO> libraryDtos = libraryService.getAllByUsername(username);
        List<BookDTO> bookDtos = new ArrayList<>();
        libraryDtos.forEach(libraryDto -> bookDtos.add(bookClient.getBook(libraryDto.getBookId())));
        userBookDTO.setBookDTOS(bookDtos);
        return new ResponseEntity<>(userBookDTO, HttpStatus.ACCEPTED);
    }


    @Operation(description = "Create user")
    @ApiResponse(responseCode = "201",description = "Successfully created")
    @ApiResponse(responseCode = "400",description = "Not created")
    @PostMapping("/library/users")
    public ResponseEntity<UserDTO> addUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userClient.addUser(userDTO), HttpStatus.CREATED);
    }


    @Operation(description = "Delete user")
    @ApiResponse(responseCode = "204",description = "Successfully deleted")
    @ApiResponse(responseCode = "400",description = "Not deleted")
    @DeleteMapping("/library/users/{username}")
    public ResponseEntity<Integer> deleteUser(@PathVariable("username") String username) {
        userClient.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(description = "Update user")
    @ApiResponse(responseCode = "201",description = "Successfully updated")
    @ApiResponse(responseCode = "400",description = "Not updated")
    @PutMapping("/library/users/{username}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("username") String username, @RequestBody @Valid UserDTO userDTO) {
        userClient.updateUser(username, userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);


    }


    @Operation(description = "Book issue ")
    @ApiResponse(responseCode = "201",description = "Successfully issued")
    @ApiResponse(responseCode = "400",description = "Not issued")
    @PostMapping("/library/users/{username}/books/{bookId}")
    public ResponseEntity<LibraryDTO> issueBook(@PathVariable("username") String username, @PathVariable("bookId") int bookId) {
        userClient.getUser(username);
        libraryService.countBook(username);
        bookClient.getBook(bookId);
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setBookId(bookId);
        libraryDTO.setUsername(username);
        return new ResponseEntity<>(libraryService.add(libraryDTO), HttpStatus.CREATED);
    }



    @Operation(description = "Book release ")
    @ApiResponse(responseCode = "201",description = "Successfully released")
    @ApiResponse(responseCode = "400",description = "Not released")
    @DeleteMapping("/library/users/{username}/books/{bookId}")
    public ResponseEntity<LibraryDTO> releaseBook(@PathVariable("username") String username, @PathVariable("bookId") int bookId) {
        LibraryDTO libraryDTO = libraryService.getByUsernameAndBookId(username, bookId);
        libraryService.delete(libraryDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
