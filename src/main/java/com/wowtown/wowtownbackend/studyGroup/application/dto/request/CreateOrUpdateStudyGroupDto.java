package com.wowtown.wowtownbackend.studyGroup.application.dto.request;

import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateStudyGroupDto {
  @NotEmpty(message = "avatarId 필드는 비어있을 수 없습니다.")
  private long avatarId;

  @NotEmpty(message = "subject 필드는 비어있을 수 없습니다.")
  private String subject;

  @Min(1)
  @NotEmpty(message = "personnel 필드는 비어있을 수 없습니다.")
  private int personnel;

  private String description;

  private StudyGroupStatus status;
}
