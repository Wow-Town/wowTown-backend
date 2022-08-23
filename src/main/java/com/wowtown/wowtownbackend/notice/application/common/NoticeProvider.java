package com.wowtown.wowtownbackend.notice.application.common;

import com.wowtown.wowtownbackend.notice.domain.Notice;

public interface NoticeProvider {
  public Notice getNotice(long noticeId);
}
