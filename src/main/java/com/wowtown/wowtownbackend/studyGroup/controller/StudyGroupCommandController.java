package com.wowtown.wowtownbackend.studyGroup.controller;

import com.wowtown.wowtownbackend.studyGroup.application.StudyGroupCommandExecutor;
import com.wowtown.wowtownbackend.studyGroup.application.dto.request.CreateOrUpdateStudyGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/studyGroups")
@RequiredArgsConstructor
public class StudyGroupCommandController {
  private final StudyGroupCommandExecutor studyGroupCommandExecutor;

  @PostMapping
  public ResponseEntity createStudyGroup(@RequestBody CreateOrUpdateStudyGroupDto dto) {
    studyGroupCommandExecutor.createStudyGroup(dto);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  //  @PutMapping(value = "/{studyGroupId}")
  //  public ResponseEntity updateStudyGroup(
  //      @PathVariable("studyGroupId") long studyGroupId,
  //      @RequestBody CreateOrUpdateStudyGroupDto dto) {
  //    studyGroupCommandExecutor.updateStudyGroup(studyGroupId, dto);
  //  }
  //
  //  @DeleteMapping(value = "/{studyGroupId}")
  //  public ResponseEntity deleteStudyGroup() {}
}
