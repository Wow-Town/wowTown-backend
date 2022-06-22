package com.wowtown.wowtownbackend.channel.application.common;

import com.wowtown.wowtownbackend.channel.application.dto.request.CreateChannelDto;
import com.wowtown.wowtownbackend.channel.application.dto.response.GetChannelDto;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

  Channel toChannel(CreateChannelDto dto);

  @Mapping(source = "id", target = "channelId")
  GetChannelDto toGetChannelDto(Channel channel);
}
