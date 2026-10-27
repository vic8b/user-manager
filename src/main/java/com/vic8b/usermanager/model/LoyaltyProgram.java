package com.vic8b.usermanager.model;

import com.vic8b.usermanager.command.UpdateProgramCommand;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "loyalty_programs")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoyaltyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public LoyaltyProgram(String name, String description, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateProgram(@NonNull UpdateProgramCommand command) {
        this.name = command.name();
        this.description = command.description();
        this.startDate = command.startDate();
        this.endDate = command.endDate();
    }

    public boolean isActiveOn(LocalDate date) {
        return !date.isBefore(startDate) && (endDate == null || !date.isAfter(endDate));
    }
}
