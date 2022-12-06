package com.epam.users.controller;

import com.epam.users.data.UserDTO;
import com.epam.users.exception.UserNotFoundException;
import com.epam.users.exception.UserNotRemovedException;
import com.epam.users.exception.UserNotSavedException;
import com.epam.users.service.UserService;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
;
    @Autowired
    ObjectMapper mapper;
    UserDTO userDTO = new UserDTO();

    @BeforeEach
    void setUp() {
        userDTO.setUsername("1234");
        userDTO.setName("raj");
        userDTO.setEmail("raj@1234");
    }
    @Test
    void getUser() throws Exception{
        mockMvc.perform(get("/users/123"))
                .andExpect(status().isAccepted());

    }
    @Test
    void getUserError() throws Exception{
        doThrow(UserNotFoundException.class).when(userService).get(anyString());

        mockMvc.perform(get("/users/1213"))
                .andExpect(status().isBadRequest());

    }



    @Test
    void getAllUser() throws Exception {
        when(userService.getAll()).thenReturn(Arrays.asList(new UserDTO(),new UserDTO()));
        mockMvc.perform(get("/users"))
                .andExpect(status().isAccepted()).andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void addUser() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(userDTO));

        mockMvc.perform(mockRequest).andExpect(status().isCreated());

    }
    @Test
    void addValidUser() throws Exception {
        userDTO.setUsername("");
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(userDTO));

        mockMvc.perform(mockRequest).andExpect(status().isBadRequest());

    }
    @Test
    void addUserError() throws Exception{
        doThrow(UserNotSavedException.class).when(userService).add(any());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(userDTO));

        mockMvc.perform(mockRequest).andExpect(status().isBadRequest());

    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/1234")).andExpect(status().isNoContent());

    }


    @Test
    void deleteUserError() throws Exception {
        doThrow(UserNotRemovedException.class).when(userService).delete(any());

        mockMvc.perform(delete("/users/raj9211")).andExpect(status().isBadRequest());
    }

    @Test
    void updateUsers() throws Exception {
        when(userService.get(anyString())).thenReturn(userDTO);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/users/raj123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userDTO));

        mockMvc.perform(mockRequest).andExpect(status().isCreated());
    }
}