package com.wowtown.wowtownbackend.studyGroup.application;

import com.wowtown.wowtownbackend.studyGroup.application.common.StudyGroupMapper;
import com.wowtown.wowtownbackend.studyGroup.application.dto.response.GetStudyGroupDto;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRepository;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyGroupQueryProcessor {
  private final StudyGroupRepository studyGroupRepository;
  private final StudyGroupMapper studyGroupMapper;

  public List<GetStudyGroupDto> getAllStudyGroup() {
    List<GetStudyGroupDto> studyGroupDtoList =
        studyGroupRepository.findAll().stream()
            .map(studyGroup -> studyGroupMapper.toGetStudyGroupDto(studyGroup))
            .collect(Collectors.toList());
    return studyGroupDtoList;
  }

  public List<GetStudyGroupDto> getStudyGroupBySubject(String subject) {
    List<GetStudyGroupDto> studyGroupDtoList =
        studyGroupRepository.findBySubjectContaining(subject).stream()
            .filter(studyGroup -> studyGroup.getStatus() == StudyGroupStatus.OPEN)
            .map(studyGroup -> studyGroupMapper.toGetStudyGroupDto(studyGroup))
            .collect(Collectors.toList());
    return studyGroupDtoList;
  }

  //    public List<GetStudyGroupDtoRes> getStudyGroupByInterest(){
  //        List<GetStudyGroupDtoRes> studyGroupDtoList = studyGroupRepository
  //                .findByInterestTypes()
  //                .stream()
  //                .filter(studyGroup->studyGroup.getIsOpen() ==1)
  //                .map(studyGroup -> studyGroupMapper.toGetStudyGroupDtoRes(studyGroup))
  //                .collect(Collectors.toList());
  //        return studyGroupDtoList;
  //
  //    }
}
