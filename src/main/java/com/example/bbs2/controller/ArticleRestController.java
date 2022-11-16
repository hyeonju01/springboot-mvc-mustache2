package com.example.bbs2.controller;

/*
tdd 방식으로 구현
 */

import com.example.bbs2.domain.dto.ArticleAddRequest;
import com.example.bbs2.domain.dto.ArticleAddResponse;
import com.example.bbs2.domain.dto.ArticleResponse;
import com.example.bbs2.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleRestController {
    private final ArticleService articleService;

    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> get(@PathVariable Long id) {
        ArticleResponse articleResponse = articleService.getArticle(id);
        return ResponseEntity.ok().body(articleResponse);
    }

    @PostMapping
    public ResponseEntity<ArticleAddResponse> addArticle(@RequestBody ArticleAddRequest dto) {
        ArticleAddResponse response = articleService.add(dto);
        return ResponseEntity.ok().body(response);
    }
}