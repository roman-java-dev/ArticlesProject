package com.example.articlesproject.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime publishingDate;
}
