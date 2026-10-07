package com.vic8b.usermanager.repository;

import com.vic8b.usermanager.model.User;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    public User add(@NonNull User user) {
        if (users.containsKey(user.getId())) {
            throw new IllegalArgumentException("User " + user.getId() + " already exists");
        }
        if (findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email: " + user.getEmail() + " already exists");
        }
        users.put(user.getId(), user);
        return user;
    }

    public void remove(@NonNull UUID id) {
        if (!users.containsKey(id)) {
            throw new IllegalArgumentException("User does not exist");
        }
        users.remove(id);
    }

    public Optional<User> findByEmail(@NonNull String email) {
        return users.values().stream()
                .filter(user -> email.equalsIgnoreCase(user.getEmail()))
                .findFirst();
    }

    public Optional<User> findById(@NonNull UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    public List<User> findByDates(@NonNull LocalDate from, @NonNull LocalDate to) {
        return users.values().stream()
                .filter(user -> !user.getCreationDate().isBefore(from) && !user.getCreationDate().isAfter(to))
                .toList();
    }

    public List<User> findAll() {
        return List.copyOf(users.values());
    }
}
