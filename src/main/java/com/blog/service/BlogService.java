package com.blog.service;

import com.blog.domain.Article;
import com.blog.dto.AddArticleRequest;
import com.blog.dto.UpdateArticleRequest;
import com.blog.repository.BlogRepository;
import com.blog.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    // 게시글 추가
    public Article save(AddArticleRequest request, String nickname) {
        return blogRepository.save(request.toEntity(nickname));
    }

    // 글 전체 조회
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // 글 조회
    public Article findById(long id) {
        return blogRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("not found: " + id));
    }


    public void delete(long id) {

        Article article = blogRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("not found :" + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }


    // 글 삭제 (여러 개)
    public void deleteAllByIds(List<Long> ids) {
        for (long id : ids) {
            Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found :" + id));

            authorizeArticleAuthor(article);
            blogRepository.delete(article);
        }
    }


    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeArticleAuthor(article);

        // 제목과 내용을 업데이트
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());


        // 변경 내용을 데이터베이스에 저장
        return blogRepository.save(article);

    }

    private void authorizeArticleAuthor(Article article) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String author = userRepository.findByEmail(username).get().getNickname();

        if(!article.getAuthor().equals(author)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

    // 현재 로그인한 사용자의 글만 조회
    public List<Article> findAllByLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String author = userRepository.findByEmail(username).get().getNickname();
        return blogRepository.findAllByAuthor(author);
    }

}
