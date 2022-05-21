package com.wowtown.wowtownbackend.common.config;

import com.wowtown.wowtownbackend.common.argumentresolver.LoginUserArgumentResolver;
import com.wowtown.wowtownbackend.common.interceptor.LoginInterceptor;
import com.wowtown.wowtownbackend.common.redis.RedisService;
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
  private final RedisService redisService;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new LoginInterceptor(jwtTokenProvider, redisService))
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/signUp/**", "/login");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
        .maxAge(-1) // add maxAge
        .allowCredentials(true)
        .allowedOriginPatterns("http://localhost:3000");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new LoginUserArgumentResolver(jwtTokenProvider));
  }
}
