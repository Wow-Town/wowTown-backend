package com.wowtown.wowtownbackend.channel.application.common;

import com.wowtown.wowtownbackend.channel.domain.Channel;

public interface ChannelProvider {
  public Channel getChannel(long channelId);
}
