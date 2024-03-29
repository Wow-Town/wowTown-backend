package com.wowtown.wowtownbackend.privateSpace.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.common.ChatRoomMapper;
import com.wowtown.wowtownbackend.common.event.MessageType;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.notice.domain.Notice;
import com.wowtown.wowtownbackend.privateSpace.application.dto.request.MessageDto;
import com.wowtown.wowtownbackend.privateSpace.domain.AvatarPrivateSpace;
import com.wowtown.wowtownbackend.privateSpace.domain.AvatarPrivateSpaceRepository;
import com.wowtown.wowtownbackend.privateSpace.domain.PrivateSpace;
import com.wowtown.wowtownbackend.privateSpace.domain.PrivateSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrivateSpaceCommandExecutor {
  private final AvatarProvider avatarProvider;
  private final PrivateSpaceRepository privateSpaceRepository;
  private final AvatarPrivateSpaceRepository avatarPrivateSpaceRepository;
  private final ChatRoomMapper chatRoomMapper;
  private final SimpMessageSendingOperations sendingOperations;

  @Transactional
  public PrivateSpace createPrivateSpace(String subject, Avatar avatar, Notice notice) {

    PrivateSpace privateSpace = new PrivateSpace(notice);

    privateSpace.addAvatarPrivateSpace(subject, avatar);

    return privateSpaceRepository.save(privateSpace);
  }

  @Transactional
  public void joinNoticePrivateSpace(UUID privateSpaceUUID, String roomName, Avatar avatar) {
    PrivateSpace findPrivateSpace =
        privateSpaceRepository
            .findPrivateSpaceByUuid(privateSpaceUUID)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 프라이빗 스페이스 입니다."));

    findPrivateSpace.addAvatarPrivateSpace(roomName, avatar);
  }

  @Transactional
  public void enterPrivateSpace(MessageDto message, String sessionId) {
    PrivateSpace findPrivateSpace =
        privateSpaceRepository
            .findPrivateSpaceByUuid(message.getPrivateSpaceUUID())
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 프라이빗 스페이스 입니다."));

    findPrivateSpace.enterPrivateSpace(sessionId, message.getSenderId(), message.getPeerUUID());
    sendingOperations.convertAndSend(
        "/sub/privateSpace/" + message.getPrivateSpaceUUID().toString(), message);
  }

  @Transactional
  public void leavePrivateSpace(String sessionId) {
    Optional<AvatarPrivateSpace> findAvatarPrivateSpace =
        avatarPrivateSpaceRepository.findAvatarPrivateSpaceWithSessionId(sessionId);

    findAvatarPrivateSpace.ifPresent(
        avatarPrivateSpace -> {
          MessageDto message =
              new MessageDto(
                  MessageType.LEAVE,
                  avatarPrivateSpace.getPrivateSpace().getUuid(),
                  avatarPrivateSpace.getAvatar().getId(),
                  avatarPrivateSpace.getPeerUUID());
          PrivateSpace findPrivateSpace = avatarPrivateSpace.getPrivateSpace();
          findPrivateSpace.leavePrivateSpace(sessionId);

          sendingOperations.convertAndSend(
              "/sub/privateSpace/" + message.getPrivateSpaceUUID().toString(), message);
        });
  }
}
