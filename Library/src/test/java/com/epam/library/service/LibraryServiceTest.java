package com.epam.library.service;

import com.epam.library.data.Library;
import com.epam.library.data.LibraryDTO;
import com.epam.library.repository.LibraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @InjectMocks
    private  LibraryService libraryService;

    @Mock
    private LibraryRepository libraryRepository;

    LibraryDTO libraryDTO1 = new LibraryDTO();
    LibraryDTO libraryDTO2 = new LibraryDTO();
    Library library1 = new Library();
    Library library2 = new Library();

    @BeforeEach
    void setUp() {
        libraryDTO1.setUsername("username");
        libraryDTO1.setBookId(1);
        libraryDTO2.setUsername("username");
        libraryDTO1.setBookId(2);
        libraryDTO1.setId(1);


    }
    @Test
    void add() {
        when(libraryRepository.save(any())).thenReturn(library1);
        assertNotNull(libraryService.add(libraryDTO1));
    }
    @Test
    void addMapped() {
        when(libraryRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        try {
            assertNotNull(libraryService.add(libraryDTO1));
        }catch (Exception e){
            System.out.println(e);
        }
    }
    @Test
    void addError() {
        when(libraryRepository.save(any())).thenThrow(org.mockito.exceptions.base.MockitoException.class);
        try {
            assertNotNull(libraryService.add(libraryDTO1));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void delete() {

        libraryService.delete(libraryDTO1);
        verify(libraryRepository).delete(any());

    }

    @Test
    void deleteError() {

        doThrow(org.mockito.exceptions.base.MockitoException.class).when(libraryRepository).delete(any());
        try {
            libraryService.delete(libraryDTO1);
            verify(libraryRepository).delete(any());
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    @Test
    void getAllByUsername() {
        when(libraryRepository.findAllByUsername(anyString())).thenReturn(Arrays.asList(library1,library2));
        List<LibraryDTO> libraryDTOS = libraryService.getAllByUsername(anyString());
        assertEquals(2,libraryDTOS.size());

    }

    @Test
    void getByUsernameAndBookId() {
        when(libraryRepository.findByUsernameAndBookId(anyString(),anyInt())).thenReturn(Optional.ofNullable(library1));
        assertNotNull(libraryService.getByUsernameAndBookId(anyString(),anyInt()));
    }
    @Test
    void getByUsernameAndBookIdError() {
        when(libraryRepository.findByUsernameAndBookId(anyString(),anyInt())).thenReturn(Optional.ofNullable(null));
        try {
            assertNotNull(libraryService.getByUsernameAndBookId(anyString(), anyInt()));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void countBook() {
        when(libraryRepository.countByUsername(anyString())).thenReturn(2);
        assertEquals(2,libraryService.countBook(anyString()));
    }
    @Test
    void countBookError() {
        when(libraryRepository.countByUsername(anyString())).thenReturn(3);
        try {
            assertEquals(2,libraryService.countBook(anyString()));
        }catch (Exception e){
            System.out.println(e);
        }
    }

}