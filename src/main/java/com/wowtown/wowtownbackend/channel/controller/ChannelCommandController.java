package com.wowtown.wowtownbackend.channel.controller;

import com.wowtown.wowtownbackend.channel.application.ChannelCommandExecutor;
import com.wowtown.wowtownbackend.channel.application.dto.request.EnterChannelDto;
import com.wowtown.wowtownbackend.channel.application.dto.response.GetChannelDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelCommandController {
  private final ChannelCommandExecutor channelCommandExecutor;

  @ApiOperation(value = "채널 입장하기", notes = "")
  @PostMapping
  public ResponseEntity<List<GetChannelDto>> enterChannel(
      @RequestBody EnterChannelDto dto, HttpServletResponse response) {
    long channelId = channelCommandExecutor.enterChannel(dto);
    Cookie cookie = new Cookie("channelId", String.valueOf(channelId));
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setDomain("wowtown.co.kr");
    response.addCookie(cookie);

    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
