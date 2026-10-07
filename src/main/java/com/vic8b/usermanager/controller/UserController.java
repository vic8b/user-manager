package com.vic8b.usermanager.controller;

import com.vic8b.usermanager.command.UpdateUserCommand;
import com.vic8b.usermanager.model.User;
import com.vic8b.usermanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.getUserByIdOrThrow(id);
    }

    @GetMapping(params = "email")
    public User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmailOrThrow(email);
    }

    @GetMapping(params = {"from", "to"})
    public List<User> getUsersByDates(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return userService.getUsersByDates(from, to);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable UUID id, @RequestBody UpdateUserCommand command) {
        return userService.updateUser(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable UUID id) {
        userService.remove(id);
    }
}
