package com.wowtown.wowtownbackend.common.interceptor;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.user.application.common.JwtTokenProvider;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AvatarInterceptor implements HandlerInterceptor {
  private final JwtTokenProvider jwtTokenProvider;
  private final AvatarProvider avatarProvider;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
      throws Exception {
    if (request.getMethod().equals("OPTIONS")) {
      return true;
    }
    String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    Long avatarId = null;

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("avatarId")) {
          avatarId = Long.parseLong(cookie.getValue());
        }
      }
    }

    User user = jwtTokenProvider.getAuthenticatedUser(accessToken);

    Avatar avatar = avatarProvider.getAvatar(avatarId);
    if (avatar.getUser().equals(user)) {
      return true;
    }
    return false;
  }
}
