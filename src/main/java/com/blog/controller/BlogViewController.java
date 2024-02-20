package com.blog.controller;

import com.blog.domain.Article;
import com.blog.dto.ArticleListViewResponse;
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
        List<ArticleListViewResponse> articles = blogService.findAll()
            .stream()
            .map(ArticleListViewResponse::new)
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
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }

    @GetMapping("/my-articles")
    public String viewMyArticles(Model model) {
        List<Article> myArticles = blogService.findAllByLoggedInUser();
        model.addAttribute("articles", myArticles);
        return "myArticleList";
    }
    // todo: blog 컨트롤러로 이동하고 service코드 수정
}
