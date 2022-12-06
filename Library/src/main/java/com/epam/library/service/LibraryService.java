package com.epam.library.service;

import com.epam.library.data.Library;
import com.epam.library.data.LibraryDTO;
import com.epam.library.exception.BookIssueLimitexceedException;
import com.epam.library.exception.BookMappedWithUserException;
import com.epam.library.exception.BookNotMappedWithUserException;
import com.epam.library.exception.BookNotReleasedWithUserException;
import com.epam.library.repository.LibraryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LibraryService {
    private static final Logger LOGGER = LogManager.getLogger(LibraryService.class);
    @Autowired
    LibraryRepository libraryRepository;
    ModelMapper modelMapper = new ModelMapper();

    public LibraryDTO add(LibraryDTO libraryDTO) {

        Library library;
        try {

            library = libraryRepository.save(modelMapper.map(libraryDTO, Library.class));
            LOGGER.info("Book Issued");
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("{} is mapped with other user ",libraryDTO.getBookId()  );
            throw new BookMappedWithUserException(libraryDTO.getBookId() + " is mapped with other user ");

        } catch (Exception e) {
            LOGGER.error("Book not Issued ");
            throw new BookNotMappedWithUserException(libraryDTO.getBookId() + " not is issued with  " + libraryDTO.getUsername());

        }

        return modelMapper.map(library, LibraryDTO.class);
    }

    public void delete(LibraryDTO libraryDTO) {


        try {
            Library library = modelMapper.map(libraryDTO, Library.class);
            libraryRepository.delete(library);
            LOGGER.info("Book Released");
        } catch (Exception e) {
            LOGGER.error("Book not Released ");
            throw new BookNotReleasedWithUserException(libraryDTO.getBookId() + " not is Released with  " + libraryDTO.getUsername());

        }

    }

    public List<LibraryDTO> getAllByUsername(String username) {


        List<Library> questions = libraryRepository.findAllByUsername(username);
        return modelMapper.map(questions, new TypeToken<List<LibraryDTO>>() {
        }.getType());

    }

    public LibraryDTO getByUsernameAndBookId(String username, int bookId) {

        return modelMapper.map(libraryRepository.findByUsernameAndBookId(username, bookId)
                .orElseThrow(() -> new BookNotMappedWithUserException(bookId + " is not mapped with " + username)), LibraryDTO.class);
    }

    public int countBook(String username) {

        int count = libraryRepository.countByUsername(username);
        if (count >= 3) {
            throw new BookIssueLimitexceedException(username + " hand 3 book already");
        }
        return count;
    }
}




