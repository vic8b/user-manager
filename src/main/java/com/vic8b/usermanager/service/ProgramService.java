package com.vic8b.usermanager.service;

import com.vic8b.usermanager.command.CreateProgramCommand;
import com.vic8b.usermanager.command.UpdateProgramCommand;
import com.vic8b.usermanager.dto.ProgramDto;
import com.vic8b.usermanager.exception.BusinessException;
import com.vic8b.usermanager.exception.ConflictException;
import com.vic8b.usermanager.exception.NotFoundException;
import com.vic8b.usermanager.mapper.ProgramMapper;
import com.vic8b.usermanager.model.LoyaltyProgram;
import com.vic8b.usermanager.repository.ProgramRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramMapper mapper;

    public ProgramDto addProgram(@NonNull CreateProgramCommand command) {
        validateDates(command.startDate(), command.endDate());
        validateNameAvailability(command.name());
        LoyaltyProgram program = mapper.toEntity(command);
        programRepository.save(program);
        return mapper.toDto(program);
    }

    public void removeProgram(@NotNull Long programId) {
        LoyaltyProgram program = findProgramByIdOrThrow(programId);
        programRepository.delete(program);
    }

    public ProgramDto updateProgram(@NotNull Long programId, @NonNull UpdateProgramCommand command) {
        LoyaltyProgram program = findProgramByIdOrThrow(programId);
        validateDates(command.startDate(), command.endDate());
        validateUpdateNameAvailability(command.name(), programId);
        program.updateProgram(command);
        programRepository.save(program);
        return mapper.toDto(program);
    }

    public ProgramDto getProgramById(@NotNull Long programId) {
        return mapper.toDto(findProgramByIdOrThrow(programId));
    }

    public List<ProgramDto> getPrograms(boolean active) {
        return programRepository.findAll().stream()
                .filter(program -> !active || program.isActiveOn(LocalDate.now()))
                .map(mapper::toDto)
                .toList();
    }

    private void validateDates(@NotNull LocalDate startDate, LocalDate endDate) {
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new BusinessException("End date cannot be before start date");
        }
    }

    private void validateNameAvailability(@NotBlank String name) {
        if (programRepository.existsByName(name)) {
            throw new ConflictException("Name " + name + " already exists");
        }
    }

    private void validateUpdateNameAvailability(@NotBlank String name, @NotNull Long id) {
        if (programRepository.existsByNameAndIdNot(name, id)) {
            throw new ConflictException("Name " + name + " already exists");
        }
    }

    private LoyaltyProgram findProgramByIdOrThrow(@NonNull Long programId) {
        return programRepository.findById(programId)
                .orElseThrow(() -> new NotFoundException("Program with id " + programId + " not found"));
    }
}
