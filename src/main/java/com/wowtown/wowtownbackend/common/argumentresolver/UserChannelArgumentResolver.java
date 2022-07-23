package com.wowtown.wowtownbackend.common.argumentresolver;

import com.wowtown.wowtownbackend.channel.application.common.ChannelProvider;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.common.annotation.UserChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class UserChannelArgumentResolver implements HandlerMethodArgumentResolver {
  private final ChannelProvider channelProvider;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean hasAvatarAnnotation = parameter.hasParameterAnnotation(UserChannel.class);
    boolean hasType = Channel.class.isAssignableFrom(parameter.getParameterType());

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

    Channel findChannel = null;
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("channelId")) {
          findChannel = channelProvider.getChannel(Long.parseLong(cookie.getValue()));
        }
      }
    }
    return findChannel;
  }
}
