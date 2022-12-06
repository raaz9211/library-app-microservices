package com.epam.library.client;

import com.epam.library.data.UserDTO;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name="users",fallbackFactory = UserClientFallbackFactory.class)
@LoadBalancerClient(name="users")
public interface UserClient {
    @GetMapping("users")
    public List<UserDTO> getAllUser();

    @GetMapping("users/{username}")
    public UserDTO getUser(@PathVariable("username") String username);


    @PostMapping("users")
    public UserDTO addUser(@RequestBody @Valid UserDTO userDTO);

    @DeleteMapping("users/{username}")
    public void deleteUser(@PathVariable("username") String username);

    @PutMapping("users/{username}")
    public UserDTO updateUser(@PathVariable("username") String username, @RequestBody @Valid UserDTO userDTO);
}