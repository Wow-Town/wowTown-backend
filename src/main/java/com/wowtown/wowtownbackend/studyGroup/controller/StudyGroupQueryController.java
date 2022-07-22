package com.wowtown.wowtownbackend.studyGroup.controller;

import com.wowtown.wowtownbackend.avatar.application.AvatarQueryProcessor;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import com.wowtown.wowtownbackend.common.annotation.ValidInterestType;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.studyGroup.application.StudyGroupQueryProcessor;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/studyGroups")
@RequiredArgsConstructor
public class StudyGroupQueryController {

  private final StudyGroupQueryProcessor studyGroupQueryProcessor;
  private final AvatarQueryProcessor avatarQueryProcessor;

  @ApiOperation(value = "모든 공고 조회", notes = ".")
  @GetMapping
  public ResponseEntity getAllStudyGroup() {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(studyGroupQueryProcessor.getAllStudyGroup());
  }
  @ApiOperation(value = "아바타가 참여하고 있는 공고 조회", notes = "=.")
  @GetMapping(value = "/avatar")
  public ResponseEntity getStudyGroupWithQuery(@ApiIgnore @UserAvatar Avatar avatar) {

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(studyGroupQueryProcessor.getStudyGroupWithAvatar(avatar));
  }

  @ApiOperation(value = "제목,관심사를 통해 공고 조회", notes = "")
  @GetMapping(value = "/search")
  public ResponseEntity getStudyGroupWithQuery(
      @RequestParam(required = false, value = "subject") @NotEmpty String subject,
      @RequestParam(required = false, value = "interests")
          @ValidInterestType(message = "존재하지 않는 관심사 입니다.", enumClass = InterestType.class)
          List<String> interests) {
    if (subject != null && interests != null) {
      return ResponseEntity.status(HttpStatus.OK)
          .contentType(MediaType.APPLICATION_JSON)
          .body(studyGroupQueryProcessor.getStudyGroupWithSubjectAndInterest(subject, interests));
    }
    if (subject != null) {
      return ResponseEntity.status(HttpStatus.OK)
          .contentType(MediaType.APPLICATION_JSON)
          .body(studyGroupQueryProcessor.getStudyGroupWithSubject(subject));
    } else {
      return ResponseEntity.status(HttpStatus.OK)
          .contentType(MediaType.APPLICATION_JSON)
          .body(studyGroupQueryProcessor.getStudyGroupWithInterest(interests));
    }
  }
}
