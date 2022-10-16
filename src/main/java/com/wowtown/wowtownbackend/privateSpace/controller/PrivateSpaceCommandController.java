package com.wowtown.wowtownbackend.privateSpace.controller;

import com.wowtown.wowtownbackend.common.event.MessageType;
import com.wowtown.wowtownbackend.privateSpace.application.PrivateSpaceCommandExecutor;
import com.wowtown.wowtownbackend.privateSpace.application.dto.request.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PrivateSpaceCommandController {

  private final PrivateSpaceCommandExecutor privateSpaceCommandExecutor;

  @MessageMapping("/privateSpace/message")
  public void message(@RequestBody MessageDto message, SimpMessageHeaderAccessor headerAccessor) {
    String sessionId = headerAccessor.getSessionId();
    if (message.getType() == MessageType.ENTER) {
      privateSpaceCommandExecutor.enterPrivateSpace(message, sessionId);
    } else if (message.getType() == MessageType.LEAVE) {
      privateSpaceCommandExecutor.leavePrivateSpace(sessionId);
    }
  }
}
