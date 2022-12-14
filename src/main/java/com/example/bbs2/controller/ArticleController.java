package com.example.bbs2.controller;

import com.example.bbs2.domain.entity.Article;
import com.example.bbs2.domain.dto.ArticleDto;
import com.example.bbs2.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if(!optionalArticle.isEmpty()) {
            model.addAttribute("article", optionalArticle.get());
            return "/articles/edit";
        } else {
            model.addAttribute("message", String.format("%d가 없습니다.", id));
            return "error";
        }
    }

    // delete 기능 구현
    @GetMapping("/{id}/delete")
    public String edit(@PathVariable Long id) {
        articleRepository.deleteById(id); //return type- void
        return "redirect:/articles";
    }

    @PostMapping("") //articles
    public String add(ArticleDto articleDto) {
        log.info(articleDto.getTitle());
        Article savedArticle = articleRepository.save(articleDto.toEntity());
        log.info("generatedId:{}", savedArticle.getId());
        return String.format("redirect:/articles/%d", savedArticle.getId());
    }

    @PostMapping(value = "/posts")
    public String createArticle(ArticleDto form) {
        log.info(form.toString());

        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        Article saved = articleRepository.save(articleEntity);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    // update 컨트롤러
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, ArticleDto form, Model model) {
        log.info("title: {} content: {}", form.getTitle(), form.getContent());
        Article article = articleRepository.save(form.toEntity());
        model.addAttribute("article", article);
        return String.format("redirect:/articles/%d", article.getId());
    }
}