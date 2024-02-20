package com.blog.repository;

import com.blog.domain.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {

    Optional<Article> findAllById(long[] id);

    List<Article> findAllByAuthor(String author);
}
