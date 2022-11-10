package com.example.bbs2.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity //객체를 인식해주는 어노테이션
@Table(name = "article2") //article2 테이블을 생성해준다.
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}