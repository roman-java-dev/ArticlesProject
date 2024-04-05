package com.example.articlesproject.controller;

import com.example.articlesproject.dto.request.ArticleRequestDto;
import com.example.articlesproject.dto.response.ArticleResponseDto;
import com.example.articlesproject.dto.response.ArticlesResponse;
import com.example.articlesproject.model.Article;
import com.example.articlesproject.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final ObjectMapper mapper;

    @PostMapping
    public ResponseEntity<ArticleResponseDto> addArticle(@RequestBody @Valid ArticleRequestDto dto) {
        ArticleResponseDto addedArticle = mapper.convertValue(
                articleService.addArticle(mapper.convertValue(dto, Article.class)), ArticleResponseDto.class);
        return new ResponseEntity<>(addedArticle, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ArticlesResponse> getArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ArticlesResponse articles = articleService.getArticles(page, size);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<String> getStatistics() {
        return new ResponseEntity<>(articleService.getStatistics(), HttpStatus.OK);
    }
}
