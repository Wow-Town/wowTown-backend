package com.wowtown.wowtownbackend.channel.controller;

import com.wowtown.wowtownbackend.channel.application.ChannelCommandExecutor;
import com.wowtown.wowtownbackend.channel.application.dto.request.EnterChannelDto;
import com.wowtown.wowtownbackend.channel.application.dto.response.GetChannelDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
      @RequestBody EnterChannelDto dto, HttpServletRequest request, HttpServletResponse response) {

    long channelId = channelCommandExecutor.enterChannel(dto);

    String origin = request.getHeader("Origin");

    String domain =
        (origin == null
                || origin.equals("http://localhost:3000")
                || origin.equals("https://localhost:3000")
                || origin.equals("https://localhost:443"))
            ? "localhost"
            : origin.substring(8);

    ResponseCookie cookie =
        ResponseCookie.from("channelId", String.valueOf(channelId))
            .path("/")
            .httpOnly(true)
            .domain(domain)
            .build();
    response.addHeader("Set-Cookie", cookie.toString());

    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }
}
