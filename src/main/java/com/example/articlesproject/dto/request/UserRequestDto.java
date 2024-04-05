package com.example.articlesproject.dto.request;

import javax.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}