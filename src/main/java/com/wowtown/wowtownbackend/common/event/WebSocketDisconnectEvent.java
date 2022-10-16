package com.wowtown.wowtownbackend.common.event;

import com.wowtown.wowtownbackend.chatroom.application.ChatRoomCommandExecutor;
import com.wowtown.wowtownbackend.privateSpace.application.PrivateSpaceCommandExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Configuration
@RequiredArgsConstructor
public class WebSocketDisconnectEvent {

  private final ChatRoomCommandExecutor chatRoomCommandExecutor;
  private final PrivateSpaceCommandExecutor privateSpaceCommandExecutor;

  @EventListener
  public void onDisconnectEvent(SessionDisconnectEvent event) {
    chatRoomCommandExecutor.leaveChatRoom(event.getSessionId());
    privateSpaceCommandExecutor.leavePrivateSpace(event.getSessionId());
  }
}
