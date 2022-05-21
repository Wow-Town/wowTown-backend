package com.wowtown.wowtownbackend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wowtown.wowtownbackend.common.argumentresolver.LoginUserArgumentResolver;
import com.wowtown.wowtownbackend.common.redis.RedisService;
import com.wowtown.wowtownbackend.user.application.UserQueryProcessor;
import com.wowtown.wowtownbackend.user.application.common.JwtTokenProvider;
import com.wowtown.wowtownbackend.user.application.common.PasswordEncoder;
import com.wowtown.wowtownbackend.user.application.dto.request.LoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UserEmailCheckDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetJwtTokenDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetLoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserChannelDto;
import com.wowtown.wowtownbackend.user.domain.User;
import com.wowtown.wowtownbackend.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserQueryController.class)
class UserQueryControllerTest {
  @Autowired MockMvc mvc;

  @Autowired ObjectMapper mapper;

  @MockBean PasswordEncoder passwordEncoder;

  @MockBean UserQueryProcessor userQueryProcessor;

  @MockBean JwtTokenProvider jwtTokenProvider;

  @MockBean RedisService redisService;

  @MockBean UserRepository userRepository;

  @MockBean LoginUserArgumentResolver loginUserArgumentResolver;

  @Test
  void login() throws Exception {
    // given
    LoginUserDto loginUserDto = new LoginUserDto("devconf5296@gmail.com", "1234");
    GetJwtTokenDto getJwtTokenDto = new GetJwtTokenDto("accessToken", "refreshToken");

    // when
    doReturn(getJwtTokenDto).when(userQueryProcessor).login(any(LoginUserDto.class));

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.post("/login")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(loginUserDto));
    // then
    mvc.perform(builder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(cookie().exists("api-key"));
  }

  @Test
  void checkUserEmailOverlap() throws Exception {
    // given
    UserEmailCheckDto userEmailCheckDto = new UserEmailCheckDto("devconf5296@gmail.com");
    // when
    doReturn(true).when(userQueryProcessor).checkUserEmailOverlap(any(UserEmailCheckDto.class));

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.post("/signUp/check")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(userEmailCheckDto));
    // then
    mvc.perform(builder).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void getLoginUser() throws Exception {
    // given
    GetLoginUserDto getLoginUserDto = new GetLoginUserDto(1L, "devconf5296@gmail.com", "홍길동");
    User user = new User("devconf5296@gmail.com", "홍길동");
    ReflectionTestUtils.setField(user, "id", 1L);

    // when
    doReturn(true).when(loginUserArgumentResolver).supportsParameter(any());
    doReturn(user).when(jwtTokenProvider).getAuthenticatedUser(any());
    doReturn(user).when(loginUserArgumentResolver).resolveArgument(any(), any(), any(), any());

    doReturn(getLoginUserDto).when(userQueryProcessor).getLoginUser(any(User.class));

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.get("/users/self")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8");
    // then
    mvc.perform(builder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(1L))
        .andExpect(jsonPath("$.email").value("devconf5296@gmail.com"))
        .andExpect(jsonPath("$.userName").value("홍길동"));
    ;
  }

  @Test
  void getUserChannel() throws Exception {
    // given
    Long userId = 1L;
    List<GetUserChannelDto> getUserChannelDtoList = new ArrayList<>();
    GetUserChannelDto getUserChannelDto = new GetUserChannelDto(1L, "channel1");
    getUserChannelDtoList.add(getUserChannelDto);

    // when
    doReturn(getUserChannelDtoList)
        .when(userQueryProcessor)
        .getUserChannelWithUserId(any(Long.class));

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.get("/users/{userId}/channels", userId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8");
    // then
    mvc.perform(builder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].channelId").value(1))
        .andExpect(jsonPath("$[0].channelName").value("channel1"));
  }
}
