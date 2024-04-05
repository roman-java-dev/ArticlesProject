package com.example.articlesproject.repository;

import com.example.articlesproject.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT COUNT(a) FROM Article a WHERE a.publishingDate >= :startDate AND a.publishingDate <= :endDate")
    int findArticlesCountByActualDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
