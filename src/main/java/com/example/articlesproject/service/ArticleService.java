package com.example.articlesproject.service;

import com.example.articlesproject.dto.response.ArticlesResponse;
import com.example.articlesproject.model.Article;

public interface ArticleService {
    Article addArticle(Article article);

    ArticlesResponse getArticles(int page, int size);

    String getStatistics();
}
