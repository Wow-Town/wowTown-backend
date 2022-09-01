package com.wowtown.wowtownbackend.chatroom.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.common.ChatRoomMapper;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.ChatMessageDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.InviteAvatar;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetCreatedChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.domain.*;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomCommandExecutor {
  private final AvatarProvider avatarProvider;
  private final ChatRoomRepository chatRoomRepository;
  private final AvatarChatRoomRepository avatarChatRoomRepository;
  private final ChatRoomMapper chatRoomMapper;
  private final SimpMessageSendingOperations sendingOperations;

  @Transactional
  public GetCreatedChatRoomDto createAvatarChatroom(InviteAvatar dto, Avatar avatar) {
    // 초대한 아바타를 참여한 아바타 리스트를 만든다.
    Avatar invitedAvatar = avatarProvider.getAvatar(dto.getAvatarId());

    ChatRoomType chatRoomType = ChatRoomType.SINGLE;

    // 채팅방을 만든다.
    ChatRoom chatRoom = new ChatRoom(chatRoomType);

    chatRoom.addAvatarChatRoom(invitedAvatar.getNickName(), avatar);
    chatRoom.addAvatarChatRoom(avatar.getNickName(), invitedAvatar);

    chatRoomRepository.save(chatRoom);

    return chatRoomMapper.toGetCreatedChatRoomDto(invitedAvatar.getNickName(), chatRoom.getUuid());
  }

  @Transactional
  public UUID createNoticeChatroom() {

    ChatRoomType chatRoomType = ChatRoomType.MULTI;

    ChatRoom chatRoom = new ChatRoom(chatRoomType);

    return chatRoomRepository.save(chatRoom).getUuid();
  }

  @Transactional
  public void joinNoticeChatroom(UUID chatRoomUUID, String roomName, Avatar avatar) {
    ChatRoom findChatRoom =
        chatRoomRepository
            .findChatRoomByUuid(chatRoomUUID)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));

    findChatRoom.addAvatarChatRoom(roomName, avatar);
  }

  @Transactional
  public void enterChatRoom(ChatMessageDto message) {
    ChatRoom findChatRoom =
        chatRoomRepository
            .findChatRoomByUuid(message.getChatRoomUUID())
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));

    findChatRoom.enterChatRoom(message.getSessionId(), message.getSenderId());
    sendingOperations.convertAndSend(
        "/sub/chatRooms/" + message.getChatRoomUUID().toString(), message);
  }

  @Transactional
  public void leaveChatRoom(ChatMessageDto message) {
    Optional<AvatarChatRoom> findAvatarChatroom =
        avatarChatRoomRepository.findAvatarChatRoomWithSessionId(message.getSessionId());

    findAvatarChatroom.ifPresent(
        avatarChatRoom -> {
          ChatRoom findChatRoom = avatarChatRoom.getChatRoom();
          if (findChatRoom.getRoomType().equals(ChatRoomType.SINGLE)) {
            findChatRoom.leaveChatRoom(message.getSessionId());
            if (findChatRoom.getCurrentJoinNum() == 0
                && findChatRoom.getChatMessageList().size() == 0) {
              chatRoomRepository.delete(findChatRoom);
            }
          } else if (findChatRoom.getRoomType().equals(ChatRoomType.MULTI)) {
            findChatRoom.leaveChatRoom(message.getSessionId());
          }
        });
  }

  @Transactional
  public void sendChatMessage(ChatMessageDto message) {
    ChatRoom findChatRoom =
        chatRoomRepository
            .findChatRoomByUuid(message.getChatRoomUUID())
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));
    int count = findChatRoom.getParticipantsNum() - findChatRoom.getCurrentJoinNum();
    ChatMessage chatMessage =
        chatRoomMapper.toChatMessage(
            message.getType(),
            message.getSender(),
            message.getSenderId(),
            message.getMessage(),
            count);

    ChatMessage chatMessageToSend = findChatRoom.addChatMessage(chatMessage);

    sendingOperations.convertAndSend(
        "/sub/chatRooms/" + message.getChatRoomUUID().toString(),
        chatRoomMapper.toGetChatMessageDto(chatMessageToSend));
  }
}
