package com.example.bbs2.controller;

import com.example.bbs2.domain.dto.ArticleAddRequest;
import com.example.bbs2.domain.dto.ArticleAddResponse;
import com.example.bbs2.domain.dto.ArticleResponse;
import com.example.bbs2.domain.entity.Article;
import com.example.bbs2.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@WebMvcTest(ArticleRestController.class)
class ArticleRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ArticleService articleService;

    @Test
    @DisplayName("게시글 1개의 내용을 조회하는 기능이 잘 작동하는가? ")
    public void jsonResponse() throws Exception {
        ArticleResponse articleResponse = ArticleResponse.builder()
                .id(1l)
                .title("title")
                .content("content").build();

        given(articleService.getArticle(1l)).willReturn(articleResponse);

        Long articleId = 1l;
        String url = String.format("/api/v1/articles/%d", articleId);

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title").value("title"))
                .andDo(print());

        verify(articleService).getArticle(articleId);

    }

    @Test
    void add() throws Exception {

        ArticleAddRequest dto = new ArticleAddRequest("제목입니다.", "내용이라옹");

        given(articleService.add(any())).willReturn(new ArticleAddResponse(1l, dto.getTitle(), dto.getContent()));

        mockMvc.perform(post("/api/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new ArticleAddRequest("제목입니다.", "내용이라옹")))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andDo(print());

        verify(articleService).add(dto);
    }
}