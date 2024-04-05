package com.example.articlesproject.service.impl;

import com.example.articlesproject.dto.response.ArticleResponseDto;
import com.example.articlesproject.dto.response.ArticlesResponse;
import com.example.articlesproject.model.Article;
import com.example.articlesproject.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ArticleServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    private ArticleServiceImpl articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addArticle() {
        Article article = new Article();
        when(articleRepository.save(any())).thenReturn(article);

        Article addedArticle = articleService.addArticle(article);

        assertEquals(article, addedArticle);
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void getArticles() {
        int page = 0;
        int size = 10;

        List<Article> articlesList = new ArrayList<>();
        articlesList.add(new Article());
        Page<Article> articlesPage = new PageImpl<>(articlesList);

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(articlesPage);
        when(mapper.convertValue(any(), eq(ArticleResponseDto.class))).thenReturn(new ArticleResponseDto());

        ArticlesResponse articlesResponse = articleService.getArticles(page, size);

        assertEquals(1, articlesResponse.getArticles().size());
        assertEquals(1, articlesResponse.getTotalItems());
        assertEquals(1, articlesResponse.getTotalPages());
        assertEquals(page, articlesResponse.getPage());
        assertEquals(size, articlesResponse.getPageSize());
        verify(articleRepository, times(1)).findAll(any(Pageable.class));
        verify(mapper, times(1)).convertValue(any(), eq(ArticleResponseDto.class));
    }
}
