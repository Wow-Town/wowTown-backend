 package com.wowtown.wowtownbackend.chatroom.domain;

 import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
 import lombok.Getter;

 import javax.persistence.*;

 @Getter
 @Entity
 public class MultiChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String roomName; //어차피 공고랑 연결된 거면 채팅방의 이름이 필요?

  private int personnel; //최대 인원수
  private int participantsNum; //현재 참여자 수

  @OneToOne(mappedBy = "multiChatRoom")
  private StudyGroup studyGroup;

  protected MultiChatRoom() {}

  public MultiChatRoom(String roomName, int personnel,StudyGroup studyGroup) {
    this.roomName = roomName;
    this.personnel = personnel;
    this.participantsNum =0;
    this.studyGroup = studyGroup;
  }
  public MultiChatRoom(String name,int personnel){
   this.roomName = name;
   this.personnel = personnel;
  }
  public void updateMultiChatRoom(MultiChatRoom updatePayLoad){
   this.roomName = updatePayLoad.getRoomName();
   this.personnel = updatePayLoad.getPersonnel();
  }
  public void enterMultiChatRoom(){
   this.participantsNum +=1;
  }
  public void exitMultiChatRoom(){
   this.participantsNum -=1;
  }
 }
