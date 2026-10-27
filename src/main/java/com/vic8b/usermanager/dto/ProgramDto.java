package com.vic8b.usermanager.dto;

import java.time.LocalDate;

public record ProgramDto(Long id, String name, String description, LocalDate startDate, LocalDate endDate) {
}
