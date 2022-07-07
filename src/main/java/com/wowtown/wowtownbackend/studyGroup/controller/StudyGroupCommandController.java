package com.wowtown.wowtownbackend.studyGroup.controller;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.argumentresolver.UserAvatar;
import com.wowtown.wowtownbackend.studyGroup.application.StudyGroupCommandExecutor;
import com.wowtown.wowtownbackend.studyGroup.application.dto.request.CreateOrUpdateStudyGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@Controller
@RequestMapping("/studyGroups")
@RequiredArgsConstructor
public class StudyGroupCommandController {
  private final StudyGroupCommandExecutor studyGroupCommandExecutor;

  @PostMapping
  public ResponseEntity createStudyGroup(
      @RequestBody CreateOrUpdateStudyGroupDto dto, @UserAvatar Avatar avatar) {
    studyGroupCommandExecutor.createStudyGroup(dto, avatar);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }

  @PutMapping(value = "/{studyGroupId}")
  public ResponseEntity updateStudyGroup(
      @PathVariable("studyGroupId") long studyGroupId,
      @RequestBody CreateOrUpdateStudyGroupDto dto,
      @UserAvatar Avatar avatar) {
    studyGroupCommandExecutor.updateStudyGroup(studyGroupId, dto, avatar);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @DeleteMapping(value = "/{studyGroupId}")
  public ResponseEntity deleteStudyGroup(
      @PathVariable("studyGroupId") long studyGroupId, @NotEmpty @UserAvatar Avatar avatar) {
    studyGroupCommandExecutor.deleteStudyGroup(studyGroupId, avatar);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
