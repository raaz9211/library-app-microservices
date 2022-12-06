package com.epam.users.service;

import com.epam.users.data.User;
import com.epam.users.data.UserDTO;
import com.epam.users.repository.UserRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    User user = new User();
    UserDTO userDTO = new UserDTO();


    @BeforeEach
    void setUp() {
        user.setUsername("1234");
        user.setName("raj");
        user.setEmail("raj@1234");
        userDTO.setUsername("1234");
        userDTO.setName("raj");
        userDTO.setEmail("raj@1234");
    }
    @Test
    void add() {
        when(userRepository.save(any())).thenReturn(user);
        assertNotNull(userService.add(userDTO));


    }
    @Test
    void addError() {

        doThrow(org.mockito.exceptions.base.MockitoException.class).when(userRepository).save(any());
        try {


            assertNotNull(userService.add(userDTO));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void delete() {
        userService.delete(userDTO);
        verify(userRepository).delete(any());

    }
    @Test
    void deleteError() {

        doThrow(org.mockito.exceptions.base.MockitoException.class).when(userRepository).delete(any());
        try {
            userService.delete(userDTO);
            verify(userRepository).delete(any());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void getAll() {
        List<User> usersMock = Arrays.asList(user, new User());
        List<UserDTO> userDTOSMock = Arrays.asList(userDTO, new UserDTO());

        given(userRepository.findAll()).willReturn(usersMock);
        List<UserDTO> bookDTOS = userService.getAll();
        assertEquals(2, bookDTOS.size());

    }

    @Test
    void get () {
        given(userRepository.findById(anyString())).willReturn(Optional.ofNullable(user));
        assertEquals(user.getName(), userService.get(anyString()).getName());

    }        @Test
    void getNull () {
        given(userRepository.findById(anyString())).willReturn(Optional.ofNullable(null));
        UserDTO foundUser = null;
        try {
            foundUser = userService.get(anyString());
        }catch (Exception e){
            System.out.println(e);
        }
        assertNull(foundUser);

    }
}