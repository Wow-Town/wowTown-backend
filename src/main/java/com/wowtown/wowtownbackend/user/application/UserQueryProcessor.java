package com.wowtown.wowtownbackend.user.application;

import com.wowtown.wowtownbackend.user.application.common.PasswordEncoder;
import com.wowtown.wowtownbackend.user.application.common.UserMapper;
import com.wowtown.wowtownbackend.user.application.dto.request.LoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UserEmailCheckDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserChannelDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserDto;
import com.wowtown.wowtownbackend.user.domain.User;
import com.wowtown.wowtownbackend.user.domain.UserChannelRepository;
import com.wowtown.wowtownbackend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserQueryProcessor {
  private final UserRepository userRepository;
  private final UserChannelRepository userChannelRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public GetUserDto loginUser(LoginUserDto dto) {
    User findUser =
        userRepository
            .findUserByEmail(dto.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일 입니다."));

    String getSalt = findUser.getSalt();
    String loginPassword = passwordEncoder.encode(dto.getPassword(), getSalt);

    if (loginPassword.equals(findUser.getHashedPW())) {
      // todo 토큰 부여
      return userMapper.toGetUserDto(findUser);
    }
    throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
  }

  public boolean checkUserEmailOverlap(UserEmailCheckDto dto) {
    Optional<User> findUser = userRepository.findUserByEmail(dto.getEmail());
    if (findUser.isPresent()) {
      throw new IllegalArgumentException("해당 이메일이 이미 존재합니다.");
    }
    return true;
  }

  public List<GetUserChannelDto> getUserChannelWithUserId(long userId) {
    List<GetUserChannelDto> userChannels =
        userChannelRepository.findUserChannelByUserId(userId).stream()
            .map(
                userChannel ->
                    userMapper.toUserChannelDto(
                        userChannel.getChannel().getId(),
                        userChannel.getChannel().getChannelName()))
            .collect(Collectors.toList());
    return userChannels;
  }
}
