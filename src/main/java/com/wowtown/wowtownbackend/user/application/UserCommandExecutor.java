package com.wowtown.wowtownbackend.user.application;

import com.wowtown.wowtownbackend.channel.application.ChannelQueryProcessor;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.user.application.common.PasswordEncoder;
import com.wowtown.wowtownbackend.user.application.common.UserMapper;
import com.wowtown.wowtownbackend.user.application.dto.request.ChangeUserPWDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserChannelDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UpdateUserDto;
import com.wowtown.wowtownbackend.user.domain.User;
import com.wowtown.wowtownbackend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandExecutor {

  private final ChannelQueryProcessor channelQueryProcessor;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  @Transactional
  public long createUser(CreateUserDto dto) {
    String salt = passwordEncoder.getSalt();
    String hashedPW = passwordEncoder.encode(dto.getPassword(), salt);
    User user =
        userRepository.save(userMapper.toUser(dto.getEmail(), dto.getUserName(), hashedPW, salt));
    return user.getId();
  }

  @Transactional
  public boolean updateUser(long userId, UpdateUserDto dto) {
    User findUser =
        userRepository
            .findUserById(userId)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 유저입니다."));
    findUser.updateUser(userMapper.toUser(dto.getEmail(), dto.getUserName()));
    return true;
  }

  @Transactional
  public boolean updateUserPW(long userId, ChangeUserPWDto dto) {
    User findUser =
        userRepository
            .findUserById(userId)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 유저입니다."));

    String salt = findUser.getSalt();
    if (passwordEncoder.encode(dto.getCurrentPW(), salt).equals(findUser.getHashedPW())) {
      String newSalt = passwordEncoder.getSalt();
      String newHashedPW = passwordEncoder.encode(dto.getNewPW(), newSalt);

      findUser.updateUserPW(newHashedPW, newSalt);
    } else {
      throw new InstanceNotFoundException("현재 비밀번호가 일치하지 않습니다.");
    }
    return true;
  }

  @Transactional
  public boolean deleteUser(long userId) {
    User findUser =
        userRepository
            .findUserById(userId)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 유저입니다."));

    userRepository.delete(findUser);
    return true;
  }

  @Transactional
  public boolean addUserChannel(long userId, CreateUserChannelDto dto) {
    User findUser =
        userRepository
            .findUserById(userId)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 유저입니다."));

    Channel findChannel = channelQueryProcessor.getChannelWithId(dto.getChannelId());
    findUser.addUserChannel(findChannel);
    return true;
  }
}
