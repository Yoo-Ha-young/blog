package com.blog.controller;

import com.blog.domain.Article;
import com.blog.dto.ArticleViewResponse;
import com.blog.service.BlogService;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleViewResponse> articles = blogService.findAll().stream()
            .map(ArticleViewResponse::new)
            .toList();
        model.addAttribute("articles", articles);
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "articleContent";
    }
    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {

        if (id == null) { // id가 없으면 생성
            model.addAttribute("article", new ArticleViewResponse());
        } else { // id가 있으면 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
    // todo: 175pg 204pg


}