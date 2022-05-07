package com.wowtown.wowtownbackend.user.application;

import com.wowtown.wowtownbackend.user.application.common.UserMapper;
import com.wowtown.wowtownbackend.user.domain.UserRepository;
import com.wowtown.wowtownbackend.user.infra.PasswordEncoderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserQueryProcessorTest {

  @InjectMocks private UserCommandExecutor userCommandExecutor;

  @Spy private UserRepository userRepository;

  @Spy private PasswordEncoderImpl passwordEncoder;

  @Spy private UserMapper userMapper;

  @Test
  void loginUser() {}

  @Test
  void checkUserEmailOverlap() {}

  @Test
  void getUserChannelWithUserId() {}
}
