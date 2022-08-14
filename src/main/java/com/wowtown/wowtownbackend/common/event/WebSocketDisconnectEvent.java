package com.wowtown.wowtownbackend.common.event;

import com.wowtown.wowtownbackend.chatroom.application.ChatRoomCommandExecutor;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Configuration
@RequiredArgsConstructor
public class WebSocketDisconnectEvent {

  private final ChatRoomCommandExecutor chatRoomCommandExecutor;

  @EventListener
  public void onDisconnectEvent(SessionDisconnectEvent event) {

    StompCommand messageType = (StompCommand) event.getMessage().getHeaders().get("stompCommand");
    String sessionId = event.getSessionId();
    ChatMessageDto chatMessageDto =
        new ChatMessageDto(messageType, sessionId, null, null, null, null);
    chatRoomCommandExecutor.disConnectChatRoom(chatMessageDto);
  }
}
