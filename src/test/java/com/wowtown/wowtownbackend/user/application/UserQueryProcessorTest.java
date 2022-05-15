package com.wowtown.wowtownbackend.user.application;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.user.application.common.UserMapper;
import com.wowtown.wowtownbackend.user.application.dto.request.LoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UserEmailCheckDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserChannelDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserDto;
import com.wowtown.wowtownbackend.user.domain.User;
import com.wowtown.wowtownbackend.user.domain.UserChannel;
import com.wowtown.wowtownbackend.user.domain.UserChannelRepository;
import com.wowtown.wowtownbackend.user.domain.UserRepository;
import com.wowtown.wowtownbackend.user.infra.PasswordEncoderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserQueryProcessorTest {

  @InjectMocks private UserQueryProcessor userQueryProcessor;

  @Spy private UserRepository userRepository;
  @Spy private UserChannelRepository userChannelRepository;

  @Spy private PasswordEncoderImpl passwordEncoder;

  @Spy private UserMapper userMapper;

  private User savedUser;

  private UserChannel savedUserChannel;

  @BeforeEach
  void init() {
    // create user
    String salt = passwordEncoder.getSalt();
    String hashedPW = passwordEncoder.encode("1234", salt);
    savedUser = new User("devconf5296@gmail.com", "홍길동", hashedPW, salt);
    ReflectionTestUtils.setField(savedUser, "id", 1L);

    // create channel
    Channel savedChannel = new Channel("channel1");
    ReflectionTestUtils.setField(savedChannel, "id", 1L);

    // create userChannel
    savedUserChannel = new UserChannel();
    savedUserChannel.setUser(savedUser);
    savedUserChannel.setChannel(savedChannel);
    ReflectionTestUtils.setField(savedUserChannel, "id", 1L);
  }

  @Test
  void loginUser() {
    // given
    LoginUserDto loginUserDto = new LoginUserDto("devconf5296@gmail.com", "1234");
    GetUserDto getUserDto = new GetUserDto(1L, "devconf5296@gmail.com", "홍길동");

    // when
    doReturn(Optional.of(savedUser)).when(userRepository).findUserByEmail(any(String.class));
    doReturn(getUserDto).when(userMapper).toGetUserDto(any(User.class));
    GetUserDto responseDto = userQueryProcessor.loginUser(loginUserDto);

    // then
    assertThat(responseDto.getUserId()).isEqualTo(savedUser.getId());
    assertThat(responseDto.getUserName()).isEqualTo(savedUser.getUserName());
    assertThat(responseDto.getEmail()).isEqualTo(savedUser.getEmail());
  }

  @Test
  void checkUserEmailOverlap() {
    // given
    UserEmailCheckDto userEmailCheckDto = new UserEmailCheckDto("devconf5296@gmail.com");

    // when
    doReturn(Optional.of(savedUser)).when(userRepository).findUserByEmail(any(String.class));
    Throwable exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              userQueryProcessor.checkUserEmailOverlap(userEmailCheckDto);
            });

    // then
    assertThat("해당 이메일이 이미 존재합니다.").isEqualTo(exception.getMessage());
  }

  @Test
  void getUserChannelWithUserId() {
    // given
    Long userId = savedUser.getId();
    List<UserChannel> getUserChannelList = new ArrayList<>();
    getUserChannelList.add(savedUserChannel);
    GetUserChannelDto getUserChannelDto = new GetUserChannelDto(1L, "channel1");

    // when
    doReturn(getUserChannelList)
        .when(userChannelRepository)
        .findUserChannelByUserId(any(Long.class));
    doReturn(getUserChannelDto)
        .when(userMapper)
        .toUserChannelDto(
            getUserChannelList.get(0).getChannel().getId(),
            getUserChannelList.get(0).getChannel().getChannelName());

    List<GetUserChannelDto> response = userQueryProcessor.getUserChannelWithUserId(userId);

    // then
    assertThat(response.get(0).getChannelId()).isEqualTo(getUserChannelDto.getChannelId());
    assertThat(response.get(0).getChannelName()).isEqualTo(getUserChannelDto.getChannelName());
  }
}
