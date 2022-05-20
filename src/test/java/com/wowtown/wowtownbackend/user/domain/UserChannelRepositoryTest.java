package com.wowtown.wowtownbackend.user.domain;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.channel.domain.ChannelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserChannelRepositoryTest {

  @Autowired private UserChannelRepository userChannelRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private ChannelRepository channelRepository;

  User defaultUser;
  Channel defaultChannel;
  Long userId;

  @BeforeEach
  @Transactional
  void init() {
    User user = new User("devconf@gamil.com", "홍길동", "1234", "abcd");
    Channel channel = new Channel("channel1", 100);
    this.defaultUser = userRepository.save(user);
    this.defaultChannel = channelRepository.save(channel);
    this.defaultUser.addUserChannel(channel);
    this.userId = this.defaultUser.getId();
  }

  @AfterEach
  @Transactional
  void clear() {
    userRepository.deleteAll();
  }

  @Test
  void findUserChannelByUserId() {
    // when
    List<UserChannel> userChannelList =
        new ArrayList<>(userChannelRepository.findUserChannelByUserId(userId));
    // then
    assertThat(userChannelList.get(0).getUser()).isEqualTo(this.defaultUser);
    assertThat(userChannelList.get(0).getChannel()).isEqualTo(this.defaultChannel);
  }
}
