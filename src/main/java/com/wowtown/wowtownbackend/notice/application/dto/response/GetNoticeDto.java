package com.wowtown.wowtownbackend.notice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetNoticeDto {
  private Long noticeId;

  private String ownerNickName;

  private String subject;

  private List<String> interests;
}
