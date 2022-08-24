package com.wowtown.wowtownbackend.notice.application.dto.request;

import com.wowtown.wowtownbackend.common.annotation.ValidInterestType;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateNoticeDto {
  @NotEmpty(message = "subject 필드는 비어있을 수 없습니다.")
  private String subject;

  private String description;

  @Size(min = 3, max = 3)
  @ValidInterestType(message = "존재하지 않는 관심사 입니다.", enumClass = InterestType.class)
  private List<String> interests;
}
