package com.wowtown.wowtownbackend.common.interceptor;

import com.wowtown.wowtownbackend.common.redis.RedisService;
import com.wowtown.wowtownbackend.user.application.common.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

  private final JwtTokenProvider jwtTokenProvider;
  private final RedisService redisService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
      throws Exception {
    if (request.getMethod().equals("OPTIONS")) {
      return true;
    }
    String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    String refreshToken = null;

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("api-key")) {
          refreshToken = cookie.getValue();
        }
      }
    }

    if (!jwtTokenProvider.validateToken(accessToken)) {
      String email = redisService.getValues(refreshToken);
      if (email != null) {
        // Todo refreshToken이 유효하면 exception 발생시키고 exception 처리하는 핸들러에서 새로운 토큰을 발행해준다.
        return true;
      }
      // Todo refreshToken이 유효하지 않으면 exception 발생시키고 exception 처리하는 핸들러에서 다시 로그인 하라는 에러 메시지를 발행해준다.
      return false;
    }
    return true;
  }
}
