package com.wowtown.wowtownbackend.channel.application;

import com.wowtown.wowtownbackend.channel.application.common.ChannelMapper;
import com.wowtown.wowtownbackend.channel.application.dto.response.GetChannelDto;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.channel.domain.ChannelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class ChannelQueryProcessorTest {
  @InjectMocks private ChannelQueryProcessor channelQueryProcessor;

  @Spy private ChannelRepository channelRepository;

  @Spy private ChannelMapper channelMapper;

  private Channel savedChannel;

  @BeforeEach
  void init() {
    // create channel
    savedChannel = new Channel("channel1", 100);
    ReflectionTestUtils.setField(savedChannel, "id", 1L);
  }

  @Test
  void getAllChannelList() {
    // given
    List<Channel> getChannelList = new ArrayList<>();
    getChannelList.add(savedChannel);
    GetChannelDto getChannelDto = new GetChannelDto("channel1", 100, 0);

    // when
    doReturn(getChannelList).when(channelRepository).findAll();
    doReturn(getChannelDto).when(channelMapper).toGetChannelDto(getChannelList.get(0));

    List<GetChannelDto> response = channelQueryProcessor.getAllChannelList();

    // then
    assertThat(response.get(0).getChannelName()).isEqualTo(getChannelDto.getChannelName());
    assertThat(response.get(0).getMaxJoinNum()).isEqualTo(getChannelDto.getMaxJoinNum());
    assertThat(response.get(0).getCurrentJoinNum()).isEqualTo(getChannelDto.getCurrentJoinNum());
  }
}
