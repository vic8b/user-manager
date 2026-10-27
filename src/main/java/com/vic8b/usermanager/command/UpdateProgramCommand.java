package com.vic8b.usermanager.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateProgramCommand(@NotBlank String name, String description, @NotNull LocalDate startDate, LocalDate endDate) {
}
