package com.example.articlesproject.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class ArticleRequestDto {
    @NotBlank
    @Size(max = 100)
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String content;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime publishingDate;
}
