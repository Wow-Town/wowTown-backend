package com.wowtown.wowtownbackend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wowtown.wowtownbackend.user.application.UserCommandExecutor;
import com.wowtown.wowtownbackend.user.application.dto.request.ChangeUserPWDto;
import com.wowtown.wowtownbackend.user.application.dto.request.CreateUserDto;
import com.wowtown.wowtownbackend.user.application.dto.request.UpdateUserDto;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserCommandController.class)
public class UserCommandControllerTest {

  @Autowired MockMvc mvc;

  @Autowired ObjectMapper mapper;

  @MockBean UserCommandExecutor userCommandExecutor;

  CreateUserDto createUserDto;

  @BeforeEach
  public void init() {
    this.createUserDto = new CreateUserDto("devconf5296@gmail.com", "홍길동", "1234");
  }

  @Test
  public void signUp() throws Exception {
    // when
    Mockito.when(userCommandExecutor.createUser(createUserDto)).thenReturn(1L);

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.post("/users/signUp")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(createUserDto));

    // then
    mvc.perform(builder).andExpect(status().isCreated());
  }

  @Test
  public void updateUser() throws Exception {
    // given
    Long userId = userCommandExecutor.createUser(createUserDto);

    UpdateUserDto updateUserDto = new UpdateUserDto("wmf2fkrh@gmail.com", "김철수");

    // when
    userCommandExecutor.updateUser(userId, updateUserDto);
    Mockito.when(userCommandExecutor.updateUser(userId, updateUserDto)).thenReturn(true);

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.put("/users/{userId}/edit", userId.toString())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(updateUserDto));

    // then
    mvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void updateUserPW() throws Exception {
    // given
    Long userId = userCommandExecutor.createUser(createUserDto);

    ChangeUserPWDto changeUserPWDto = new ChangeUserPWDto("1234", "5678");

    // when
    Mockito.when(userCommandExecutor.updateUserPW(userId, changeUserPWDto)).thenReturn(true);

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.put("/users/{userId}/edit/password", userId.toString())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(changeUserPWDto));

    // then
    mvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void deleteUser() throws Exception {
    // given
    Long userId = userCommandExecutor.createUser(createUserDto);

    // when
    Mockito.when(userCommandExecutor.deleteUser(userId)).thenReturn(true);

    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.delete("/users/{userId}", userId.toString())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsBytes(createUserDto));

    // then
    mvc.perform(builder).andExpect(status().isOk());
  }
}
