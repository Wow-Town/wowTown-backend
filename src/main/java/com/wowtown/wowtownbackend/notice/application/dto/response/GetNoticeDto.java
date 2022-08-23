package com.wowtown.wowtownbackend.notice.application.dto.response;

import com.wowtown.wowtownbackend.notice.domain.NoticeStatus;
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

  private String subject;

  private int personnel;

  private String description;

  private List<String> interests;

  private NoticeStatus status;
}
