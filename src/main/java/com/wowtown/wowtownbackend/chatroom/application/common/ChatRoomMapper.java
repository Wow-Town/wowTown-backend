package com.wowtown.wowtownbackend.chatroom.application.common;

import com.wowtown.wowtownbackend.chatroom.application.dto.response.*;
import com.wowtown.wowtownbackend.chatroom.domain.ChatMessage;
import com.wowtown.wowtownbackend.chatroom.domain.ChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.MessageType;
import org.mapstruct.Mapper;

import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {

  default ChatMessage toChatMessage(
      MessageType type, String sender, Long senderId, byte[] message, Integer count) {
    if (type == null && sender == null && senderId == null && message == null && count == null) {
      return null;
    }

    MessageType type1 = null;
    if (type != null) {
      type1 = type;
    }
    String sender1 = null;
    if (sender != null) {
      sender1 = sender;
    }
    Long senderId1 = null;
    if (senderId != null) {
      senderId1 = senderId;
    }
    byte[] message1 = null;
    if (message != null) {
      message1 = message;
    }
    Integer count1 = null;
    if (count != null) {
      count1 = count;
    }

    ChatMessage chatMessage = new ChatMessage(type1, sender1, senderId1, message1, count1);

    return chatMessage;
  }

  default GetChatRoomDetailDto toGetChatRoomDetailDto(ChatRoom chatRoom) {
    if (chatRoom == null) {
      return null;
    }

    GetChatRoomDetailDto getChatRoomDetailDto = new GetChatRoomDetailDto();

    List<GetChatMessageDto> getChatMessageDtoList =
        chatRoom.getChatMessageList().stream()
            .map(chatMessage -> toGetChatMessageDto(chatMessage))
            .collect(Collectors.toList());

    getChatRoomDetailDto.setChatMessageList(getChatMessageDtoList);

    List<GetParticipantAvatarDto> getParticipantAvatarDtoList =
        chatRoom.getAvatarChatRoomSet().stream()
            .map(
                avatarChatRoom ->
                    toGetParticipantAvatarDto(
                        avatarChatRoom.getAvatar().getId(),
                        avatarChatRoom.getAvatar().getNickName()))
            .collect(Collectors.toList());

    getChatRoomDetailDto.setAvatarList(getParticipantAvatarDtoList);

    return getChatRoomDetailDto;
  }

  GetParticipantAvatarDto toGetParticipantAvatarDto(Long avatarId, String nickName);

  default GetChatMessageDto toGetChatMessageDto(ChatMessage chatMessage) {
    if (chatMessage == null) {
      return null;
    }

    GetChatMessageDto getChatMessageDto = new GetChatMessageDto();

    getChatMessageDto.setType(chatMessage.getType());
    getChatMessageDto.setSender(chatMessage.getSender());
    getChatMessageDto.setSenderId(chatMessage.getSenderId());
    byte[] message = chatMessage.getMessage();
    if (message != null) {
      if (chatMessage.getType() != MessageType.TALK) {
        String base64Encoding = Base64.getEncoder().encodeToString(message);
        getChatMessageDto.setMessage(base64Encoding);
      }
      getChatMessageDto.setMessage(new String(message));
    }
    if (chatMessage.getCount() != null) {
      getChatMessageDto.setCount(chatMessage.getCount());
    }
    getChatMessageDto.setSendAt(chatMessage.getSendAt());

    return getChatMessageDto;
  }

  default GetChatRoomDto toGetChatRoomDto(
      UUID chatRoomUUID,
      String roomName,
      byte[] latestMessage,
      Integer receiveMessageNum,
      Integer participantsNum,
      String chatRoomType) {
    if (chatRoomUUID == null
        && roomName == null
        && latestMessage == null
        && receiveMessageNum == null
        && participantsNum == null
        && chatRoomType == null) {
      return null;
    }

    GetChatRoomDto getChatRoomDto = new GetChatRoomDto();

    if (chatRoomUUID != null) {
      getChatRoomDto.setChatRoomUUID(chatRoomUUID);
    }
    if (roomName != null) {
      getChatRoomDto.setRoomName(roomName);
    }
    if (latestMessage != null) {
      getChatRoomDto.setLatestMessage(new String(latestMessage));
    }
    if (receiveMessageNum != null) {
      getChatRoomDto.setReceiveMessageNum(receiveMessageNum);
    }
    if (participantsNum != null) {
      getChatRoomDto.setParticipantsNum(participantsNum);
    }
    if (chatRoomType != null) {
      getChatRoomDto.setChatRoomType(chatRoomType);
    }

    return getChatRoomDto;
  }

  GetCreatedChatRoomDto toGetCreatedChatRoomDto(String roomName, UUID chatRoomUUID);

  GetUploadFileDto toGetUploadFileDto(String contentType, String url);
}
