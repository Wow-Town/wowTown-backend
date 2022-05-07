package com.wowtown.wowtownbackend.user.application.common;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserChannelDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserDto;
import com.wowtown.wowtownbackend.user.domain.User;
import com.wowtown.wowtownbackend.user.domain.UserChannel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  default User toUser(String email, String userName, String hashedPW, String salt) {
    if (email == null && userName == null && hashedPW == null && salt == null) {
      return null;
    }

    User user = new User(email, userName, hashedPW, salt);

    return user;
  }

  default User toUser(String email, String userName) {
    if (email == null && userName == null) {
      return null;
    }

    User user = new User(email, userName);

    return user;
  }

  GetUserDto toGetUserDto(User user);

  UserChannel toUserChannel(User user, Channel channel);

  GetUserChannelDto toUserChannelDto(Long channelId, String channelName);
}
