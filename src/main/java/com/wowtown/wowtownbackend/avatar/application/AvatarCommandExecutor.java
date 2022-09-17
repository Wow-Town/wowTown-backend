package com.wowtown.wowtownbackend.avatar.application;

import com.wowtown.wowtownbackend.avatar.application.common.AvatarMapper;
import com.wowtown.wowtownbackend.avatar.application.dto.request.CreateOrUpdateAvatarDto;
import com.wowtown.wowtownbackend.avatar.application.dto.request.FriendAvatarDto;
import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import com.wowtown.wowtownbackend.avatar.domain.AvatarFriend;
import com.wowtown.wowtownbackend.avatar.domain.AvatarFriendStatus;
import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
import com.wowtown.wowtownbackend.channel.domain.Channel;
import com.wowtown.wowtownbackend.common.codeGenerator.CodeGenerator;
import com.wowtown.wowtownbackend.common.email.InviteEmailSender;
import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
import com.wowtown.wowtownbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvatarCommandExecutor {

  private final AvatarRepository avatarRepository;
  private final AvatarMapper avatarMapper;
  private final InviteEmailSender inviteEmailSender;
  private final CodeGenerator codeGenerator;

  @Transactional
  public long createAvatar(CreateOrUpdateAvatarDto dto, Channel channel, User user) {
    // 아바타에 닉네임이 있는지 존재 확인 ->
    Optional<Avatar> findAvatar =
        avatarRepository.findAvatarWithChannelIdAndUserIdAndNickName(
            channel.getId(), user.getId(), dto.getNickName());
    if (findAvatar.isPresent()) {
      Avatar avatar = findAvatar.get();
      if (avatar.getUser().equals(user)) {
        throw new InstanceNotFoundException("해당 채널에 아바타가 이미 존재합니다.");
      }
      if (avatar.getNickName().equals(dto.getNickName())) {
        throw new InstanceNotFoundException("해당 닉네임이 이미 존재합니다.");
      }
    }
    Avatar avatar = avatarRepository.save(avatarMapper.toAvatar(dto, user, channel));
    return avatar.getId();
  }
  // 채널 아이디, 유저 가지고 디비 조회 있을 경우 아바타존재 아니면 새로만들기
  // dto의 닉네임을 가지고 조회중복이 되면 빠꾸
  @Transactional
  public boolean updateAvatar(CreateOrUpdateAvatarDto dto, Channel channel, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channel.getId(), user.getId())
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    Avatar updatePayload = avatarMapper.toUpdateAvatar(dto);
    findAvatar.updateAvatar(updatePayload);
    return true;
  }

  @Transactional
  public boolean deleteAvatar(Channel channel, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channel.getId(), user.getId())
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    avatarRepository.delete(findAvatar);
    return true;
  }

  @Transactional
  public void addFriend(FriendAvatarDto dto, Avatar avatar) {
    if (avatar.getId().equals(dto.getFriendAvatarId())) {
      throw new IllegalStateException("자신한테는 친구 신청을 할 수 없습니다.");
    }

    Avatar friend =
        avatarRepository
            .findById(dto.getFriendAvatarId())
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 아바타 입니다."));

    // 상대방 친구 목록에 자신이 있으면 이미 친구이거나 친구신청함.
    // 상대방 친구 목록에 자신이 있는데 자신의 친구목록에 상대방이 없는경우는 있을수 없음.(동기화 해줘야함) -> 아마도 친구 삭제 로직을 구현한다면 거기서 작성해야 할듯
    // 상대방 친구 목록에 자신이 없다면 상대방 친구 목록에 자신 넣어줌
    if (friend.checkFriendInAvatarFriendSet(avatar) != AvatarFriendStatus.BLANK) {
      throw new IllegalStateException("이미 친구이거나 친구신청을 하였습니다.");
    }
    // 우선 상대방 친구 목록에 자신을 넣어준다.  나중에 상대방이 친구 수락을 하면 자신의 친구 목록에 추가됨
    AvatarFriend avatarFriend = new AvatarFriend(friend, avatar);
    friend.addAvatarFriend(avatarFriend);
  }

  @Transactional
  public void approveFriendRequest(FriendAvatarDto dto, Avatar avatar) {
    if (avatar.getId().equals(dto.getFriendAvatarId())) {
      throw new IllegalStateException("자신한테는 친구 신청을 할 수 없습니다.");
    }

    Avatar friend =
        avatarRepository
            .findById(dto.getFriendAvatarId())
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 아바타 입니다."));

    // 자신의 친구 목록에 상대방이 있는지 확인후 있으면 친구수락
    switch (avatar.checkFriendInAvatarFriendSet(friend)) {
      case BLANK:
        throw new InstanceNotFoundException("친구 신청한 아바타가 아닙니다.");
      case APPROVED:
        throw new InstanceNotFoundException("이미 친구 입니다.");
      case REQUESTED:
        // 본인 친구 요청 수락
        avatar.approveAvatarFriend(friend);
        // 상대방 친구 목록에 본인이 친구 신청한적이 있는지 확인
        if (friend.checkFriendInAvatarFriendSet(avatar) == AvatarFriendStatus.REQUESTED) {
          friend.approveAvatarFriend(avatar);
        } else {
          // 상대방으로부터 친구신청 요청을 받았지만 상대방 친구 목록에 본인이 없을경우 상대방 친구 목록에 본인 추가
          AvatarFriend avatarFriend = new AvatarFriend(friend, avatar);
          avatarFriend.approveFriendRequest();
          friend.addAvatarFriend(avatarFriend);
        }
    }
  }

  @Transactional
  public void rejectFriendRequest(FriendAvatarDto dto, Avatar avatar) {
    if (avatar.getId().equals(dto.getFriendAvatarId())) {
      throw new IllegalStateException("자신한테는 친구 신청 거절을 할 수 없습니다.");
    }

    Avatar friend =
        avatarRepository
            .findById(dto.getFriendAvatarId())
            .orElseThrow(() -> new InstanceNotFoundException("존재하지 않는 아바타 입니다."));

    // 자신의 친구 목록에 상대방이 있는지 확인후 있으면 친구거절
    switch (avatar.checkFriendInAvatarFriendSet(friend)) {
      case BLANK:
        throw new InstanceNotFoundException("친구 신청한 아바타가 아닙니다.");
      case APPROVED:
        throw new InstanceNotFoundException("이미 친구 입니다.");
      case REQUESTED:
        // 본인 친구 요청 거절
        avatar.rejectAvatarFriend(friend);
        // 상대방 친구 목록에 본인이 친구 신청한적이 있는지 확인
        if (friend.checkFriendInAvatarFriendSet(avatar) == AvatarFriendStatus.REQUESTED) {
          friend.rejectAvatarFriend(avatar);
        }
    }
  }

  @Transactional
  public boolean sendEmail(Channel channel, User user) {
    Avatar findAvatar =
        avatarRepository
            .findAvatarWithChannelIdAndUserId(channel.getId(), user.getId())
            .orElseThrow(() -> new InstanceNotFoundException("아바타가 존재하지 않습니다."));
    String inviteCode = codeGenerator.generateRandomCode();
    findAvatar.updateInviteCode(inviteCode);
    inviteEmailSender.invite(user.getEmail(), inviteCode);
    return true;
  }

  //  @Transactional
  //  public void avatarEnterChatRoom(ChatRoom chatRoom, Avatar avatar) {
  //    avatar.addAvatarChatRoom(chatRoom);
  //  }
  //
  //  @Transactional
  //  public void avatarLeaveChatRoom(ChatRoom chatRoom, Avatar avatar) {
  //    avatar.removeAvatarChatRoom(chatRoom);
  //  }

  //  @Transactional
  //  public void addAvatarScrapNotice(Notice notice, Avatar avatar) {
  //    avatar.addAvatarScrapNotice(notice);
  //  }
}
