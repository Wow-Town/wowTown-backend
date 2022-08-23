package com.wowtown.wowtownbackend.notice.application.common;

import com.wowtown.wowtownbackend.common.domain.Interest;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.notice.application.dto.response.GetNoticeDto;
import com.wowtown.wowtownbackend.notice.domain.Notice;
import com.wowtown.wowtownbackend.notice.domain.NoticeStatus;
import org.mapstruct.Mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NoticeMapper {
  default Notice toNotice(
      String subject,
      Integer personnel,
      String description,
      List<String> interestList,
      String NoticeStutus) {
    Set<Interest> interestSet = new HashSet<>();

    if (subject == null
        && personnel == null
        && description == null
        && interestList == null
        && NoticeStutus == null) {
      return null;
    }

    for (String getInterest : interestList) {
      Interest interest = new Interest(InterestType.valueOf(getInterest));
      interestSet.add(interest);
    }

    Notice notice =
        new Notice(
            subject,
            personnel,
            description,
            interestSet,
            NoticeStatus.valueOf(NoticeStutus));

    return notice;
  }

  default GetNoticeDto toGetNoticeDto(Notice notice) {
    if (notice == null) {
      return null;
    }

    GetNoticeDto getNoticeDto = new GetNoticeDto();

    getNoticeDto.setNoticeId(notice.getId());
    getNoticeDto.setSubject(notice.getSubject());
    getNoticeDto.setPersonnel(notice.getPersonnel());
    getNoticeDto.setDescription(notice.getDescription());
    List<String> interestList =
        notice.getInterestSet().stream()
            .map(interest -> interest.getType().toString())
            .collect(Collectors.toList());

    getNoticeDto.setInterests(interestList);
    getNoticeDto.setStatus(notice.getNoticeStatus());

    return getNoticeDto;
  }
}
