// package com.wowtown.wowtownbackend.chatroom.domain;
//
// import com.wowtown.wowtownbackend.studyGroup.domain.StudyGroup;
// import lombok.Getter;
//
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.OneToOne;
//
// @Getter
// @Entity
// public class MultiChatRoom {
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;
//
//  private String roomName;
//
//  private int participantsNum;
//
//  @OneToOne(mappedBy = "multiChatRoom")
//  private StudyGroup studyGroup;
//
//  protected MultiChatRoom() {}
//
//  public MultiChatRoom(String roomName, int participantsNum) {
//    this.roomName = roomName;
//    this.participantsNum = participantsNum;
//  }
// }
