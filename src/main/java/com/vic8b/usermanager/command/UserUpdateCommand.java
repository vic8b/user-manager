package com.vic8b.usermanager.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateCommand(@NotBlank String firstName, @NotBlank String lastName, @NotBlank @Email String email) {
}
