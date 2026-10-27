package com.vic8b.usermanager.dto;

public record ErrorMessageDto(String message, int httpCode, String timestamp) {
}
