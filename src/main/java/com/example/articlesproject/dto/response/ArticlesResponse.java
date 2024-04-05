package com.example.articlesproject.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ArticlesResponse {
    private long totalItems;
    private int page;
    private int totalPages;
    private int pageSize;
    private List<ArticleResponseDto> articles;
}