package com.epam.users.service;

import com.epam.users.data.User;
import com.epam.users.data.UserDTO;
import com.epam.users.exception.UserNotFoundException;
import com.epam.users.exception.UserNotRemovedException;
import com.epam.users.exception.UserNotSavedException;
import com.epam.users.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    public UserDTO add(UserDTO userDTO) {

        User user;
        try {
            user = userRepository.save(modelMapper.map(userDTO, User.class));
            LOGGER.info("Book added");
        } catch (Exception e) {
            LOGGER.error("Book not added ");
            throw new UserNotSavedException("user not added ");
        }

        return modelMapper.map(user, UserDTO.class);
    }

    public void delete(UserDTO bookDTO) {
        User user = modelMapper.map(bookDTO, User.class);

        try {
            userRepository.delete(user);
            LOGGER.info("Question removed ");
        } catch (Exception e) {

            LOGGER.error(e);
            LOGGER.error("Enter valid question code ");
            throw new UserNotRemovedException("user not removed ");


        }
    }



    public List<UserDTO> getAll() {

        List<User> questions = (List<User>) userRepository.findAll();
        return modelMapper.map(questions, new TypeToken<List<UserDTO>>() {
        }.getType());
    }

    public UserDTO get(String username) {

        return modelMapper.map(userRepository.findById(username)
                .orElseThrow(() -> new UserNotFoundException("User Not found with id " + username)), UserDTO.class);
    }

}