package com.epam.library.controller;

import com.epam.library.client.BookClient;
import com.epam.library.client.UserClient;
import com.epam.library.data.BookDTO;
import com.epam.library.data.LibraryDTO;
import com.epam.library.data.UserDTO;
import com.epam.library.exception.BookIssueLimitexceedException;
import com.epam.library.exception.BookMappedWithUserException;
import com.epam.library.exception.BookNotMappedWithUserException;
import com.epam.library.exception.BookNotReleasedWithUserException;
import com.epam.library.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserClient userClient;
    @MockBean
    BookClient bookClient;

    @MockBean
    LibraryService libraryService;
    @Autowired
    ObjectMapper mapper;
    UserDTO userDTO = new UserDTO();
    BookDTO bookDTO = new BookDTO();
    @BeforeEach
    void setUp() {
        userDTO.setUsername("1234");
        userDTO.setName("raj");
        userDTO.setEmail("raj@1234");
        bookDTO.setAuthor("Audthor");
        bookDTO.setName("Namea");
        bookDTO.setPublisher("Publisher");

    }

    @Test
    void getAllBook() throws Exception{
        when(bookClient.getAllBook()).thenReturn(Arrays.asList(new BookDTO(),new BookDTO()));
        mockMvc.perform(get("http://localhost:8080/library/books"))
                .andExpect(status().isAccepted()).andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void getBook() throws Exception{
        when(bookClient.getBook(anyInt())).thenReturn(new BookDTO());

        mockMvc.perform(get("http://localhost:8080/library/books/123"))
                .andExpect(status().isAccepted());

    }

    @Test
    void addBook() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("http://localhost:8080/library/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(bookDTO));

        mockMvc.perform(mockRequest).andExpect(status().isCreated());

    }

    @Test
    void addBookError() throws Exception {
        bookDTO.setAuthor("");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("http://localhost:8080/library/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(bookDTO));

        mockMvc.perform(mockRequest).andExpect(status().isBadRequest());

    }

    @Test
    void deleteBook() throws Exception{
        mockMvc.perform(delete("http://localhost:8080/library/books/1234")).andExpect(status().isNoContent());

    }

    @Test
    void updateBook()throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("http://localhost:8080/library/books/123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(bookDTO));

        mockMvc.perform(mockRequest).andExpect(status().isCreated());
    }
    @Test
    void getAllUser() throws Exception{
//        when(userClient.getAllUser()).thenReturn(new ResponseEntity<>(Arrays.asList(new UserDTO(),new UserDTO()), HttpStatus.CREATED));
        when(userClient.getAllUser()).thenReturn(Arrays.asList(new UserDTO(),new UserDTO()));
        mockMvc.perform(get("http://localhost:8080/library/users"))
                .andExpect(status().isAccepted()).andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void getUser() throws Exception{
        when(userClient.getUser(anyString())).thenReturn(new UserDTO());

        mockMvc.perform(get("http://localhost:8080/library/users/123"))
                .andExpect(status().isAccepted());

    }
    @Test
    void getUserError() throws Exception{

        doThrow(FeignException.class).when(userClient).getUser(anyString());

        mockMvc.perform(get("/library/users/avs"))
                .andExpect(status().isBadRequest());

    }




    @Test
    void addUser() throws Exception{

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("http://localhost:8080/library/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(userDTO));

        mockMvc.perform(mockRequest).andExpect(status().isCreated());

    }

    @Test
    void deleteUser() throws Exception{
        mockMvc.perform(delete("http://localhost:8080/library/users/1234")).andExpect(status().isNoContent());

    }

    @Test
    void updateUser() throws Exception {


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("http://localhost:8080/library/users/raj123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userDTO));

        mockMvc.perform(mockRequest).andExpect(status().isCreated());
    }

    @Test
    void issueBook() throws Exception{
        when(libraryService.countBook(anyString())).thenReturn(2);
        mockMvc.perform(post("http://localhost:8080/library/users/1234/books/12")).andExpect(status().isCreated());

    }
    @Test
    void issueBookMapped() throws Exception{
        when(libraryService.add(any())).thenThrow(BookMappedWithUserException.class);
        when(libraryService.countBook(anyString())).thenReturn(2);
        mockMvc.perform(post("http://localhost:8080/library/users/1234/books/12")).andExpect(status().isBadRequest());

    }
    @Test
    void issueBookEexceed() throws Exception{
        when(libraryService.add(any())).thenThrow(BookIssueLimitexceedException.class);
        when(libraryService.countBook(anyString())).thenReturn(2);
        mockMvc.perform(post("http://localhost:8080/library/users/1234/books/12")).andExpect(status().isBadRequest());

    }


    @Test
    void cantIssueBook() throws Exception{
        when(libraryService.add(any())).thenThrow(BookNotMappedWithUserException.class);
        when(libraryService.countBook(anyString())).thenReturn(2);
        mockMvc.perform(post("http://localhost:8080/library/users/1234/books/12")).andExpect(status().isBadRequest());

    }


    @Test
    void releaseBook()throws Exception {
        when(libraryService.getByUsernameAndBookId(anyString(),anyInt())).thenReturn(new LibraryDTO());
        mockMvc.perform(delete("http://localhost:8080/library/users/1234/books/12")).andExpect(status().isNoContent());

    }
    @Test
    void cantReleaseBook()throws Exception {

        when(libraryService.getByUsernameAndBookId(anyString(),anyInt())).thenReturn(new LibraryDTO());
        doThrow(BookNotReleasedWithUserException.class).when(libraryService).delete(any());

        mockMvc.perform(delete("http://localhost:8080/library/users/1234/books/12")).andExpect(status().isBadRequest());

    }



}