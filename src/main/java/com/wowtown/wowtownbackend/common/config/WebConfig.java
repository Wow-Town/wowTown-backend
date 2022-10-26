package com.wowtown.wowtownbackend.common.config;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.channel.application.common.ChannelProvider;
import com.wowtown.wowtownbackend.common.argumentresolver.LoginUserArgumentResolver;
import com.wowtown.wowtownbackend.common.argumentresolver.UserAvatarArgumentResolver;
import com.wowtown.wowtownbackend.common.argumentresolver.UserChannelArgumentResolver;
import com.wowtown.wowtownbackend.common.interceptor.AvatarInterceptor;
import com.wowtown.wowtownbackend.common.interceptor.ChannelInterceptor;
import com.wowtown.wowtownbackend.common.interceptor.LoginInterceptor;
import com.wowtown.wowtownbackend.user.application.common.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final JwtTokenProvider jwtTokenProvider;
  private final AvatarProvider avatarProvider;
  private final ChannelProvider channelProvider;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new LoginInterceptor(jwtTokenProvider))
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns(
            "/signUp/**",
            "/login",
            "/h2-console/**",
            "/favicon.ico",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/webjars/**",
            "/ws-stomp"); // 임시로 열어놓음
    registry
        .addInterceptor(new ChannelInterceptor(channelProvider))
        .order(2)
        .addPathPatterns("/avatars/**");
    registry
        .addInterceptor(new AvatarInterceptor(jwtTokenProvider, avatarProvider))
        .order(3)
        .addPathPatterns("/studyGroups/**");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
        .maxAge(-1) // add maxAge
        .allowCredentials(true)
        .exposedHeaders("Authorization")
        .allowedOriginPatterns(
            "http://localhost:3000", "https://localhost:3000", "https://wowtown.co.kr");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new LoginUserArgumentResolver(jwtTokenProvider));
    resolvers.add(new UserAvatarArgumentResolver(avatarProvider));
    resolvers.add(new UserChannelArgumentResolver(channelProvider));
  }
}
