package com.epam.books.controller;

import com.epam.books.data.BookDTO;
import com.epam.books.exception.BookNotFoundException;
import com.epam.books.exception.BookNotRemovedException;
import com.epam.books.exception.BookNotSavedException;
import com.epam.books.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    BookDTO bookDTO = new BookDTO();
    @Autowired
    ObjectMapper mapper;


    @BeforeEach
    void setUp() {
        bookDTO.setAuthor("Audthor");
        bookDTO.setName("Namea");
        bookDTO.setPublisher("Publisher");

    }

    @Test
    void getBook() throws Exception {
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isAccepted());

    }

    @Test
    void getBookError() throws Exception {
        doThrow(BookNotFoundException.class).when(bookService).get(anyInt());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isBadRequest());

    }


    @Test
    void getAllBook() throws Exception {
        when(bookService.getAll()).thenReturn(Arrays.asList(new BookDTO(), new BookDTO()));
        mockMvc.perform(get("/books"))
                .andExpect(status().isAccepted()).andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void addBook() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(bookDTO));

        mockMvc.perform(mockRequest).andExpect(status().isCreated());

    }

    @Test
    void addBookError() throws Exception {
        doThrow(BookNotSavedException.class).when(bookService).add(any());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(bookDTO));

        mockMvc.perform(mockRequest).andExpect(status().isBadRequest());

    }

    @Test
    void addBookVaid() throws Exception {
        bookDTO.setName("a");
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(bookDTO));

        mockMvc.perform(mockRequest).andExpect(status().isBadRequest());

    }

    @Test
    void deleteBook() throws Exception {
        mockMvc.perform(delete("/books/1")).andExpect(status().isNoContent());
    }

    @Test
    void deleteBookError() throws Exception {
        doThrow(BookNotRemovedException.class).when(bookService).delete(any());

        mockMvc.perform(delete("/books/1")).andExpect(status().isBadRequest());
    }

    @Test
    void updateBook() throws Exception {
        when(bookService.get(1)).thenReturn(bookDTO);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(bookDTO));

        mockMvc.perform(mockRequest).andExpect(status().isCreated());
    }
}