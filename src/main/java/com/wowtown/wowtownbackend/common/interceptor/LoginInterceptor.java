package com.wowtown.wowtownbackend.common.interceptor;

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

    // refreshToken이 만료되지 않았을경우
    if (!jwtTokenProvider.validateToken(accessToken)) {
      String updateToken = jwtTokenProvider.updateAccessToken(refreshToken);
      response.setHeader("Authorization", updateToken);
    }
    return true;
  }
}
