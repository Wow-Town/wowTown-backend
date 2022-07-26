 package com.wowtown.wowtownbackend.avatar.domain;

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
  @JoinColumn(name = "AVATAR_ID")
  private Avatar following;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AVATAR_ID")
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
 }
