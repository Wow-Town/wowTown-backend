package com.wowtown.wowtownbackend.studyGroup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetStudyGroupByNameDto {

  private String studyGroupNamePart;
}
