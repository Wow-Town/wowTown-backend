package com.wowtown.wowtownbackend.studyGroup.application.dto.request;

import com.wowtown.wowtownbackend.common.annotation.ValidInterestType;
import com.wowtown.wowtownbackend.common.annotation.ValidStudyGroupStatus;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateStudyGroupDto {
  @NotEmpty(message = "subject 필드는 비어있을 수 없습니다.")
  private String subject;

  @Min(1)
  private int personnel;

  private String description;

  @Size(min = 3, max = 3)
  @ValidInterestType(message = "존재하지 않는 관심사 입니다.", enumClass = InterestType.class)
  private List<String> interests;

  @ValidStudyGroupStatus(message = "존재하지 않는 상태 입니다.", enumClass = StudyGroupStatus.class)
  private String status;
}
