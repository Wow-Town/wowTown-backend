package com.wowtown.wowtownbackend.chatroom.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.common.ChatRoomMapper;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.ChatMessageDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.request.InviteAvatar;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetCreatedChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetUploadFileDto;
import com.wowtown.wowtownbackend.chatroom.domain.*;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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
  private final AmazonS3 amazonS3;

  @Transactional
  public GetCreatedChatRoomDto createAvatarChatroom(InviteAvatar dto, Avatar avatar) {
    // 초대한 아바타를 참여한 아바타 리스트를 만든다.
    Avatar invitedAvatar = avatarProvider.getAvatar(dto.getAvatarId());

    ChatRoom chatRoom = null;
    List<AvatarChatRoom> myAvatarChatRoomList =
        avatarChatRoomRepository.findAvatarChatRoomByAvatarId(avatar.getId());

    for (AvatarChatRoom avatarChatRoom : myAvatarChatRoomList) {
      if (avatarChatRoom.getDefaultRoomName().equals(invitedAvatar.getNickName())) {
        chatRoom = avatarChatRoom.getChatRoom();
        break;
      }
    }

    if (chatRoom == null) {
      ChatRoomType chatRoomType = ChatRoomType.SINGLE;

      // 채팅방을 만든다.
      chatRoom = new ChatRoom(chatRoomType);

      chatRoom.addAvatarChatRoom(invitedAvatar.getNickName(), avatar);
      chatRoom.addAvatarChatRoom(avatar.getNickName(), invitedAvatar);

      chatRoomRepository.save(chatRoom);
    }
    return chatRoomMapper.toGetCreatedChatRoomDto(invitedAvatar.getNickName(), chatRoom.getUuid());
  }

  @Transactional
  public UUID createNoticeChatroom(String subject, Avatar avatar) {

    ChatRoomType chatRoomType = ChatRoomType.MULTI;

    ChatRoom chatRoom = new ChatRoom(chatRoomType);

    chatRoom.addAvatarChatRoom(subject, avatar);

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
  public void enterChatRoom(ChatMessageDto message, String sessionId) {
    ChatRoom findChatRoom =
        chatRoomRepository
            .findChatRoomByUuid(message.getChatRoomUUID())
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));

    findChatRoom.enterChatRoom(sessionId, message.getSenderId());
    sendingOperations.convertAndSend(
        "/sub/chatRooms/" + message.getChatRoomUUID().toString(), message);
  }

  @Transactional
  public void leaveChatRoom(String sessionId) {
    Optional<AvatarChatRoom> findAvatarChatroom =
        avatarChatRoomRepository.findAvatarChatRoomWithSessionId(sessionId);

    findAvatarChatroom.ifPresent(
        avatarChatRoom -> {
          ChatRoom findChatRoom = avatarChatRoom.getChatRoom();
          if (findChatRoom.getRoomType().equals(ChatRoomType.SINGLE)) {
            findChatRoom.leaveChatRoom(sessionId);
            if (findChatRoom.getCurrentJoinNum() == 0
                && findChatRoom.getChatMessageList().size() == 0) {
              chatRoomRepository.delete(findChatRoom);
            }
          } else if (findChatRoom.getRoomType().equals(ChatRoomType.MULTI)) {
            findChatRoom.leaveChatRoom(sessionId);
          }
        });
  }

  @Transactional
  public void sendChatMessage(ChatMessageDto message) {
    ChatRoom findChatRoom =
        chatRoomRepository
            .findChatRoomByUuid(message.getChatRoomUUID())
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방 입니다."));

    Avatar sender = avatarProvider.getAvatar(message.getSenderId());

    int count = findChatRoom.getParticipantsNum() - findChatRoom.getCurrentJoinNum();
    ChatMessage chatMessage =
        chatRoomMapper.toChatMessage(message.getType(), sender, message.getMessage(), count);

    ChatMessage chatMessageToSend = findChatRoom.addChatMessage(chatMessage);

    sendingOperations.convertAndSend(
        "/sub/chatRooms/" + message.getChatRoomUUID().toString(),
        chatRoomMapper.toGetChatMessageDto(chatMessageToSend));
  }

  @Transactional
  public List<GetUploadFileDto> fileUpload(
      UUID chatRoomUUID, MultipartFile[] files, Avatar avatar) {
    try {
      ChatRoom findChatRoom =
          chatRoomRepository
              .findChatRoomByUuid(chatRoomUUID)
              .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 채팅방입니다."));

      String bucket = "wowtown";
      String chatRoomId = findChatRoom.getId().toString();
      List<GetUploadFileDto> getUploadFileDtos = new ArrayList<>();

      for (MultipartFile file : files) {
        String fileIdx = UUID.randomUUID().toString();
        String key = chatRoomId + "/" + fileIdx + "/" + file.getOriginalFilename();
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(file.getContentType());
        data.setContentLength(file.getSize());

        amazonS3.putObject(bucket, key, file.getInputStream(), data);

        String url = amazonS3.getUrl(bucket, key).toString();
        getUploadFileDtos.add(chatRoomMapper.toGetUploadFileDto(file.getContentType(), url));
      }

      return getUploadFileDtos;
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}
