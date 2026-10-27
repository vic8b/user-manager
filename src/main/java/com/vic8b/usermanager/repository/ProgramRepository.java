package com.vic8b.usermanager.repository;

import com.vic8b.usermanager.model.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<LoyaltyProgram, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
