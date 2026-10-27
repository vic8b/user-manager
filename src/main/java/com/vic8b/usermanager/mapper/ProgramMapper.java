package com.vic8b.usermanager.mapper;

import com.vic8b.usermanager.command.CreateProgramCommand;
import com.vic8b.usermanager.dto.ProgramDto;
import com.vic8b.usermanager.model.LoyaltyProgram;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgramMapper {
    ProgramDto toDto(LoyaltyProgram program);
    LoyaltyProgram toEntity(CreateProgramCommand command);
}
