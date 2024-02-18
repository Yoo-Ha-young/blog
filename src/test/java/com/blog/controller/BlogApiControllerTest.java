package com.blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.assertj.core.util.Arrays;
import com.blog.domain.Article;
import com.blog.dto.AddArticleRequest;
import com.blog.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

//    @BeforeEach // 테스트 실행 전 실행하는 메서드
//    public void mockMvcSetup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//        blogRepository.deleteAll();
//    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        // 객체 json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1); // 크기가 1인지 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);

    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
            .build());
        //when
        final ResultActions resultActions = mockMvc.perform(get(url));


        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].content").value(content))
            .andExpect(jsonPath("$[0].title").value(title));

    }

    @DisplayName("findAllArticle: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

       Article savedArticle = blogRepository.save(Article.builder()
            .title(title)
            .content(content)
            .build());
        //when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value(content))
            .andExpect(jsonPath("$.title").value(title));

    }


    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
            .title(title)
            .content(content)
            .build());
        //when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());


        //then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.isEmpty());
    }


    @DisplayName("deleteArticlesByIds: 선택된 게시글 삭제에 성공한다.")
    @Test
    public void deleteArticlesByIds() throws Exception {
        // given
        // 아이디가 1인 게시글이 선택되었다고 가정
        long[] selectedIds = {18,20};

        // when
        mockMvc.perform(delete("/api/articles/deleteByIds")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(selectedIds)))
            .andExpect(status().isOk());

        // then
        // 삭제 후 해당 아이디의 게시글이 더 이상 존재하지 않음을 확인
        for (Long id : selectedIds) {
            Optional<Article> article = blogRepository.findById(id);
            assertThat(article).isEmpty();
        }
    }
}