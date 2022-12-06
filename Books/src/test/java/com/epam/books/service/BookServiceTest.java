package com.epam.books.service;

import com.epam.books.data.Book;
import com.epam.books.data.BookDTO;
import com.epam.books.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    Book book = new Book();
    BookDTO bookDTO = new BookDTO();


    @BeforeEach
    void setUp() {
        book.setAuthor("Author");
        book.setName("Name");
        book.setPublisher("Publisher");

        bookDTO.setAuthor("Author");
        bookDTO.setName("Name");
        bookDTO.setPublisher("Publisher");
    }


    @Test
    void add() {

        when(bookRepository.save(any())).thenReturn(book);
        assertNotNull(bookService.add(bookDTO));
    }

    @Test
    void addError() {

        doThrow(org.mockito.exceptions.base.MockitoException.class).when(bookRepository).save(null);
        try {


            assertNull(bookService.add(bookDTO));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void delete() {
        bookService.delete(bookDTO);
        verify(bookRepository).delete(any());
    }

    @Test
    void deleteError() {

        doThrow(org.mockito.exceptions.base.MockitoException.class).when(bookRepository).delete(any());
        try {
            bookService.delete(bookDTO);
            verify(bookRepository).delete(any());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void getAll() {
        List<Book> booksMock = Arrays.asList(book, new Book());
        List<BookDTO> bookDTOSMock = Arrays.asList(bookDTO, new BookDTO());

        given(bookRepository.findAll()).willReturn(booksMock);
        List<BookDTO> bookDTOS = bookService.getAll();
        assertEquals(2, bookDTOS.size());
    }

    @Test
    void get() {
        given(bookRepository.findById(anyInt())).willReturn(Optional.ofNullable(book));
        assertEquals(book.getName(), bookService.get(anyInt()).getName());

    }

    @Test
    void getNull() {
        given(bookRepository.findById(anyInt())).willReturn(Optional.ofNullable(null));
        BookDTO foundBook = null;
        try {
            foundBook = bookService.get(anyInt());
        } catch (Exception e) {
            System.out.println(e);
        }
        assertNull(foundBook);

    }
}