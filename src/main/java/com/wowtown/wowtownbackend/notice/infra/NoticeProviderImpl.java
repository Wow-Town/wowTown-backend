package com.wowtown.wowtownbackend.notice.infra;

import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.notice.application.common.NoticeProvider;
import com.wowtown.wowtownbackend.notice.domain.Notice;
import com.wowtown.wowtownbackend.notice.domain.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeProviderImpl implements NoticeProvider {
  private final NoticeRepository noticeRepository;

  @Override
  public Notice getNotice(long NoticeId) {
    Notice findNotice =
        noticeRepository
            .findById(NoticeId)
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    return findNotice;
  }
}
