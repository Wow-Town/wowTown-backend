package com.wowtown.wowtownbackend.channel.domain;

import java.util.Optional;

public interface ChannelRepository {
  Channel save(Channel toSave);

  Optional<Channel> findChannelById(Long channelId);
}
