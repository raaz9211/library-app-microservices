package com.epam.books.service;

import com.epam.books.data.Book;
import com.epam.books.data.BookDTO;
import com.epam.books.exception.BookNotFoundException;
import com.epam.books.exception.BookNotRemovedException;
import com.epam.books.exception.BookNotSavedException;
import com.epam.books.repository.BookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookService {
    private static final Logger LOGGER = LogManager.getLogger(BookService.class);
    @Autowired
    BookRepository bookRepository;
    ModelMapper modelMapper = new ModelMapper();

    public BookDTO add(BookDTO bookDTO) {

        Book book;
        try {
            book = bookRepository.save(modelMapper.map(bookDTO, Book.class));
            LOGGER.info("Book added");
        } catch (Exception e) {
            LOGGER.error("Book not added ");
            throw new BookNotSavedException("Book not added ");
        }

        return modelMapper.map(book, BookDTO.class);
    }

    public void delete(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);

        try {
            bookRepository.delete(book);
            LOGGER.info("Question removed ");
        } catch (Exception e) {

            LOGGER.error(e);
            LOGGER.error("Enter valid question code ");
            throw new BookNotRemovedException("Book not removed ");


        }
    }



    public List<BookDTO> getAll() {

        List<Book> questions = (List<Book>) bookRepository.findAll();
        return modelMapper.map(questions, new TypeToken<List<BookDTO>>() {
        }.getType());
    }

    public BookDTO get(int id) {

        return modelMapper.map(bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not found with id " + id)), BookDTO.class);
    }

}