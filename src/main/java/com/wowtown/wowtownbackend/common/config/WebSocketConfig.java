package com.wowtown.wowtownbackend.common.config;

import com.wowtown.wowtownbackend.common.infra.AgentWebSocketHandlerDecoratorFactory;
import com.wowtown.wowtownbackend.common.interceptor.HttpHandshakeInterceptor;
import com.wowtown.wowtownbackend.common.interceptor.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final StompHandler stompHandler;
  private final AgentWebSocketHandlerDecoratorFactory agentWebSocketHandlerDecoratorFactory;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/sub");
    config.setApplicationDestinationPrefixes("/pub");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
        .addEndpoint("/ws-stomp")
        .setAllowedOriginPatterns("*")
        .addInterceptors(new HttpHandshakeInterceptor());
    ;
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(stompHandler);
  }

  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
    registry.setMessageSizeLimit(10000 * 1024);
    registry.setSendBufferSizeLimit(10000 * 1024);
    registry.setSendTimeLimit(10000 * 1024);
  }

  @Bean
  public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
    ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
    container.setMaxTextMessageBufferSize(10000 * 1024); // 10Mb
    container.setMaxSessionIdleTimeout(2048L * 2048L * 2048L);
    container.setAsyncSendTimeout(2048L * 2048L * 2048L);
    container.setMaxBinaryMessageBufferSize(10000 * 1024);
    return container;
  }
}
