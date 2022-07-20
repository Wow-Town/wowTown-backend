package com.wowtown.wowtownbackend.user.application;

import com.wowtown.wowtownbackend.common.redis.RedisService;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.user.application.common.JwtTokenProvider;
import com.wowtown.wowtownbackend.user.application.common.PasswordEncoder;
import com.wowtown.wowtownbackend.user.application.common.UserMapper;
import com.wowtown.wowtownbackend.user.application.dto.request.LoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UserEmailCheckDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetJwtTokenDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetLoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserChannelDto;
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
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisService redisService;

  public GetJwtTokenDto login(LoginUserDto dto) {
    User findUser =
        userRepository
            .findUserByEmail(dto.getEmail())
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 이메일 입니다."));

    String getSalt = findUser.getSalt();
    String loginPassword = passwordEncoder.encode(dto.getPassword(), getSalt);

    if (loginPassword.equals(findUser.getHashedPW())) {
      String accessToken = jwtTokenProvider.createAccessToken(findUser.getEmail());
      String refreshToken = jwtTokenProvider.createRefreshToken(findUser.getEmail());

      // Redis 인메모리에 리프레시 토큰 저장
      redisService.setValues(refreshToken, findUser.getEmail());

      return userMapper.toGetJwtTokenDto(accessToken, refreshToken);
    }
    throw new InstanceNotFoundException("비밀번호가 일치하지 않습니다.");
  }

  public boolean checkUserEmailOverlap(UserEmailCheckDto dto) {
    Optional<User> findUser = userRepository.findUserByEmail(dto.getEmail());
    if (findUser.isPresent()) {
      throw new InstanceNotFoundException("해당 이메일이 이미 존재합니다.");
    }
    return true;
  }

  public GetLoginUserDto getLoginUser(User user) {
    return userMapper.toGetLoginUserDto(user);
  }

  public List<GetUserChannelDto> getUserChannelWithUserId(long userId) {
    List<GetUserChannelDto> userChannelDtoList =
        userChannelRepository.findUserChannelByUserId(userId).stream()
            .map(
                userChannel ->
                    userMapper.toUserChannelDto(
                        userChannel.getChannel().getId(),
                        userChannel.getChannel().getChannelName()))
            .collect(Collectors.toList());
    return userChannelDtoList;
  }
}
