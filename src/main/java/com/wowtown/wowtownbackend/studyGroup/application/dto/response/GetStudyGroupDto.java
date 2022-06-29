package com.wowtown.wowtownbackend.studyGroup.application.dto.response;

import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetStudyGroupDto {
  private Long studyGroupId;
  private String subject;
  private int personnel;
  private String description;
  private StudyGroupStatus status;
}
