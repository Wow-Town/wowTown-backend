package com.wowtown.wowtownbackend.chatroom.application.common;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetChatRoomAvatarDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetChatRoomDetailDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetCreatedChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.domain.ChatRoom;
import com.wowtown.wowtownbackend.chatroom.domain.ChatRoomType;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
  default ChatRoom toAvatarChatRoom(String roomName) {
    if (roomName == null) {
      return null;
    }

    ChatRoom chatRoom = new ChatRoom(roomName);

    return chatRoom;
  }

  default ChatRoom toStudyGroupChatRoom(String roomName, Integer personnel) {
    if (roomName == null && personnel == null) {
      return null;
    }

    ChatRoom chatRoom = new ChatRoom(roomName, personnel);

    return chatRoom;
  }

  default ChatRoom toUpdateChatRoom(String name, Integer personnel) {
    if (name == null) {
      return null;
    }
    if (personnel <= 0) { // 0명 이하 생성시
      return null;
    }
    ChatRoom chatRoom = new ChatRoom(name, personnel);
    return chatRoom;
  }

  default GetChatRoomDetailDto toGetChatRoomDetailDto(ChatRoom chatRoom, List<Avatar> avatarList) {
    if (chatRoom == null) {
      return null;
    }

    GetChatRoomDetailDto getChatRoomDetailDto = new GetChatRoomDetailDto();

    getChatRoomDetailDto.setChatRoomUuid(chatRoom.getUuid());
    getChatRoomDetailDto.setPersonnel(chatRoom.getPersonnel());
    getChatRoomDetailDto.setParticipantsNum(chatRoom.getParticipantsNum());

    List<GetChatRoomAvatarDto> getChatRoomAvatarDtoList = new ArrayList<>();

    for (Avatar avatar : avatarList) {
      GetChatRoomAvatarDto getChatRoomAvatarDto = new GetChatRoomAvatarDto();

      getChatRoomAvatarDto.setAvatarId(avatar.getId());
      getChatRoomAvatarDto.setAvatarName(avatar.getNickName());

      getChatRoomAvatarDtoList.add(getChatRoomAvatarDto);
    }

    getChatRoomDetailDto.setAvatarList(getChatRoomAvatarDtoList);

    return getChatRoomDetailDto;
  }

  GetChatRoomDto toGetChatRoomDto(UUID chatRoomUUID, String roomName, ChatRoomType chatRoomType);

  GetCreatedChatRoomDto toGetCreatedChatRoomDto(UUID chatRoomUUID);
}
