package com.epam.jpop.libraryservice.feign;

import com.epam.jpop.libraryservice.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users")
    List<User> getUsers();

    @GetMapping("/api/users/{id}")
    User getUser(@PathVariable Long id);

    @PostMapping("/api/users")
    User addUser(@RequestBody User user);

    @PutMapping("/api/users/{id}")
    User updateUser(@RequestBody User user, @PathVariable Long id);

    @DeleteMapping("/api/users/{id}")
    User deleteUser(@PathVariable Long id);

}
