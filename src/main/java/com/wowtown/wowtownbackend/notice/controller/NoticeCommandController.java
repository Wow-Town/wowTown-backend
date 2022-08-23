package com.wowtown.wowtownbackend.notice.controller;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.common.annotation.UserAvatar;
import com.wowtown.wowtownbackend.notice.application.NoticeCommandExecutor;
import com.wowtown.wowtownbackend.notice.application.dto.request.CreateOrUpdateNoticeDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Controller
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeCommandController {
  private final NoticeCommandExecutor noticeCommandExecutor;

  @ApiOperation(value = "공고 생성", notes = ".")
  @PostMapping
  public ResponseEntity createNotice(
      @Valid @RequestBody CreateOrUpdateNoticeDto dto, @ApiIgnore @UserAvatar Avatar avatar) {
    noticeCommandExecutor.createNotice(dto, avatar);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }

  @ApiOperation(value = "공고 업데이트", notes = "=")
  @PutMapping(value = "/{noticeId}")
  public ResponseEntity updateNotice(
      @PathVariable("noticeId") @Min(1) long noticeId,
      @Valid @RequestBody CreateOrUpdateNoticeDto dto,
      @ApiIgnore @UserAvatar Avatar avatar) {
    noticeCommandExecutor.updateNotice(noticeId, dto, avatar);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  @ApiOperation(value = "공고 삭제", notes = "")
  @ApiImplicitParam(
      name = "noticeId",
      value = "공고 id",
      required = true,
      dataType = "string",
      paramType = "path",
      defaultValue = "None")
  @DeleteMapping(value = "/{noticeId}")
  public ResponseEntity deleteNotice(
      @PathVariable("noticeId") @Min(1) long noticeId, @ApiIgnore @UserAvatar Avatar avatar) {
    noticeCommandExecutor.deleteNotice(noticeId, avatar);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  }

  // todo 공고 스크랩 기능 나중에 추가

  //  @ApiOperation(value = "공고 스크랩", notes = "")
  //  @ApiImplicitParam(
  //      name = "noticeId",
  //      value = "공고 id",
  //      required = true,
  //      dataType = "string",
  //      paramType = "path",
  //      defaultValue = "None")
  //  @DeleteMapping(value = "/{noticeId}/scrap")
  //  public ResponseEntity scrapNotice(
  //      @PathVariable("noticeId") @Min(1) long noticeId,
  //      @ApiIgnore @UserAvatar Avatar avatar) {
  //    noticeCommandExecutor.scrapNotice(noticeId, avatar);
  //    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
  //  }
}
