package com.example.bbs2.controller;

import com.example.bbs2.domain.entity.Article;
import com.example.bbs2.domain.dto.ArticleDto;
import com.example.bbs2.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {


    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/new")
    public String createPage() {
        return "articles/new";
    }

    //전체 조회
    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "articles/list";
    }

    //전체 조회(타입 캐스팅)
    @GetMapping("")
    public String index() {
        return "redirect:/articles/list";
    }

    @GetMapping("/{id}")
    public String selectSingle(@PathVariable Long id, Model model) {
        Optional<Article> optArticle = articleRepository.findById(id); //id를 받아온다.
        if(!optArticle.isEmpty()) {
            model.addAttribute("article", optArticle.get());
            return "articles/show"; //show.mustache로 연결
        } else {
            return "articles/error";
        }
    }

//    @PostMapping("") //articles
//    public String add(ArticleDto articleDto) {
//        log.info(articleDto.getTitle());
//        Article savedArticle = articleRepository.save(articleDto.toEntity());
//        log.info("generatedId:{}", savedArticle.getId());
//        return String.format("redirect:/articles/%d", savedArticle.getId());
//    }

    @PostMapping(value = "/posts")
    public String createArticle(ArticleDto form) {
        log.info(form.toString());

        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        Article saved = articleRepository.save(articleEntity);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }
}