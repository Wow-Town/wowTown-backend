package com.wowtown.wowtownbackend.privateSpace.application.common;

import com.wowtown.wowtownbackend.privateSpace.application.dto.response.GetPrivateSpaceDto;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PrivateSpaceMapper {
  default GetPrivateSpaceDto toGetPrivateSpaceDto(
      UUID chatRoomUUID, UUID privateSpaceUUID, String roomName, Integer participantsNum) {
    if (chatRoomUUID == null
        && privateSpaceUUID == null
        && roomName == null
        && participantsNum == null) {
      return null;
    }
    GetPrivateSpaceDto getPrivateSpaceDto = new GetPrivateSpaceDto();

    if (chatRoomUUID != null) {
      getPrivateSpaceDto.setChatRoomUUID(chatRoomUUID);
    }
    if (privateSpaceUUID != null) {
      getPrivateSpaceDto.setPrivateSpaceUUID(privateSpaceUUID);
    }
    if (roomName != null) {
      getPrivateSpaceDto.setRoomName(roomName);
    }
    if (participantsNum != null) {
      getPrivateSpaceDto.setParticipantsNum(participantsNum);
    }
    return getPrivateSpaceDto;
  }
}
