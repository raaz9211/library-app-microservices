package com.epam.library.client;

import com.epam.library.data.BookDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookClientImplTest {



    BookClientImpl bookClientImpl=new BookClientImpl(new Throwable());
    BookDTO bookDTO=new BookDTO();

    private static final String DUMMY = "Dummy";

    @BeforeEach
    void setUp() {
        bookDTO.setAuthor(DUMMY);
        bookDTO.setPublisher(DUMMY);
        bookDTO.setName(DUMMY);

    }

    @Test
    void getAllBook() {
        List<BookDTO> books = Arrays.asList(bookDTO);
        List<BookDTO> book = bookClientImpl.getAllBook();
    }

    @Test
    void getBook() {
        BookDTO bookDtoEntity=bookClientImpl.getBook(anyInt());
    }
    @Test
    void getBookFail() {
        bookClientImpl.setCause(new SampleFeignException(400, ""));
        assertThrows(FeignException.class, () -> bookClientImpl.getBook(anyInt()));

    }

    @Test
    void addBook() {
        bookClientImpl.addBook(bookDTO);
    }

    @Test
    void deleteBook() {
        bookClientImpl.deleteBook(anyInt());
    }
    @Test
    void deleteBookFail() {
        bookClientImpl.setCause(new SampleFeignException(400, ""));
        assertThrows(FeignException.class, () -> bookClientImpl.deleteBook(anyInt()));
    }


    @Test
    void updateBook() {
        BookDTO bookDtoEntity=bookClientImpl.updateBook(1, bookDTO);
    }
    @Test
    void updateBookFail() {
        bookClientImpl.setCause(new SampleFeignException(400, ""));
        assertThrows(FeignException.class, () -> bookClientImpl.updateBook(anyInt(),bookDTO));

    }

}