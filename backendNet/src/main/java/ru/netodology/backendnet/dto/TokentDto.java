package ru.netodology.backendnet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokentDto(
        @JsonProperty("auth-token")
        String authToken
) {
}
