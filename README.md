# blog
[mini project : blog] with spring, react


### 기능
- 블로그 글 작성
  `POST /articles`

- 블로그 글 조회(단건 조회/전체목록 조회)
  `GET /articles/1`

- 블로그 글 삭제
  `DELETE /articles/1`

- 블로그 글 수정
  `PUT /articles/1`


## 테이블
- **User**(user_email[PK], user_password, user_nickname, 
    user_phone_number, user_address, user_profile)
- **Board**(board_number[PK], board_title, board_content, board_image, board_video, board_file, board_writer[FK-User(user_email)], board_write_date, board_click_count)
- **PopularSerach**(popular_term, popular_search_count)
- **Like**(board_number[FK-Board(board_number)], user_email[FK-User(user_email)])
- **Comment**(board_number[FK-Board(board_number)], user_email[FK-User(user_email)], comment_write_date, comment_content)

- User - Board 관계
  1. User가 Board를 작성한다. **1:n**
  2. User가 Board에 좋아요를 누른다. **n:m**
  3. User가 Board에 댓글을 작성한다. **n:m**

### 기술 
- Spring boot
- MySQL
- 스프링 데이터 JPA
- 롬복
