package com.epam.library.client;

import com.epam.library.data.UserDTO;
import feign.FeignException;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
@Setter
public class UserClientImpl implements UserClient {
    private static final String DUMMY_EMAIL = "dummy@epam.com";
    private static final String DUMMY_NAME = "dummy";
    private  Throwable cause;

    public UserClientImpl() {
    }
    public UserClientImpl(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public List<UserDTO> getAllUser() {
        UserDTO userDTO= new UserDTO();
        userDTO.setEmail(DUMMY_EMAIL);
        userDTO.setUsername(DUMMY_NAME);
        userDTO.setName(DUMMY_NAME);

        return Arrays.asList(userDTO,userDTO);
    }

    @Override
    public UserDTO getUser(String username) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 400) {
            throw (FeignException) cause;
        }else {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(DUMMY_EMAIL);
            userDTO.setUsername(DUMMY_NAME);
            userDTO.setName(DUMMY_NAME);
            return userDTO;
        }
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        UserDTO userDTODummy= new UserDTO();
        userDTODummy.setEmail(DUMMY_EMAIL);
        userDTODummy.setUsername(DUMMY_NAME);
        userDTODummy.setName(DUMMY_NAME);
        return userDTODummy;
    }

    @Override
    public void deleteUser(String username) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 400) {
            throw (FeignException) cause;
        }
    }

    @Override
    public UserDTO updateUser(String username, UserDTO userDTO) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 400) {
            throw (FeignException) cause;
        }else {
            UserDTO userDTODummy = new UserDTO();
            userDTODummy.setEmail(DUMMY_EMAIL);
            userDTODummy.setUsername(DUMMY_NAME);
            userDTODummy.setName(DUMMY_NAME);
            return userDTODummy;
        }
    }
}
