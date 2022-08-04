package com.wowtown.wowtownbackend.studyGroup.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudyGroupChatRoomDto {
  private String roomName;

  private int personnel;
}
