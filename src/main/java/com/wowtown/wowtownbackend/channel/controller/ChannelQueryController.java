package com.wowtown.wowtownbackend.channel.controller;

import com.wowtown.wowtownbackend.channel.application.ChannelQueryProcessor;
import com.wowtown.wowtownbackend.channel.application.dto.response.GetChannelDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelQueryController {
  private final ChannelQueryProcessor channelQueryProcessor;

  @ApiOperation(value = "모든 채널 가져오기", notes = "")
  @GetMapping
  public ResponseEntity<List<GetChannelDto>> getAllChannel() {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(channelQueryProcessor.getAllChannelList());
  }
}
