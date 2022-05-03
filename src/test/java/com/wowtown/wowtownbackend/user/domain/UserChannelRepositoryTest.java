// package com.wowtown.wowtownbackend.user.domain;
//
// import com.wowtown.wowtownbackend.channel.domain.Channel;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
// @DataJpaTest
// class UserChannelRepositoryTest {
//
//  @Autowired private UserChannelRepository userChannelRepository;
//
//  User defaultUser;
//  Long userId;
//  String email;
//
//  @BeforeEach
//  void init() {
//    User user = new User("devconf@gamil.com", "홍길동", "1234", "abcd");
//    Channel channel = new Channel("channel1");
//    this.defaultUser = userRepository.save(user);
//    this.userId = defaultUser.getId();
//    this.email = defaultUser.getEmail();
//  }
//
//  @AfterEach
//  void clear() {
//    userRepository.deleteAll();
//  }
//
//  @Test
//  void findUserChannelByUserId() {}
// }
