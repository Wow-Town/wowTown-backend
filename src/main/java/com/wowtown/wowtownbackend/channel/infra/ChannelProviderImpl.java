package com.wowtown.wowtownbackend.channel.infra;

import com.wowtown.wowtownbackend.channel.application.common.ChannelProvider;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.channel.domain.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelProviderImpl implements ChannelProvider {
  private final ChannelRepository channelRepository;

  @Override
  public Channel getChannel(long channelId) {
    Channel findChannel =
        channelRepository
            .findChannelById(channelId)
            .orElseThrow(() -> new IllegalArgumentException("채널이 존재하지 않습니다."));
    return findChannel;
  }
}
