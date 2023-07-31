# blog
[mini project : blog] with spring, react


### 기능
- **회원가입**
  : 사용자는 회원가입을 진행한다. 이메일 주소, 비밀번호, 비밀번호 확인, 닉네임, 핸드폰번호, 주소, 상세주소를 입력해서 회원가입을 진행한다.
  
- **로그인**
  : 회원가입이 성공적으로 이루어지면, 로그인을 진행한다. 로그인 이메일과 패스워드로 로그인을 진행한다.
  
- **프로필 수정**
  : 네비게이션 바에서 마이페이지로 이동할 수 있고, 마이페이지로 이동해서 프로필 사진을 수정할 수 있고 닉네임도 수정할 수 있다. 

- **게시물 작성**
  : 마이페이지에서 게시물을 등록할 수 있다. 게시물 등록 시에는 제목, 내용, 사진, 동영상, 파일을 올릴 수 있다.

- **게시물 리스트 보기**
  : 최신 게시물이 리스트형식으로 출력된다. 한페이지에 5개씩 게시물이 보인다. 페이징 처리가 되며 최대 10페이지까  지 보이고, 이전 섹션과 이후 섹션으로 이동하는 방향 버튼이 있다.
  게시물 리스트에서 작성자의 프로필 사진, 닉네임, 게시물의 제목, 내용, 좋아요 수, 댓글 수, 조회 수가 보여진다.
  
- **게시물 상세 보기**
  : 게시물 리스트에서 게시물을 선택하면 해당 게시물의 제목, 작성자, 작성자 프로필 사진, 작성일, 내용, 사진, 동영상, 파일이 출력된다.
  
- **게시물 수정**
  : 만약 본인 게시물이라면 게시물을 수정할 수 있다. 게시물 수정은 제목, 내용, 사진, 동영상, 파일을 수정할 수 있다.
  
- **게시물 삭제**
  : 본인 게시물이면 삭제 가능하다.
  
- **댓글 작성**
  : 모든 게시물에 댓글을 작성할 수 있다. 댓글은 내용만 작성한다. 댓글은 작성자, 작성자 프로필 사진, 작성일, 내용이 출력된다. 댓글은 한번에 3개만 출력되고 페이징이 최대 10페이지 까지 된다. 이전 섹션, 다음 섹션 버튼이 존재한다.
  
- **좋아요**
  : 댓글 수와 좋아요 수가 게시물 상세에 표시가 된다. 본인 게시물이 아니면 좋아요를 할 수 있다. 게시물에 좋아요를 누른 유저의 프로필 사진과 닉네임이 모두 출력된다.

- **메인화면**
  : 로그인에 성공하면 메인화면으로 이동한다. 메인화면에는 주간 Top3 게시글이 보인다. 주간 Top3의 경우 좋아요 순으로 정렬한다.
  
- **인기 검색어**
  : 인기 검색어가 우측에 존재하도록 한다.
  
- **네비게이션 바**
  : 상단에는 네비게이션 바가 있고, 검색을 할 수 있다.


## 테이블
- **User**(user_email[PK], user_password, user_nickname, 
    user_phone_number, user_address, user_profile)
  
- **Board**(board_number[PK], board_title, board_content, board_image, board_video, board_file, board_writer[FK-User(user_email)], board_writer_profile, board_writer_nickname, board_write_date, board_click_count, board_like_count, board_comment_count)
  
- **PopularSerach**(popular_term[PK], popular_search_count)
  
- **Like**(board_number[FK-Board(board_number)], user_email[FK-User(user_email)], like_user_profile, like_user_nickname)
  
- **Comment**(board_number[FK-Board(board_number)], user_email[FK-User(user_email)], comment_write_date, comment_content, like_user_profile, like_user_nickname)

- User - Board 관계
  1. User가 Board를 작성한다. **1:n**
  2. User가 Board에 좋아요를 누른다. **n:m**
  3. User가 Board에 댓글을 작성한다. **n:m**



### 기술 
- React
- Spring boot
- MySQL
