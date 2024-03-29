package com.wowtown.wowtownbackend.notice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetNoticeDto {
  private Long noticeId;

  private String ownerNickName;

  private String subject;

  private Set<String> interests;
}
