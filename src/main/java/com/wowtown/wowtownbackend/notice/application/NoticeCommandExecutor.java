package com.wowtown.wowtownbackend.notice.application;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.chatroom.application.ChatRoomCommandExecutor;
import com.wowtown.wowtownbackend.common.codeGenerator.CodeGenerator;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.notice.application.common.NoticeMapper;
import com.wowtown.wowtownbackend.notice.application.dto.request.CreateOrUpdateNoticeDto;
import com.wowtown.wowtownbackend.notice.application.dto.request.NoticePasswordDto;
import com.wowtown.wowtownbackend.notice.domain.Notice;
import com.wowtown.wowtownbackend.notice.domain.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticeCommandExecutor {
  private final NoticeRepository noticeRepository;
  private final NoticeMapper noticeMapper;
  private final CodeGenerator codeGenerator;
  private final ChatRoomCommandExecutor chatRoomCommandExecutor;

  @Transactional
  public long createNotice(CreateOrUpdateNoticeDto dto, Avatar avatar) { // 공고등록
    // 같은 공고 제목은 체크하지 않습니다. 제목은 unique한 것이 아니기 때문입니다.
    Notice notice =
        noticeMapper.toNotice(dto.getSubject(), dto.getDescription(), dto.getInterests());

    UUID chatRoomUUID = chatRoomCommandExecutor.createNoticeChatroom(dto.getSubject(), avatar);

    notice.addChatRoomUUID(chatRoomUUID);

    // 공고 주인 설정.
    notice.addOwner(avatar);
    // 공고 비밀번호 설정, 오픈채팅방에 입장하려면 방장으로부터 비밀번호를 받고 일치해야 입장 가능하다.
    notice.setDefaultPW(codeGenerator.generateRandomCode());

    noticeRepository.save(notice);

    return notice.getId();
  }

  @Transactional
  public boolean updateNotice(long noticeId, CreateOrUpdateNoticeDto dto, Avatar avatar) {
    Notice findNotice =
        noticeRepository
            .findById(noticeId)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 공고입니다"));

    if (findNotice.isSameOwner(avatar)) {
      findNotice.updateNotice(
          noticeMapper.toNotice(dto.getSubject(), dto.getDescription(), dto.getInterests()));
      return true;
    }
    throw new InstanceNotFoundException("게시물 소유자가 아닙니다.");
  }

  @Transactional
  public boolean deleteNotice(long noticeId, Avatar avatar) {
    Notice findNotice =
        noticeRepository
            .findById(noticeId)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 공고입니다"));

    if (findNotice.isSameOwner(avatar)) {
      noticeRepository.delete(findNotice);
      return true;
    }
    throw new InstanceNotFoundException("게시물 소유자가 아닙니다.");
  }

  public boolean checkPassword(long noticeId, NoticePasswordDto dto, Avatar avatar) {
    Notice findNotice =
        noticeRepository
            .findById(noticeId)
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 공고입니다."));

    if (findNotice.getRandomPW().equals(dto.getPassword())) {
      chatRoomCommandExecutor.joinNoticeChatroom(
          findNotice.getChatRoomUUID(), findNotice.getSubject(), avatar);
      return true;
    }
    throw new InstanceNotFoundException("비밀번호가 일치 하지 않습니다.");
  }

  // todo 공고 스크랩 기능 나중에 추가

  //  @Transactional
  //  public void scrapNotice(long noticeId, Avatar avatar) {
  //    Notice findNotice =
  //        noticeRepository
  //            .findById(noticeId)
  //            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 스터디 그룹입니다"));
  //
  //    avatarCommandExecutor.addAvatarScrapNotice(findNotice, avatar);
  //  }
}
