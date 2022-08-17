// package com.wowtown.wowtownbackend.studyGroup.domain;
//
//// import com.wowtown.wowtownbackend.common.domain.Interest;
//
// import com.wowtown.wowtownbackend.common.domain.InterestType;
// import lombok.ToString;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
// import java.util.Set;
//
// import static org.assertj.core.api.Assertions.assertThat;
//
// @ToString
// @DataJpaTest
// public class StudyGroupRepositoryTest {
//  @Autowired private JpaNoticeRepository studyGroupRepository;
//
//  @Test
//  void save() {
//    Notice studyGroup = new Notice("wowTown", 10, "알고리즘 스터디", NoticeStatus.OPEN);
//    studyGroupRepository.save(studyGroup);
//    Notice result = studyGroupRepository.findById(studyGroup.getId()).get();
//    assertThat(result).isEqualTo(studyGroup);
//  }
//
//  @Test
//  public void delete() {
//    Notice studyGroup = new Notice("wowTown", 10, "알고리즘 스터디", NoticeStatus.OPEN);
//    studyGroupRepository.save(studyGroup);
//    Long tmp = studyGroup.getId();
//    Optional<Notice> studyGroup1 = studyGroupRepository.findById(tmp);
//    org.junit.jupiter.api.Assertions.assertTrue(studyGroup1.isPresent());
//    studyGroupRepository.delete(studyGroup);
//    studyGroup1 = studyGroupRepository.findById(tmp);
//    org.junit.jupiter.api.Assertions.assertFalse(studyGroup1.isPresent());
//  }
//
//  @Test
//  public void findById() {
//    Notice studyGroup = new Notice("wowTown", 10, "알고리즘 스터디", NoticeStatus.OPEN);
//    studyGroupRepository.save(studyGroup);
//    Notice result = studyGroupRepository.findById(studyGroup.getId()).get();
//    assertThat(result).isEqualTo(studyGroup);
//  }
//
//  @Test
//  public void findByName() {
//    Notice studyGroup = new Notice("wowTown", 10, "알고리즘 스터디", NoticeStatus.OPEN);
//    studyGroupRepository.save(studyGroup);
//    Notice studyGroup1 = new Notice("wowTown2", 20, "리액트 스터디", NoticeStatus.OPEN);
//    studyGroupRepository.save(studyGroup1);
//
//    List<Notice> studyGroupList = studyGroupRepository.findBySubjectContaining("wTo");
//    assertThat(studyGroupList.size()).isEqualTo(2);
//  }
//
//  @Test
//  public void findByInterestType() {
//    Notice studyGroup = new Notice("wowTown", 10, "알고리즘 스터디", NoticeStatus.OPEN);
//    studyGroup.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup.getInterestTypes().add(InterestType.FRONTEND);
//    studyGroupRepository.save(studyGroup);
//    Notice studyGroup1 = new Notice("wowTown2", 20, "알고리즘 스터디2", NoticeStatus.OPEN);
//    studyGroup1.getInterestTypes().add(InterestType.BACKEND);
//    studyGroupRepository.save(studyGroup1);
//    Notice studyGroup2 = new Notice("wowTown3", 30, "알고리즘 스터디3", NoticeStatus.OPEN);
//    studyGroup2.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup2.getInterestTypes().add(InterestType.CPP);
//    studyGroupRepository.save(studyGroup2);
//    Notice studyGroup3 = new Notice("wowTown4", 40, "알고리즘 스터디4", NoticeStatus.OPEN);
//    studyGroupRepository.save(studyGroup3);
//    Set<Notice> studyGroupList =
// studyGroupRepository.findByInterestTypes(InterestType.BACKEND);
//    for (Notice sg : studyGroupList) {
//      System.out.println(sg.getSubject());
//    }
//    assertThat(studyGroupList.size()).isEqualTo(3);
//  }
//
//  /* @Test
//  public void findByManyInterestTypes() { //한개만 포함된 거까지 다 나오는
//    Notice studyGroup = new Notice();
//    studyGroup.setStudyGroupName("wowTown");
//    studyGroup.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup.getInterestTypes().add(InterestType.FRONTEND);
//    studyGroup.getInterestTypes().add(InterestType.CODINGTEST);
//    studyGroupRepository.save(studyGroup);
//
//    Notice studyGroup1 = new Notice();
//    studyGroup1.setStudyGroupName("wowTown2");
//    studyGroup1.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup1.getInterestTypes().add(InterestType.CODINGTEST);
//    studyGroupRepository.save(studyGroup1);
//
//    Notice studyGroup2 = new Notice();
//    studyGroup2.setStudyGroupName("wowTown3");
//    studyGroup2.getInterestTypes().add(InterestType.BACKEND);
//    studyGroupRepository.save(studyGroup2);
//
//
//    List<InterestType> interestTypes = new ArrayList<>();
//    interestTypes.add(InterestType.CODINGTEST);
//    interestTypes.add(InterestType.BACKEND);
//    Set<Notice> studyGroupList = studyGroupRepository.findByManyInterestTypes(interestTypes);
//    for(Notice sg:studyGroupList){
//      System.out.println(sg.getStudyGroupName());
//    }
//    assertThat(studyGroupList.size()).isEqualTo(2);
//  }*/
//  @Test
//  public void findByManyInterestTypes2() { // not in 으로 편법 느낌
//    Notice studyGroup = new Notice("wowTown", 10, "알고리즘 스터디", NoticeStatus.OPEN);
//    studyGroup.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup.getInterestTypes().add(InterestType.FRONTEND);
//    studyGroup.getInterestTypes().add(InterestType.CODINGTEST);
//    studyGroupRepository.save(studyGroup);
//
//    Notice studyGroup1 = new Notice("wowTown2", 20, "알고리즘 스터디2", NoticeStatus.OPEN);
//    studyGroup1.getInterestTypes().add(InterestType.BACKEND);
//    studyGroup1.getInterestTypes().add(InterestType.CODINGTEST);
//    studyGroupRepository.save(studyGroup1);
//
//    Notice studyGroup2 = new Notice("wowTown3", 30, "알고리즘 스터디3", NoticeStatus.OPEN);
//    studyGroup2.getInterestTypes().add(InterestType.BACKEND);
//    studyGroupRepository.save(studyGroup2);
//
//    List<InterestType> interestTypes = new ArrayList<>();
//    interestTypes.add(InterestType.CODINGTEST);
//    interestTypes.add(InterestType.BACKEND);
//    Set<Notice> studyGroupList = studyGroupRepository.findByManyInterestTypes2(interestTypes);
//    for (Notice sg : studyGroupList) {
//      System.out.println(sg.getSubject());
//      for (InterestType interestType : sg.interestTypes) {
//        System.out.println(interestType);
//      }
//    }
//    assertThat(studyGroupList.size()).isEqualTo(1);
//  }
//
//  @AfterEach
//  public void afterEach() {
//    studyGroupRepository.deleteAll();
//  }
// }
