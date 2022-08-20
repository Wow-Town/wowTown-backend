package com.wowtown.wowtownbackend.chatroom.application.common;

import com.wowtown.wowtownbackend.chatroom.application.dto.response.*;
import com.wowtown.wowtownbackend.chatroom.domain.ChatMessage;
import com.wowtown.wowtownbackend.chatroom.domain.ChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.ChatRoomType;
import com.wowtown.wowtownbackend.chatroom.domain.MessageType;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {

  ChatMessage toChatMessage(
      MessageType type, String sender, Long senderId, String message, Integer count);

  //  default ChatRoom toStudyGroupChatRoom(String roomName, Integer personnel) {
  //    if (roomName == null && personnel == null) {
  //      return null;
  //    }
  //
  //    ChatRoom chatRoom = new ChatRoom(roomName, personnel);
  //
  //    return chatRoom;
  //  }
  //
  //  default ChatRoom toUpdateChatRoom(String name, Integer personnel) {
  //    if (name == null) {
  //      return null;
  //    }
  //    if (personnel <= 0) { // 0명 이하 생성시
  //      return null;
  //    }
  //    ChatRoom chatRoom = new ChatRoom(name, personnel);
  //    return chatRoom;
  //  }

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

  GetChatMessageDto toGetChatMessageDto(ChatMessage chatMessage);

  GetParticipantAvatarDto toGetParticipantAvatarDto(Long avatarId, String nickName);

  GetChatRoomDto toGetChatRoomDto(
      UUID chatRoomUUID,
      String roomName,
      String latestMessage,
      Integer receiveMessageNum,
      ChatRoomType chatRoomType);

  GetCreatedChatRoomDto toGetCreatedChatRoomDto(String roomName, UUID chatRoomUUID);
}
