package com.wowtown.wowtownbackend.user.infra;

import com.wowtown.wowtownbackend.common.redis.RedisService;
import com.wowtown.wowtownbackend.error.exception.AuthenticationException;
import com.wowtown.wowtownbackend.user.application.common.JwtTokenProvider;
import com.wowtown.wowtownbackend.user.domain.User;
import com.wowtown.wowtownbackend.user.domain.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProviderImpl
    implements JwtTokenProvider { // Jwt Token을 생성, 인증, 권한 부여, 유효성 검사, PK 추출 등의 다양한 기능을 제공하는 클래스

  // 엑세스 토큰 유효시간 | 1m
  private long accessTokenValidTime = 1000L * 60 * 60; // 30 * 60 * 1000L;
  // 리프레시 토큰 유효시간 | 30d
  private long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 30;

  @Value("spring.jwt.secret")
  private String secretKey;

  private final UserRepository userRepository;

  private final RedisService redisService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  // Access Token 생성.
  @Override
  public String createAccessToken(String userEmail) {
    return this.createToken(userEmail, accessTokenValidTime);
  }
  // Refresh Token 생성.
  @Override
  public String createRefreshToken(String userEmail) {
    String refreshToken = this.createToken(userEmail, refreshTokenValidTime);

    redisService.setValues(refreshToken, userEmail, Duration.ofMillis(refreshTokenValidTime));
    return refreshToken;
  }
  // Jwt 토큰 생성
  private String createToken(String userEmail, long tokenValidTime) {
    Claims claims = Jwts.claims().setSubject(userEmail);
    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims) // 데이터
        .setIssuedAt(now) // 토큰 발행 일자
        .setExpiration(new Date(now.getTime() + tokenValidTime)) // 만료 기간
        .signWith(SignatureAlgorithm.HS512, secretKey) // 암호화 알고리즘, secret 값
        .compact(); // Token 생성
  }

  // 인증 성공시 SecurityContextHolder에 저장할 Authentication 객체 생성
  @Override
  public User getAuthenticatedUser(String token) {
    User user = userRepository.findUserByEmail(this.getUserEmail(token)).get();
    return user;
  }

  // Jwt Token에서 User PK 추출
  @Override
  public String getUserEmail(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  // Jwt Token의 유효성 및 만료 기간 검사
  @Override
  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return claims.getBody().getExpiration().after(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  public String updateAccessToken(String refreshToken) {
    String email = redisService.getValues(refreshToken);
    if (email == null) {
      throw new AuthenticationException("토큰이 만료되었습니다. 다시 로그인 해주세요.");
    }

    String newAccessToken = this.createAccessToken(email);

    Jws<Claims> accessClaims =
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(newAccessToken);
    Jws<Claims> refreshClaims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
    if (accessClaims.getBody().getExpiration().after(refreshClaims.getBody().getExpiration())) {
      throw new AuthenticationException("토큰이 만료되었습니다. 다시 로그인 해주세요.");
    }

    return newAccessToken;
  }
}
