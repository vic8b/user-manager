package com.vic8b.usermanager.repository;

import com.vic8b.usermanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
    List<User> findByLastNameContainingIgnoreCase(String lastName);
    Optional<User> findByEmail(String email);
    List<User> findByLastNameContainingIgnoreCaseAndEmail(String lastName, String email);
}
