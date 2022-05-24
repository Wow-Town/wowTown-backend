package com.wowtown.wowtownbackend.studyGroup.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudyGroupDto {
    private String studyGroupName;
    private Integer personnel;
    private String studyDetail;
    private int isOpen;
}
