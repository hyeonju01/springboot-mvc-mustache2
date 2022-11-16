package com.example.bbs2.service;

import com.example.bbs2.domain.dto.ArticleAddRequest;
import com.example.bbs2.domain.dto.ArticleAddResponse;
import com.example.bbs2.domain.dto.ArticleResponse;
import com.example.bbs2.domain.entity.Article;
import com.example.bbs2.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleResponse getArticle(Long id) {
        Optional<Article> optArticle = articleRepository.findById(id);
        Article article = optArticle.get();
        ArticleResponse articleResponse = Article.of(article);
        return articleResponse;
    }

    public ArticleAddResponse add(ArticleAddRequest dto) {
        Article article = dto.toEntity();
        Article savedArticle = articleRepository.save(article);
        return new ArticleAddResponse(savedArticle.getId(), savedArticle.getTitle(), savedArticle.getContent());

    }
}
