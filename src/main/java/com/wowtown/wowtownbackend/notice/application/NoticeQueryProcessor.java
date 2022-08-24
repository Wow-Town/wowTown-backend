package com.wowtown.wowtownbackend.notice.application;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.common.domain.InterestType;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.notice.application.common.NoticeMapper;
import com.wowtown.wowtownbackend.notice.application.dto.response.GetNoticeDetailDto;
import com.wowtown.wowtownbackend.notice.application.dto.response.GetNoticeDto;
import com.wowtown.wowtownbackend.notice.domain.Notice;
import com.wowtown.wowtownbackend.notice.domain.NoticeRepository;
import com.wowtown.wowtownbackend.notice.domain.NoticeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeQueryProcessor {
  private final NoticeRepository noticeRepository;
  private final NoticeMapper noticeMapper;

  public GetNoticeDetailDto getNoticeDetail(long noticeId) {
    Notice findNotice =
        noticeRepository
            .findById(noticeId)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 공고 입니다."));

    return noticeMapper.toGetNoticeDetailDto(findNotice);
  }

  public List<GetNoticeDto> getAllNoticeInChannel(Channel channel) {
    List<GetNoticeDto> noticeDtoList =
        noticeRepository.findByChannelId(channel.getId()).stream()
            .filter(notice -> notice.getNoticeStatus() == NoticeStatus.OPEN)
            .map(notice -> noticeMapper.toGetNoticeDto(notice))
            .collect(Collectors.toList());
    return noticeDtoList;
  }

  public List<GetNoticeDto> getNoticeWithSubjectInChannel(Channel channel, String subject) {
    List<GetNoticeDto> noticeDtoList =
        noticeRepository.findByChannelIdAndSubjectContaining(channel.getId(), subject).stream()
            .filter(notice -> notice.getNoticeStatus() == NoticeStatus.OPEN)
            .map(notice -> noticeMapper.toGetNoticeDto(notice))
            .collect(Collectors.toList());
    return noticeDtoList;
  }

  public List<GetNoticeDto> getNoticeWithInterestInChannel(
      Channel channel, List<String> interests) {
    Set<InterestType> interestSet =
        interests.stream()
            .map(interest -> InterestType.valueOf(interest))
            .collect(Collectors.toSet());

    return noticeRepository.findByChannelIdAndInterestList(channel.getId(), interestSet).stream()
        .filter(notice -> notice.getNoticeStatus() == NoticeStatus.OPEN)
        .map(notice -> noticeMapper.toGetNoticeDto(notice))
        .collect(Collectors.toList());
  }

  public List<GetNoticeDto> getNoticeWithSubjectAndInterestInChannel(
      Channel channel, String subject, List<String> interests) {
    Set<InterestType> interestSet =
        interests.stream()
            .map(interest -> InterestType.valueOf(interest))
            .collect(Collectors.toSet());

    return noticeRepository
        .findByChannelIdAndSubjectContainingAndInterestList(channel.getId(), subject, interestSet)
        .stream()
        .filter(notice -> notice.getNoticeStatus() == NoticeStatus.OPEN)
        .map(notice -> noticeMapper.toGetNoticeDto(notice))
        .collect(Collectors.toList());
  }
}
