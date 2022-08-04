package com.wowtown.wowtownbackend.studyGroup.application;

import com.wowtown.wowtownbackend.channel.domain.Channel;
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

  public List<GetStudyGroupDto> getAllStudyGroupInChannel(Channel channel) {
    List<GetStudyGroupDto> studyGroupDtoList =
        studyGroupRepository.findByChannelId(channel.getId()).stream()
            .filter(studyGroup -> studyGroup.getStudyGroupStatus() == StudyGroupStatus.OPEN)
            .map(studyGroup -> studyGroupMapper.toGetStudyGroupDto(studyGroup))
            .collect(Collectors.toList());
    return studyGroupDtoList;
  }

  public List<GetStudyGroupDto> getStudyGroupWithSubjectInChannel(Channel channel, String subject) {
    List<GetStudyGroupDto> studyGroupDtoList =
        studyGroupRepository.findByChannelIdAndSubjectContaining(channel.getId(), subject).stream()
            .filter(studyGroup -> studyGroup.getStudyGroupStatus() == StudyGroupStatus.OPEN)
            .map(studyGroup -> studyGroupMapper.toGetStudyGroupDto(studyGroup))
            .collect(Collectors.toList());
    return studyGroupDtoList;
  }

  public List<GetStudyGroupDto> getStudyGroupWithInterestInChannel(
      Channel channel, List<String> interests) {
    Set<InterestType> interestSet =
        interests.stream()
            .map(interest -> InterestType.valueOf(interest))
            .collect(Collectors.toSet());

    return studyGroupRepository
        .findByChannelIdAndInterestList(channel.getId(), interestSet)
        .stream()
        .filter(studyGroup -> studyGroup.getStudyGroupStatus() == StudyGroupStatus.OPEN)
        .map(studyGroup -> studyGroupMapper.toGetStudyGroupDto(studyGroup))
        .collect(Collectors.toList());
  }

  public List<GetStudyGroupDto> getStudyGroupWithSubjectAndInterestInChannel(
      Channel channel, String subject, List<String> interests) {
    Set<InterestType> interestSet =
        interests.stream()
            .map(interest -> InterestType.valueOf(interest))
            .collect(Collectors.toSet());

    return studyGroupRepository
        .findByChannelIdAndSubjectContainingAndInterestList(channel.getId(), subject, interestSet)
        .stream()
        .filter(studyGroup -> studyGroup.getStudyGroupStatus() == StudyGroupStatus.OPEN)
        .map(studyGroup -> studyGroupMapper.toGetStudyGroupDto(studyGroup))
        .collect(Collectors.toList());
  }
}
