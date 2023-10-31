package ru.netodology.backendnet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenDto(
        @JsonProperty("auth-token")
        String authToken
) {
}
