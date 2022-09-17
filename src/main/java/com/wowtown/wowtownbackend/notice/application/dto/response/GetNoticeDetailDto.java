package com.wowtown.wowtownbackend.notice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetNoticeDetailDto {
  private Long ownerId;

  private String subject;

  private String description;

  private Set<String> interests;

  private String randomPW;

  private UUID chatRoomUUID;
}
