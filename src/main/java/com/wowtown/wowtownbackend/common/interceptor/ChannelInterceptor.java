package com.wowtown.wowtownbackend.common.interceptor;

import com.wowtown.wowtownbackend.channel.application.common.ChannelProvider;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class ChannelInterceptor implements HandlerInterceptor {

  private final ChannelProvider channelProvider;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
      throws Exception {
    if (request.getMethod().equals("OPTIONS")) {
      return true;
    }

    Long channelId = null;

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("channelId")) {
          channelId = Long.parseLong(cookie.getValue());
        }
      }
    }

    Channel channel = channelProvider.getChannel(channelId);
    if (channel != null) {
      return true;
    }
    return false;
  }
}
