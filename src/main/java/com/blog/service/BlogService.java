package com.blog.service;

import com.blog.domain.Article;
import com.blog.dto.AddArticleRequest;
import com.blog.dto.UpdateArticleRequest;
import com.blog.repository.BlogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    // 게시글 추가
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
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
        blogRepository.deleteById(id);
    }

    public void deleteAllByIds(List<Long> ids) {
        for (long id : ids) {
            blogRepository.deleteById(id);
        }
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));


        // 제목과 내용을 업데이트
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());

        // 변경 내용을 데이터베이스에 저장
        return blogRepository.save(article);

    }

}
