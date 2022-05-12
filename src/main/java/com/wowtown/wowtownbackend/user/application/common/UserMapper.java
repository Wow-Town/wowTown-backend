package com.wowtown.wowtownbackend.user.application.common;

import com.wowtown.wowtownbackend.user.application.dto.response.GetUserChannelDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserDto;
import com.wowtown.wowtownbackend.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

  @Mapping(source = "id", target = "userId")
  GetUserDto toGetUserDto(User user);

  GetUserChannelDto toUserChannelDto(Long channelId, String channelName);
}
