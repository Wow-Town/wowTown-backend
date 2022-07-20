package com.wowtown.wowtownbackend.common.argumentresolver;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class UserAvatarArgumentResolver implements HandlerMethodArgumentResolver {
  private final AvatarProvider avatarProvider;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean hasAvatarAnnotation = parameter.hasParameterAnnotation(UserAvatar.class);
    boolean hasType = Avatar.class.isAssignableFrom(parameter.getParameterType());

    return hasAvatarAnnotation && hasType;
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory)
      throws Exception {

    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

    Cookie[] cookies = request.getCookies();

    Avatar findAvatar = null;
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("avatarId")) {
          findAvatar = avatarProvider.getAvatar(Long.parseLong(cookie.getValue()));
        }
      }
    }
    return findAvatar;
  }
}
