package com.vic8b.usermanager.service;

import com.vic8b.usermanager.command.UserRegistrationCommand;
import com.vic8b.usermanager.command.UserUpdateCommand;
import com.vic8b.usermanager.dto.UserDto;
import com.vic8b.usermanager.exception.ConflictException;
import com.vic8b.usermanager.exception.NotFoundException;
import com.vic8b.usermanager.mapper.UserMapper;
import com.vic8b.usermanager.model.User;
import com.vic8b.usermanager.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserDto addUser(@NonNull UserRegistrationCommand command) {
        LocalDateTime registrationDate = LocalDateTime.now();
        validateEmailAvailability(command.email());
        User user = mapper.toEntity(command);
        user.setRegisteredAt(registrationDate);
        userRepository.save(user);
        return mapper.toDto(user);
    }

    public void removeUser(@NonNull Long userId) {
        User user = findUserByIdOrThrow(userId);
        userRepository.delete(user);
    }

    public UserDto updateUser(@NonNull Long userId, @NonNull UserUpdateCommand command) {
        User user = findUserByIdOrThrow(userId);
        validateUpdateEmailAvailability(command.email(), userId);
        user.updateUser(command);
        userRepository.save(user);
        return mapper.toDto(user);
    }

    public List<UserDto> getUsers(String lastName, String email) {
        if (lastName != null && email != null) {
            return userRepository.findByLastNameContainingIgnoreCaseAndEmail(lastName, email).stream()
                    .map(mapper::toDto)
                    .toList();
        }

        if (lastName != null) {
            return userRepository.findByLastNameContainingIgnoreCase(lastName).stream()
                    .map(mapper::toDto)
                    .toList();
        }

        if (email != null) {
            return userRepository.findByEmail(email).stream()
                    .map(mapper::toDto)
                    .toList();
        }

        return userRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        return mapper.toDto(findUserByIdOrThrow(id));
    }

    private void validateEmailAvailability(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Email " + email + " already exists");
        }
    }

    private void validateUpdateEmailAvailability(String email, Long userId) {
        if (userRepository.existsByEmailAndIdNot(email, userId)) {
            throw new ConflictException("Email " + email + " already exists");
        }
    }

    private User findUserByIdOrThrow(@NonNull Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }
}
