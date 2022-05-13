package com.wowtown.wowtownbackend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wowtown.wowtownbackend.user.application.UserQueryProcessor;
import com.wowtown.wowtownbackend.user.application.common.PasswordEncoder;
import com.wowtown.wowtownbackend.user.application.dto.request.LoginUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UserEmailCheckDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserChannelDto;
import com.wowtown.wowtownbackend.user.application.dto.response.GetUserDto;
import com.wowtown.wowtownbackend.user.domain.User;
import com.wowtown.wowtownbackend.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserQueryController.class)
class UserQueryControllerTest {
  @Autowired MockMvc mvc;

  @Autowired ObjectMapper mapper;

  @MockBean PasswordEncoder passwordEncoder;

  @MockBean UserQueryProcessor userQueryProcessor;

  @MockBean UserRepository userRepository;

  @BeforeEach
  public void init() {
    String hashedPW = passwordEncoder.encode("1234", "abcd");
    User user = new User("devconf5296@gmail.com", "홍길동", hashedPW, "abcd");
    userRepository.save(user);
  }

  @Test
  void login() throws Exception {
    // given
    LoginUserDto loginUserDto = new LoginUserDto("devconf5296@gmail.com", "1234");
    GetUserDto getUserDto = new GetUserDto(0L, "devconf5296@gmail.com", "홍길동");
    // when
    Mockito.when(userQueryProcessor.loginUser(loginUserDto)).thenReturn(getUserDto);

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.post("/users/login")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(loginUserDto));
    // then
    mvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  void checkUserEmailOverlap() throws Exception {
    // given
    UserEmailCheckDto userEmailCheckDto = new UserEmailCheckDto("devconf5296@gmail.com");
    // when
    Mockito.when(userQueryProcessor.checkUserEmailOverlap(userEmailCheckDto)).thenReturn(true);

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.post("/users/signUp/check")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(userEmailCheckDto));
    // then
    mvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  void getUserChannel() throws Exception {
    // given
    Long userId = 1L;
    List<GetUserChannelDto> getUserChannelDtoList = new ArrayList<>();
    GetUserChannelDto getUserChannelDto = new GetUserChannelDto(1L, "channel1");
    getUserChannelDtoList.add(getUserChannelDto);

    // when
    Mockito.when(userQueryProcessor.getUserChannelWithUserId(userId))
        .thenReturn(getUserChannelDtoList);

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.get("/users/{userId}/channels", userId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8");
    // then
    mvc.perform(builder).andExpect(status().isOk());
  }
}
