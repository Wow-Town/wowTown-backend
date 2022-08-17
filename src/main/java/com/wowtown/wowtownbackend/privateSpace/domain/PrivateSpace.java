// package com.wowtown.wowtownbackend.privateSpace.domain;
//
// import com.wowtown.wowtownbackend.studyGroup.domain.Notice;
// import lombok.Getter;
//
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.OneToOne;
//
// @Getter
// @Entity
// public class PrivateSpace {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;
//
//  @OneToOne(mappedBy = "privateSpace")
//  private Notice studyGroup;
//
//  public PrivateSpace() {}
// }
