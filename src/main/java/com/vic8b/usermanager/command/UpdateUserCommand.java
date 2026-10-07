package com.vic8b.usermanager.command;

import lombok.NonNull;

public record UpdateUserCommand(@NonNull String nickname, @NonNull String firstName, @NonNull String lastName,
                                @NonNull String email) {
}
