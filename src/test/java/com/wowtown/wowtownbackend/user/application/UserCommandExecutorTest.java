package com.wowtown.wowtownbackend.user.application;

import com.wowtown.wowtownbackend.user.application.common.UserMapper;
import com.wowtown.wowtownbackend.user.application.dto.request.ChangeUserPWDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UpdateUserDto;
import com.wowtown.wowtownbackend.user.domain.User;
import com.wowtown.wowtownbackend.user.domain.UserRepository;
import com.wowtown.wowtownbackend.user.infra.PasswordEncoderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

  @Test
  void createUser() {
    // given
    CreateUserDto createUserDto = new CreateUserDto("devconf5296@gmail.com", "홍길동", "1234");
    User user = createUserEntity(createUserDto);

    ReflectionTestUtils.setField(user, "id", 1L);

    // when
    doReturn(user).when(userRepository).save(any(User.class));
    Long fakeUserId = userCommandExecutor.createUser(createUserDto);

    // then
    assertThat(fakeUserId).isEqualTo(1L);
  }

  @Test
  void updateUser() {
    // given
    CreateUserDto createUserDto = new CreateUserDto("devconf5296@gmail.com", "홍길동", "1234");
    User user = createUserEntity(createUserDto);

    ReflectionTestUtils.setField(user, "id", 1L);

    Long fakeUserId = 1L;
    UpdateUserDto updateUserDto = new UpdateUserDto("wmf2fkrh@gmail.com", "김철수");

    // when
    doReturn(Optional.of(user)).when(userRepository).findUserById(any(Long.class));
    boolean isUpdated = userCommandExecutor.updateUser(fakeUserId, updateUserDto);

    // then
    assertThat(isUpdated).isEqualTo(true);
    assertThat(user.getEmail()).isEqualTo("wmf2fkrh@gmail.com");
    assertThat(user.getUserName()).isEqualTo("김철수");
  }

  @Test
  void updateUserPW() {
    // given
    CreateUserDto createUserDto = new CreateUserDto("devconf5296@gmail.com", "홍길동", "1234");
    User user = createUserEntity(createUserDto);
    String oldPW = user.getHashedPW();
    String oldSalt = user.getSalt();

    ReflectionTestUtils.setField(user, "id", 1L);

    Long fakeUserId = 1L;
    ChangeUserPWDto changeUserPWDto = new ChangeUserPWDto("1234", "5678");

    // when
    doReturn(Optional.of(user)).when(userRepository).findUserById(any(Long.class));
    boolean isUpdatedPW = userCommandExecutor.updateUserPW(fakeUserId, changeUserPWDto);

    // then
    assertThat(isUpdatedPW).isEqualTo(true);
    assertThat(user.getHashedPW()).isNotEqualTo(oldPW);
    assertThat(user.getSalt()).isNotEqualTo(oldSalt);
  }

  @Test
  void deleteUser() {
    // given
    CreateUserDto createUserDto = new CreateUserDto("devconf5296@gmail.com", "홍길동", "1234");
    User user = createUserEntity(createUserDto);

    ReflectionTestUtils.setField(user, "id", 1L);

    Long fakeUserId = 2L;

    // when
    doReturn(Optional.of(user)).when(userRepository).findUserById(any(Long.class));
    doNothing().when(userRepository).delete(user);
    boolean isDeleted = userCommandExecutor.deleteUser(fakeUserId);

    // then
    assertThat(isDeleted).isEqualTo(true);
  }

  private User createUserEntity(CreateUserDto dto) {
    String salt = passwordEncoder.getSalt();
    String hashedPW = passwordEncoder.encode(dto.getPassword(), salt);
    return userMapper.toUser(dto.getEmail(), dto.getUserName(), hashedPW, salt);
  }
}
