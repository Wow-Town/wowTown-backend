 package com.wowtown.wowtownbackend.friend.domain;

 import com.wowtown.wowtownbackend.avatar.domain.Avatar;
 import lombok.Getter;

 import javax.persistence.*;
 import java.time.LocalDateTime;

 @Getter
 @Entity
 public class Friend {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Enumerated(EnumType.STRING)
  private FriendStatus friendStatus;
  @ManyToOne(fetch = FetchType.LAZY)
  private Avatar following;

  @ManyToOne(fetch = FetchType.LAZY)
  private Avatar follower;

  private LocalDateTime createTime;

  public Friend() {}

  public boolean checkFriendStatusIsApproved(){
   if(this.friendStatus == FriendStatus.APPROVED){
    return true;
   }
   else{
    return false;
   }
  }
  public Friend(Avatar following,Avatar follower){
   this.follower = follower;
   this.following = following;
   this.friendStatus = FriendStatus.YET;

  }
  public void friendRequestApprove(){
   this.friendStatus = FriendStatus.APPROVED;
  }
  public long reFriendId(Long myId){ //프랜드 객체를 받아서 친구 Id만 리턴
   if(myId == this.following.getId()){
    return this.follower.getId();
   }
   else{
    return this.following.getId();
   }
  }
 }
