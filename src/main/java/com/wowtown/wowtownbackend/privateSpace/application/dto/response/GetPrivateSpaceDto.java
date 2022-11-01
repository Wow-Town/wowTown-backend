package com.wowtown.wowtownbackend.privateSpace.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPrivateSpaceDto {
  private UUID chatRoomUUID;

  private UUID privateSpaceUUID;

  private String roomName;

  private int participantsNum;
}
