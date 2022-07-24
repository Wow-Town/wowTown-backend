package com.wowtown.wowtownbackend.channel.application;

import com.wowtown.wowtownbackend.channel.application.common.ChannelMapper;
import com.wowtown.wowtownbackend.channel.application.dto.response.GetChannelDto;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.channel.domain.ChannelRepository;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelQueryProcessor {
  private final ChannelRepository channelRepository;
  private final ChannelMapper channelMapper;

  public List<GetChannelDto> getAllChannelList() {
    // 언리얼 매치메이커에서 채널을 생성,수정,삭제를 해야하는데 아직 구현 안되서 임시로 채널 만듬. 나중에 삭제 예정
    //Channel channel = new Channel("홍문관", 100);
    //channelRepository.save(channel);

    return channelRepository.findAll().stream()
        .map(channelMapper::toGetChannelDto)
        .collect(Collectors.toList());
  }

  public Channel getChannelWithId(long channelId) {
    Channel findChannel =
        channelRepository
            .findChannelById(channelId)
            .orElseThrow(() -> new InstanceNotFoundException("알수 없는 채널 입니다."));
    return findChannel;
  }
}
