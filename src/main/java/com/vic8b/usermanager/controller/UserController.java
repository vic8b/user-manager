package com.vic8b.usermanager.controller;

import com.vic8b.usermanager.command.UserRegistrationCommand;
import com.vic8b.usermanager.command.UserUpdateCommand;
import com.vic8b.usermanager.dto.UserDto;
import com.vic8b.usermanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) String lastName, @RequestParam(required = false) String email) {
        return userService.getUsers(lastName, email);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Valid UserRegistrationCommand command) {
        return userService.addUser(command);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateCommand command) {
        return userService.updateUser(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable Long id) {
        userService.removeUser(id);
    }
}
