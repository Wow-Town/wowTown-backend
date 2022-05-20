package com.wowtown.wowtownbackend.channel.domain;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository {
  Channel save(Channel toSave);

  List<Channel> findAll();

  Optional<Channel> findChannelById(Long channelId);
}
