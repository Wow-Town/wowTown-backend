package com.wowtown.wowtownbackend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wowtown.wowtownbackend.common.redis.RedisService;
import com.wowtown.wowtownbackend.user.application.UserCommandExecutor;
import com.wowtown.wowtownbackend.user.application.common.JwtTokenProvider;
import com.wowtown.wowtownbackend.avatar.application.common.AvatarProvider;
import com.wowtown.wowtownbackend.user.application.common.PasswordEncoder;
import com.wowtown.wowtownbackend.user.application.dto.request.ChangeUserPWDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserChannelDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UpdateUserDto;
import com.wowtown.wowtownbackend.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserCommandController.class)
public class UserCommandControllerTest {

  @Autowired MockMvc mvc;

  @Autowired ObjectMapper mapper;

  @MockBean UserCommandExecutor userCommandExecutor;

  @MockBean PasswordEncoder passwordEncoder;

  @MockBean UserRepository userRepository;

  @MockBean JwtTokenProvider jwtTokenProvider;
  
  @MockBean AvatarProvider avatarProvider;

  @MockBean RedisService redisService;

  @Test
  public void signUp() throws Exception {
    // given
    CreateUserDto createUserDto = new CreateUserDto("devconf5296@gmail.com", "홍길동", "1234");
    // when
    doReturn(1L).when(userCommandExecutor).createUser(any(CreateUserDto.class));

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.post("/signUp")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(createUserDto));

    // then
    mvc.perform(builder).andDo(print()).andExpect(status().isCreated());
  }

  @Test
  public void updateUser() throws Exception {
    // given
    Long userId = 1L;

    UpdateUserDto updateUserDto = new UpdateUserDto("wmf2fkrh@gmail.com", "김철수");

    // when
    doReturn(true).when(userCommandExecutor).updateUser(any(Long.class), any(UpdateUserDto.class));

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.put("/users/{userId}/edit", userId.toString())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(updateUserDto));

    // then
    mvc.perform(builder).andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void updateUserPW() throws Exception {
    // given
    Long userId = 1L;

    ChangeUserPWDto changeUserPWDto = new ChangeUserPWDto("1234", "5678");

    // when
    doReturn(true)
        .when(userCommandExecutor)
        .updateUserPW(any(Long.class), any(ChangeUserPWDto.class));

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.put("/users/{userId}/edit/password", userId.toString())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(changeUserPWDto));

    // then
    mvc.perform(builder).andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void deleteUser() throws Exception {
    // given
    Long userId = 1L;

    // when
    Mockito.when(userCommandExecutor.deleteUser(userId)).thenReturn(true);

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.delete("/users/{userId}", userId.toString())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8");

    // then
    mvc.perform(builder).andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void selectUserChannel() throws Exception {
    // given
    Long userId = 1L;

    CreateUserChannelDto createUserChannelDto = new CreateUserChannelDto(1L);

    // when
    doReturn(true)
        .when(userCommandExecutor)
        .addUserChannel(any(Long.class), any(CreateUserChannelDto.class));

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.post("/users/{userId}/channels", userId.toString())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(createUserChannelDto));

    // then
    mvc.perform(builder).andDo(print()).andExpect(status().isOk());
  }
}
