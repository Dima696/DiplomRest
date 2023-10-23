package ru.netodology.backendnet.dto;

import jakarta.validation.constraints.NotBlank;

public record FileRenameRequestDto(
        @NotBlank(message = "Filename cannot be empty or null")
        String filename
) {
}
