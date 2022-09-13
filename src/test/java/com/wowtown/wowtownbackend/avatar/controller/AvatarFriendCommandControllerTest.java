// package com.wowtown.wowtownbackend.avatar.controller;
//
// import com.wowtown.wowtownbackend.avatar.domain.Avatar;
// import com.wowtown.wowtownbackend.avatar.domain.AvatarFriend;
// import com.wowtown.wowtownbackend.avatar.domain.AvatarRepository;
// import com.wowtown.wowtownbackend.error.exception.InstanceNotFoundException;
// import com.wowtown.wowtownbackend.friend.controller.FriendCommandController;
// import com.wowtown.wowtownbackend.friend.domain.FriendRepository;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
//
// import javax.transaction.Transactional;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;
//
// @SpringBootTest
// class AvatarFriendCommandControllerTest {
//  @Autowired private AvatarCommandController avatarCommandController;
//  @Autowired private AvatarRepository avatarRepository;
//  @Autowired private FriendCommandController friendCommandController;
//  @Autowired private FriendRepository friendRepository;
//
//  @Test
//  @Transactional
//  public void follow() {
//    Avatar avatar = new Avatar("avatar", "제발되라");
//    Avatar avatar1 = new Avatar("avatar1", "제발되라");
//    Avatar avatar2 = new Avatar("avatar2", "제발되라");
//    avatarRepository.save(avatar);
//    avatarRepository.save(avatar1);
//    avatarRepository.save(avatar2);
//
//    AvatarFriend avatarFriend = new AvatarFriend(avatar, avatar1);
//    avatar.getFollowingFriendList().add(avatarFriend);
//    avatar1.getFollowerFriendList().add(avatarFriend);
//    AvatarFriend avatarFriend1 = new AvatarFriend(avatar2, avatar1);
//    avatar2.getFollowingFriendList().add(avatarFriend1);
//    avatar1.getFollowerFriendList().add(avatarFriend1);
//    friendRepository.save(avatarFriend);
//    friendRepository.save(avatarFriend1);
//    //        if(findFriendList ==null){
//    //            throw new InstanceNotFoundException("팔로우 목록 없음");
//    //        }
//    List<AvatarFriend> findFollowingList = new ArrayList<>();
//    findFollowingList =
//        friendRepository.findWithFollowerId(avatar1.getId()).stream()
//            .filter(f -> !f.checkFriendStatusIsApproved())
//            .collect(Collectors.toList());
//    ;
//    if (findFollowingList == null) {
//      throw new InstanceNotFoundException("follow 하고 있는 사람 없음");
//    }
//    for (AvatarFriend findAvatarFriend : findFollowingList) {
//
//      if (findAvatarFriend.getFollowing().equals(avatar)) {
//        System.out.println("avatar 팔로우하고 있는 것 확인");
//      } else if (findAvatarFriend.getFollowing().equals(avatar2)) {
//        System.out.println("avatar2 팔로우하고 있는 것 확인");
//      }
//    }
//  }
//
//  @Test
//  public void followApprove() {}
// }
