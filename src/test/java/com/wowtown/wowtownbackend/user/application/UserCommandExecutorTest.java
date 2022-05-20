package com.wowtown.wowtownbackend.user.application;

import com.wowtown.wowtownbackend.channel.application.ChannelQueryProcessor;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.user.application.common.UserMapper;
import com.wowtown.wowtownbackend.user.application.dto.request.ChangeUserPWDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserChannelDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UpdateUserDto;
import com.wowtown.wowtownbackend.user.domain.User;
import com.wowtown.wowtownbackend.user.domain.UserRepository;
import com.wowtown.wowtownbackend.user.infra.PasswordEncoderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserCommandExecutorTest {

  @InjectMocks private UserCommandExecutor userCommandExecutor;

  @Spy private UserRepository userRepository;

  @Spy private PasswordEncoderImpl passwordEncoder;

  @Spy private UserMapper userMapper;

  @Mock private ChannelQueryProcessor channelQueryProcessor;

  private User savedUser;

  private Channel savedChannel;

  @BeforeEach
  void init() {
    // create user
    String salt = passwordEncoder.getSalt();
    String hashedPW = passwordEncoder.encode("1234", salt);
    savedUser = new User("devconf5296@gmail.com", "홍길동", hashedPW, salt);
    ReflectionTestUtils.setField(savedUser, "id", 1L);

    // create channel
    savedChannel = new Channel("channel1", 100);
    ReflectionTestUtils.setField(savedChannel, "id", 1L);
  }

  @Test
  void createUser() {
    // given
    CreateUserDto createUserDto = new CreateUserDto("devconf5296@gmail.com", "홍길동", "1234");

    // when
    doReturn(savedUser).when(userRepository).save(any(User.class));
    Long fakeUserId = userCommandExecutor.createUser(createUserDto);

    // then
    assertThat(fakeUserId).isEqualTo(1L);
  }

  @Test
  void updateUser() {
    // given
    Long fakeUserId = 1L;
    UpdateUserDto updateUserDto = new UpdateUserDto("wmf2fkrh@gmail.com", "김철수");

    // when
    doReturn(Optional.of(savedUser)).when(userRepository).findUserById(any(Long.class));
    boolean isUpdated = userCommandExecutor.updateUser(fakeUserId, updateUserDto);

    // then
    assertThat(isUpdated).isEqualTo(true);
    assertThat(savedUser.getEmail()).isEqualTo("wmf2fkrh@gmail.com");
    assertThat(savedUser.getUserName()).isEqualTo("김철수");
  }

  @Test
  void updateUserPW() {
    // given
    Long fakeUserId = 1L;
    String oldPW = savedUser.getHashedPW();
    String oldSalt = savedUser.getSalt();
    ChangeUserPWDto changeUserPWDto = new ChangeUserPWDto("1234", "5678");

    // when
    doReturn(Optional.of(savedUser)).when(userRepository).findUserById(any(Long.class));
    boolean isUpdatedPW = userCommandExecutor.updateUserPW(fakeUserId, changeUserPWDto);

    // then
    assertThat(isUpdatedPW).isEqualTo(true);
    assertThat(savedUser.getHashedPW()).isNotEqualTo(oldPW);
    assertThat(savedUser.getSalt()).isNotEqualTo(oldSalt);
  }

  @Test
  void deleteUser() {
    // given
    Long fakeUserId = 1L;

    // when
    doReturn(Optional.of(savedUser)).when(userRepository).findUserById(any(Long.class));
    doNothing().when(userRepository).delete(savedUser);
    boolean isDeleted = userCommandExecutor.deleteUser(fakeUserId);

    // then
    assertThat(isDeleted).isEqualTo(true);
  }

  @Test
  void addUserChannel() {
    // given
    CreateUserChannelDto createUserChannelDto = new CreateUserChannelDto(1L);

    Long fakeUserId = 1L;
    Long fakeChannelId = 1L;

    // when
    doReturn(Optional.of(savedUser)).when(userRepository).findUserById(any(Long.class));
    doReturn(savedChannel).when(channelQueryProcessor).getChannelWithId(fakeChannelId);
    userCommandExecutor.addUserChannel(fakeUserId, createUserChannelDto);

    // then
    assertThat(savedUser.getUserChannelList().get(0).getUser()).isEqualTo(savedUser);
    assertThat(savedUser.getUserChannelList().get(0).getChannel()).isEqualTo(savedChannel);
  }
}
