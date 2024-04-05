package com.example.articlesproject.service.impl;

import com.example.articlesproject.dto.response.ArticleResponseDto;
import com.example.articlesproject.dto.response.ArticlesResponse;
import com.example.articlesproject.model.Article;
import com.example.articlesproject.repository.ArticleRepository;
import com.example.articlesproject.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ObjectMapper mapper;
    @Value("${api.articles.statistics.message}")
    private String message;
    @Value("${api.articles.statistics.days}")
    private int statisticsDays;

    @Override
    public Article addArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public ArticlesResponse getArticles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = articleRepository.findAll(pageable);

        List<ArticleResponseDto> articleResponseDtoList = articles.getContent()
                .stream()
                .map(article -> mapper.convertValue(article, ArticleResponseDto.class))
                .collect(Collectors.toList());

        return ArticlesResponse.builder()
                .totalItems(articles.getTotalElements())
                .page(page)
                .totalPages(articles.getTotalPages())
                .pageSize(size)
                .articles(articleResponseDtoList)
                .build();
    }

    @Override
    public String getStatistics() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime startDate = currentDate.minusDays(statisticsDays);
        return String.format(message,
                articleRepository.findArticlesCountByActualDate(startDate, currentDate),
                statisticsDays);
    }
}
