package com.wowtown.wowtownbackend.studyGroup.application;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.studyGroup.application.common.StudyGroupMapper;
import com.wowtown.wowtownbackend.studyGroup.application.dto.response.GetStudyGroupDto;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupRepository;
import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroupStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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

  public List<GetStudyGroupDto> getStudyGroupWithAvatar(Avatar avatar) {
    return studyGroupRepository.findByAvatarId(avatar.getId()).stream()
        .map(studyGroup -> studyGroupMapper.toGetStudyGroupDto(studyGroup))
        .collect(Collectors.toList());
  }

  public List<GetStudyGroupDto> getStudyGroupWithSubject(String subject) {
    List<GetStudyGroupDto> studyGroupDtoList =
        studyGroupRepository.findBySubjectContaining(subject).stream()
            .filter(studyGroup -> studyGroup.getStatus() == StudyGroupStatus.OPEN)
            .map(studyGroup -> studyGroupMapper.toGetStudyGroupDto(studyGroup))
            .collect(Collectors.toList());
    return studyGroupDtoList;
  }

  public List<GetStudyGroupDto> getStudyGroupWithInterest(List<String> interests) {
    Set<InterestType> interestSet =
        interests.stream()
            .map(interest -> InterestType.valueOf(interest))
            .collect(Collectors.toSet());

    return studyGroupRepository.findByInterestList(interestSet).stream()
        .map(studyGroup -> studyGroupMapper.toGetStudyGroupDto(studyGroup))
        .collect(Collectors.toList());
  }

  public List<GetStudyGroupDto> getStudyGroupWithSubjectAndInterest(
      String subject, List<String> interests) {
    Set<InterestType> interestSet =
        interests.stream()
            .map(interest -> InterestType.valueOf(interest))
            .collect(Collectors.toSet());

    return studyGroupRepository
        .findBySubjectContainingAndInterestList(subject, interestSet)
        .stream()
        .map(studyGroup -> studyGroupMapper.toGetStudyGroupDto(studyGroup))
        .collect(Collectors.toList());
  }
}
