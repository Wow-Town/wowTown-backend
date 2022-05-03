package com.wowtown.wowtownbackend.channel.domain;

import java.util.Optional;

public interface ChannelRepository {
  Optional<Channel> findChannelById(Long channelId);
}
