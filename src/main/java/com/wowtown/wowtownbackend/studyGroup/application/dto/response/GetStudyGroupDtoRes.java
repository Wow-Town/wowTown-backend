package com.wowtown.wowtownbackend.studyGroup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetStudyGroupDtoRes {
    private Long studyGroupId;
    private String studyGroupName;
    private Integer personnel;
    private String studyDetail;
    private int isOpen;
}
