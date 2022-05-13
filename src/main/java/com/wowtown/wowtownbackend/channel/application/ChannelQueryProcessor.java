package com.wowtown.wowtownbackend.channel.application;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.channel.domain.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelQueryProcessor {
  private final ChannelRepository channelRepository;

  public Channel getChannelWithId(long channelId) {
    Channel findChannel =
        channelRepository
            .findChannelById(channelId)
            .orElseThrow(() -> new IllegalArgumentException("존재하는 채널이 없습니다."));
    return findChannel;
  }
}
