package com.wowtown.wowtownbackend.chatroom.application.common;

import com.wowtown.wowtownbackend.chatroom.application.dto.response.GetMultiChatRoomDto;
import com.wowtown.wowtownbackend.chatroom.domain.MultiChatRoom;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MultiChatRoomMapper {
  default MultiChatRoom toMultiChatRoom(String name, Integer personnel, StudyGroup studyGroup) {
    if (name == null) {
      return null;
    }
    if (personnel <= 0) { // 0명 이하 생성시
      return null;
    }
    MultiChatRoom multiChatRoom = new MultiChatRoom(name, personnel, studyGroup);
    return multiChatRoom;
  }

  default MultiChatRoom toUpdateMultiChatRoom(String name, Integer personnel) {
    if (name == null) {
      return null;
    }
    if (personnel <= 0) { // 0명 이하 생성시
      return null;
    }
    MultiChatRoom multiChatRoom = new MultiChatRoom(name, personnel);
    return multiChatRoom;
  }

  @Mapping(source = "id", target = "multiChatRoomId")
  GetMultiChatRoomDto toGetMultiChatRoomDto(MultiChatRoom multiChatRoom);
}
