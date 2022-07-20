package com.wowtown.wowtownbackend.common.argumentresolver;

import com.wowtown.wowtownbackend.common.annotation.LoginUser;
import com.wowtown.wowtownbackend.user.application.common.JwtTokenProvider;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean hasUserAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
    boolean hasType = User.class.isAssignableFrom(parameter.getParameterType());

    return hasUserAnnotation && hasType;
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory)
      throws Exception {

    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

    String token = request.getHeader(HttpHeaders.AUTHORIZATION);

    return jwtTokenProvider.getAuthenticatedUser(token);
  }
}
