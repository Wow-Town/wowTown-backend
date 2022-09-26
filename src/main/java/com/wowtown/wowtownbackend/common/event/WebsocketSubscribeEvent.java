package com.wowtown.wowtownbackend.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Configuration
@RequiredArgsConstructor
public class WebsocketSubscribeEvent {
  @EventListener
  public void onSubscribeEvent(SessionSubscribeEvent event) {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
    System.out.println(headerAccessor.getSessionId());
  }
}
