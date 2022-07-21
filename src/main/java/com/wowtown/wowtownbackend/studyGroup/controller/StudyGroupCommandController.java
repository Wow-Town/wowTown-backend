package com.wowtown.wowtownbackend.studyGroup.controller;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import com.wowtown.wowtownbackend.studyGroup.application.StudyGroupCommandExecutor;
import com.wowtown.wowtownbackend.studyGroup.application.dto.request.CreateOrUpdateStudyGroupDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Controller
@RequestMapping("/studyGroups")
@RequiredArgsConstructor
public class StudyGroupCommandController {
  private final StudyGroupCommandExecutor studyGroupCommandExecutor;

  @ApiOperation(value = "공고 생성", notes = ".")
  @PostMapping
  public ResponseEntity createStudyGroup(
      @Valid @RequestBody CreateOrUpdateStudyGroupDto dto, @ApiIgnore @UserAvatar Avatar avatar) {
    studyGroupCommandExecutor.createStudyGroup(dto, avatar);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }

  @ApiOperation(value = "공고 업데이트", notes = "=")
  @PutMapping(value = "/{studyGroupId}")
  public ResponseEntity updateStudyGroup(
      @PathVariable("studyGroupId") @Min(1) long studyGroupId,
      @Valid @RequestBody CreateOrUpdateStudyGroupDto dto,
      @ApiIgnore @UserAvatar Avatar avatar) {
    studyGroupCommandExecutor.updateStudyGroup(studyGroupId, dto, avatar);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @ApiOperation(value = "공고 삭제", notes = "")
  @ApiImplicitParam(name ="studyGroupId",value="공고 id",required = true, dataType = "string",paramType = "path",defaultValue = "None")
  @DeleteMapping(value = "/{studyGroupId}")
  public ResponseEntity deleteStudyGroup(
      @PathVariable("studyGroupId") @Min(1) long studyGroupId,@ApiIgnore  @UserAvatar Avatar avatar) {
    studyGroupCommandExecutor.deleteStudyGroup(studyGroupId, avatar);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
