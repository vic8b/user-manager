package com.vic8b.usermanager.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

    @EqualsAndHashCode.Include
    private final UUID id;
    private String nickname;
    private String firstName;
    private String lastName;
    private String email;
    private final LocalDate creationDate;
    private boolean active;

    @Builder
    public User(@NonNull String nickname, @NonNull String firstName, @NonNull String lastName, @NonNull String email) {
        validateNickname(nickname);
        validateEmail(email);

        this.id = UUID.randomUUID();
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creationDate = LocalDate.now();
        this.active = true;
    }

    public void update(@NonNull String nickname, @NonNull String firstName, @NonNull String lastName, @NonNull String email) {
        validateNickname(nickname);
        validateEmail(email);
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    private static void validateEmail(@NonNull String email) {
        if (email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    private static boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("Nickname cannot be blank");
        }
        if (nickname.length() < 3) {
            throw new IllegalArgumentException("Nickname must contain at least 3 characters");
        }
    }
}
