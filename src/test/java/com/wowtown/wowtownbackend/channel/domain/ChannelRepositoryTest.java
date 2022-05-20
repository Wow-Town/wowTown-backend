package com.wowtown.wowtownbackend.channel.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ChannelRepositoryTest {

  @Autowired private ChannelRepository channelRepository;

  Channel defaultChannel1;
  Channel defaultChannel2;
  Long channelId1;
  Long channelId2;

  @BeforeEach
  @Transactional
  void init() {
    Channel channel1 = new Channel("channel1", 100);
    Channel channel2 = new Channel("channel2", 200);
    this.defaultChannel1 = channelRepository.save(channel1);
    this.defaultChannel2 = channelRepository.save(channel2);
    this.channelId1 = defaultChannel1.getId();
    this.channelId2 = defaultChannel2.getId();
  }

  @Test
  void findAll() {
    // when
    List<Channel> findChannelList = channelRepository.findAll();
    // then
    assertThat(findChannelList.get(0)).isEqualTo(defaultChannel1);
    assertThat(findChannelList.get(1)).isEqualTo(defaultChannel2);
  }

  @Test
  void findChannelById() {
    // when
    Channel findChannel = channelRepository.findChannelById(channelId2).get();
    // then
    assertThat(findChannel).isEqualTo(defaultChannel2);
  }
}
