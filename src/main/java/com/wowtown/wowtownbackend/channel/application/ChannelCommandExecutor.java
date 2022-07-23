package com.wowtown.wowtownbackend.channel.application;

import com.wowtown.wowtownbackend.channel.application.dto.request.EnterChannelDto;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.channel.domain.ChannelRepository;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelCommandExecutor {
  private final ChannelRepository channelRepository;

  public long enterChannel(EnterChannelDto dto) {
    Channel findChannel =
        channelRepository
            .findChannelById(dto.getChannelId())
            .orElseThrow(() -> new InstanceNotFoundException("알수 없는 채널 입니다."));

    return findChannel.getId();
  }
}
