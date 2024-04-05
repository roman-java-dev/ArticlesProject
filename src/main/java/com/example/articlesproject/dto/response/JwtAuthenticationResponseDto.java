package com.example.articlesproject.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthenticationResponseDto {
    private String token;
}