package com.wowtown.wowtownbackend.studyGroup.controller;

import com.wowtown.wowtownbackend.avatar.application.AvatarQueryProcessor;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.argumentresolver.UserAvatar;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.studyGroup.application.StudyGroupQueryProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/studyGroups")
@RequiredArgsConstructor
public class StudyGroupQueryController {

  private final StudyGroupQueryProcessor studyGroupQueryProcessor;
  private final AvatarQueryProcessor avatarQueryProcessor;

  @GetMapping
  public ResponseEntity getAllStudyGroup() {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(studyGroupQueryProcessor.getAllStudyGroup());
  }

  @GetMapping(value = "/avatar")
  public ResponseEntity getStudyGroupWithQuery(@UserAvatar Avatar avatar) {

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(studyGroupQueryProcessor.getStudyGroupWithAvatar(avatar));
  }

  @GetMapping(value = "/search")
  public ResponseEntity getStudyGroupWithQuery(
      @RequestParam(required = false, value = "subject") String subject,
      @RequestParam(required = false, value = "interests") List<InterestType> interests) {
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
