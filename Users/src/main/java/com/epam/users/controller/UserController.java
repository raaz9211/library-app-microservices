package com.epam.users.controller;

import com.epam.users.data.UserDTO;
import com.epam.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Operation(description = "Get user")
    @ApiResponse(responseCode = "202",description = "Successfully fetched")
    @ApiResponse(responseCode = "400",description = "not Found")
    @GetMapping("users/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.get(username), HttpStatus.ACCEPTED);
    }


    @Operation(description = "Get all user")
    @ApiResponse(responseCode = "202",description = "Successfully fetched")
    @GetMapping("users")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.ACCEPTED);
    }



    @Operation(description = "Create user")
    @ApiResponse(responseCode = "201",description = "Successfully created")
    @ApiResponse(responseCode = "400",description = "Not created")
    @PostMapping("users")
    public ResponseEntity<UserDTO> addUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.add(userDTO), HttpStatus.CREATED);
    }


    @Operation(description = "Delete user")
    @ApiResponse(responseCode = "204",description = "Successfully deleted")
    @ApiResponse(responseCode = "400",description = "Not deleted")
    @DeleteMapping("users/{username}")
    public ResponseEntity<Integer> deleteUser(@PathVariable("username") String username) {
        UserDTO userDTO = userService.get(username);
        userService.delete(userDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(description = "Update user")
    @ApiResponse(responseCode = "201",description = "Successfully updated")
    @ApiResponse(responseCode = "400",description = "Not updated")
    @PutMapping("users/{username}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("username") String username, @RequestBody @Valid UserDTO userDTO) {
        UserDTO foundUserDTO = userService.get(username);
        foundUserDTO.setUsername(username);
        userService.add(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);


    }
}
