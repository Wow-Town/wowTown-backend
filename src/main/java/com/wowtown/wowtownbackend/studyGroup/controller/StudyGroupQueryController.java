package com.wowtown.wowtownbackend.studyGroup.controller;

import com.wowtown.wowtownbackend.studyGroup.application.StudyGroupQueryProcesseor;
import com.wowtown.wowtownbackend.studyGroup.application.dto.request.GetStudyGroupByNameDto;
import com.wowtown.wowtownbackend.studyGroup.application.dto.response.GetStudyGroupDtoRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/studyGroups")
@RequiredArgsConstructor
public class StudyGroupQueryController {

  private final StudyGroupQueryProcesseor studyGroupQueryProcesseor;

  @GetMapping
  public ResponseEntity getAllStudyGroup() {
    log.info("testing");
    return ResponseEntity.status(HttpStatus.OK).body(studyGroupQueryProcesseor.getAllStudyGroup());
  }
  @GetMapping(value ="/name")
  public ResponseEntity getStudyGroupByName(@RequestBody GetStudyGroupByNameDto dto){
     log.info("name ={}",dto.getStudyGroupNamePart());
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(studyGroupQueryProcesseor.getStudyGroupByName(dto));
  }
//  @GetMapping(value ="/InterestType")
//  public ResponseEntity getStudyGroupByInterestType(){
//
//  }
}
