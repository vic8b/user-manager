package com.vic8b.usermanager.service;

import com.vic8b.usermanager.command.UpdateUserCommand;
import com.vic8b.usermanager.model.User;
import com.vic8b.usermanager.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User addUser(@NonNull User user) {
        return userRepository.add(user);
    }

    public void remove(@NonNull UUID id) {
        userRepository.remove(id);
    }

    public User updateUser(@NonNull UUID uuid, @NonNull UpdateUserCommand command) {
        User user = getUserByIdOrThrow(uuid);
        user.update(command.nickname(), command.firstName(), command.lastName(), command.email());
        return user;
    }

    public User getUserByIdOrThrow(@NonNull UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));
    }

    public User getUserByEmailOrThrow(@NonNull String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No such email found"));
    }

    public List<User> getUsersByDates(@NonNull LocalDate from, @NonNull LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("'from' cannot be after 'to'");
        }
        return userRepository.findByDates(from, to);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
