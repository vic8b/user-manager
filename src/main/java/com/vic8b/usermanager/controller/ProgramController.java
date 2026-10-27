package com.vic8b.usermanager.controller;

import com.vic8b.usermanager.command.CreateProgramCommand;
import com.vic8b.usermanager.command.UpdateProgramCommand;
import com.vic8b.usermanager.dto.ProgramDto;
import com.vic8b.usermanager.service.ProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProgramDto addProgram(@Valid @RequestBody CreateProgramCommand command) {
        return programService.addProgram(command);
    }

    @GetMapping()
    public List<ProgramDto> getPrograms(@RequestParam(defaultValue = "false") boolean active) {
        return programService.getPrograms(active);
    }

    @GetMapping("/{programId}")
    public ProgramDto getProgramById(@PathVariable Long programId) {
        return programService.getProgramById(programId);
    }

    @PutMapping("/{programId}")
    public ProgramDto updateProgramById(@PathVariable Long programId, @Valid @RequestBody UpdateProgramCommand command) {
        return programService.updateProgram(programId, command);
    }

    @DeleteMapping("/{programId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProgramById(@PathVariable Long programId) {
        programService.removeProgram(programId);
    }
}
