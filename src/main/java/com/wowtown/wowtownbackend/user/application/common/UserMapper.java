package com.wowtown.wowtownbackend.user.application.common;

import com.wowtown.wowtownbackend.user.application.dto.response.GetJwtTokenDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetLoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserChannelDto;
import com.wowtown.wowtownbackend.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

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

  GetJwtTokenDto toGetJwtTokenDto(String accessToken, String refreshToken);

  @Mapping(source = "id", target = "userId")
  GetLoginUserDto toGetLoginUserDto(User user);


  GetUserChannelDto toUserChannelDto(Long channelId, String channelName);
}
