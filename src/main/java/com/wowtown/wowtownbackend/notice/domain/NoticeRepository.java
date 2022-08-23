package com.wowtown.wowtownbackend.notice.domain;

/*import com.wowtown.wowtownbackend.common.domain.InterestType;*/

import com.wowtown.wowtownbackend.common.domain.InterestType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface NoticeRepository {
  Notice save(Notice notice);

  void delete(Notice notice);

  void deleteAll();

  List<Notice> findByChannelId(Long channelId);

  Optional<Notice> findById(Long noticeId);

  List<Notice> findByChannelIdAndSubjectContaining(Long channelId, String subject);

  List<Notice> findByChannelIdAndInterestList(Long channelId, Set<InterestType> interestList);

  List<Notice> findByChannelIdAndSubjectContainingAndInterestList(
      Long channelId, String subject, Set<InterestType> interestList);
}
