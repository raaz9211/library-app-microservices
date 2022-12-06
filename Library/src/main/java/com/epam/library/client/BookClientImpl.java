package com.epam.library.client;

import com.epam.library.data.BookDTO;
import feign.FeignException;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
@Setter
public class BookClientImpl implements BookClient{
    private static final String DUMMY = "Dummy";
    private  Throwable cause;

    public BookClientImpl(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public List<BookDTO> getAllBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor(DUMMY);
        bookDTO.setPublisher(DUMMY);
        bookDTO.setName(DUMMY);

        return Arrays.asList(bookDTO ,bookDTO);
    }

    @Override
    public BookDTO getBook(int id) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 400) {
            throw (FeignException) cause;
        }else {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setAuthor(DUMMY);
            bookDTO.setPublisher(DUMMY);
            bookDTO.setName(DUMMY);

            return bookDTO;
        }
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        BookDTO bookDTODymmay = new BookDTO();
        bookDTODymmay.setAuthor(DUMMY);
        bookDTODymmay.setPublisher(DUMMY);
        bookDTODymmay.setName(DUMMY);

        return bookDTODymmay;
    }

    @Override
    public void deleteBook(int id) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 400) {
            throw (FeignException) cause;
        }
    }

    @Override
    public BookDTO updateBook(int id, BookDTO bookDTO) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 400) {
            throw (FeignException) cause;
        } else {

            BookDTO bookDTODymmay = new BookDTO();
            bookDTODymmay.setAuthor(DUMMY);
            bookDTODymmay.setPublisher(DUMMY);
            bookDTODymmay.setName(DUMMY);

        return bookDTODymmay;
        }
    }
}
