package com.wowtown.wowtownbackend.notice.application.common;

import com.wowtown.wowtownbackend.common.domain.Interest;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.notice.application.dto.response.GetNoticeDetailDto;
import com.wowtown.wowtownbackend.notice.application.dto.response.GetNoticeDto;
import com.wowtown.wowtownbackend.notice.domain.Notice;
import org.mapstruct.Mapper;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NoticeMapper {
  default Notice toNotice(String subject, String description, List<String> interestList) {
    Set<Interest> interestSet = new HashSet<>();

    if (subject == null && description == null && interestList == null) {
      return null;
    }

    for (String getInterest : interestList) {
      Interest interest = new Interest(InterestType.valueOf(getInterest));
      interestSet.add(interest);
    }

    Notice notice = new Notice(subject, description, interestSet);

    return notice;
  }

  default GetNoticeDto toGetNoticeDto(Notice notice) {
    if (notice == null) {
      return null;
    }

    GetNoticeDto getNoticeDto = new GetNoticeDto();

    getNoticeDto.setNoticeId(notice.getId());
    getNoticeDto.setSubject(notice.getSubject());
    getNoticeDto.setOwnerNickName((notice.getAvatar().getNickName()));
    Set<String> interestSet =
        notice.getInterestSet().stream()
            .map(interest -> interest.getType().toString())
            .collect(Collectors.toCollection(LinkedHashSet::new));

    getNoticeDto.setInterests(interestSet);

    return getNoticeDto;
  }

  default GetNoticeDetailDto toGetNoticeDetailDto(Notice notice) {
    if (notice == null) {
      return null;
    }

    GetNoticeDetailDto getNoticeDetailDto = new GetNoticeDetailDto();

    getNoticeDetailDto.setOwnerId(notice.getAvatar().getId());
    getNoticeDetailDto.setSubject(notice.getSubject());
    getNoticeDetailDto.setDescription((notice.getDescription()));
    Set<String> interestSet =
        notice.getInterestSet().stream()
            .map(interest -> interest.getType().toString())
            .collect(Collectors.toCollection(LinkedHashSet::new));

    getNoticeDetailDto.setInterests(interestSet);
    getNoticeDetailDto.setRandomPW(notice.getRandomPW());
    getNoticeDetailDto.setChatRoomUUID(notice.getChatRoom().getUuid());
    getNoticeDetailDto.setPrivateSpaceUUID(notice.getPrivateSpace().getUuid());

    return getNoticeDetailDto;
  }
}
