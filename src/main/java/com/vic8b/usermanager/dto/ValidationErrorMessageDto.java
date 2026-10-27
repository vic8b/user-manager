package com.vic8b.usermanager.dto;

import java.util.List;

public record ValidationErrorMessageDto(String message, int httpCode, String timestamp, List<String> errors) {
}
