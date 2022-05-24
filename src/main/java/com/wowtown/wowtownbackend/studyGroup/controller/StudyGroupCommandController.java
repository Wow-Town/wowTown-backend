package com.wowtown.wowtownbackend.studyGroup.controller;


import com.wowtown.wowtownbackend.studyGroup.application.StudyGroupCommandExecutor;
import com.wowtown.wowtownbackend.studyGroup.application.dto.request.CreateStudyGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/studyGroups")
@RequiredArgsConstructor
public class StudyGroupCommandController {
    private final StudyGroupCommandExecutor studyGroupCommandExecutor;

    @PostMapping(value ="/newStudyGroup")
    public ResponseEntity createStudyGroup(@RequestBody  CreateStudyGroupDto dto){
        studyGroupCommandExecutor.createStudyGroup(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @PatchMapping(value ="/{studyGroupId}")
//    public ResponseEntity updateStudyGroup(){
//
//    }
//    @DeleteMapping(value ="/{studyGroupId}")
//    public ResponseEntity deleteStudyGroup(){
//
//    }

}
