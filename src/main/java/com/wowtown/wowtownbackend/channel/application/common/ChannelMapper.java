package com.wowtown.wowtownbackend.channel.application.common;

import com.wowtown.wowtownbackend.channel.application.dto.request.CreateChannelDto;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

  Channel toChannel(CreateChannelDto dto);
}
