package com.wowtown.wowtownbackend.studyGroup.controller;

import com.wowtown.wowtownbackend.studyGroup.application.StudyGroupQueryProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/studyGroups")
@RequiredArgsConstructor
public class StudyGroupQueryController {

  private final StudyGroupQueryProcessor studyGroupQueryProcessor;

  @GetMapping
  public ResponseEntity getAllStudyGroup() {
    log.info("testing");
    return ResponseEntity.status(HttpStatus.OK).body(studyGroupQueryProcessor.getAllStudyGroup());
  }

  @GetMapping(value = "/")
  public ResponseEntity getStudyGroupBySubject(@RequestParam("subject") String subject) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(studyGroupQueryProcessor.getStudyGroupBySubject(subject));
  }
  //  @GetMapping(value ="/InterestType")
  //  public ResponseEntity getStudyGroupByInterestType(){
  //
  //  }
}
