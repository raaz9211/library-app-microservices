package com.epam.library.client;

import com.epam.library.data.UserDTO;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

class UserClientImplTest {

    UserClientImpl userClientImpl=new UserClientImpl(new Throwable());
    UserDTO userDTO = new UserDTO();
    private static final String DUMMY_EMAIL = "dummy@epam.com";
    private static final String DUMMY_NAME = "dummy";


    @BeforeEach
    void setUp() {
        userDTO.setEmail(DUMMY_EMAIL);
        userDTO.setUsername(DUMMY_NAME);
        userDTO.setName(DUMMY_NAME);


    }
    @Test
    void getAllUser() {
        List<UserDTO> usersList= Arrays.asList(userDTO);
        List<UserDTO> users = userClientImpl.getAllUser();
    }

    @Test
    void getUser() {
        userClientImpl.getUser(anyString());
    }
    @Test
    void getBookUser() {
        userClientImpl.setCause(new SampleFeignException(400, ""));
        assertThrows(FeignException.class, () -> userClientImpl.getUser(anyString()));

    }

    @Test
    void addUser() {
        userClientImpl.addUser(any());
    }

    @Test
    void deleteUser() {
        userClientImpl.deleteUser(anyString());
    }
    @Test
    void deleteUserFail() {
        userClientImpl.setCause(new SampleFeignException(400, ""));
        assertThrows(FeignException.class, () -> userClientImpl.deleteUser(anyString()));

    }


    @Test
    void updateUser() {
        userClientImpl.updateUser(anyString(),any());
    }
    @Test
    void updateUserFail() {
        userClientImpl.setCause(new SampleFeignException(400, ""));
        assertThrows(FeignException.class, () -> userClientImpl.updateUser(anyString(),any()));

    }

}