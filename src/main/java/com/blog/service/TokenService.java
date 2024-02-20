package com.blog.service;


import com.blog.config.jwt.TokenProvider;
import com.blog.domain.User;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private UserService userService;


    // 전달 받은 리프레시 토큰으로 토큰 유효성 검사 진행
    // 유효한 토큰인 때 리프레시 토큰으로 사용자 ID 찾기
    // 사용자 id로 사용자를 찾은 후 토큰 제공자의 generateToken()메서드로 새로운 액세스 토큰 생성
    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }


}
