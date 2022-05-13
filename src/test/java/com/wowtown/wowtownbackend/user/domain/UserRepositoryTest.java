package com.wowtown.wowtownbackend.user.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
  @Autowired private UserRepository userRepository;

  User defaultUser;
  Long userId;
  String email;

  @BeforeEach
  @Transactional
  void init() {
    User user = new User("devconf@gamil.com", "홍길동", "1234", "abcd");
    this.defaultUser = userRepository.save(user);
    this.userId = defaultUser.getId();
    this.email = defaultUser.getEmail();
  }

  @AfterEach
  @Transactional
  void clear() {
    userRepository.deleteAll();
  }

  @Test
  @Transactional
  void save() {
    // given
    User user = new User("devconf@gamil.com", "홍길동", "1234", "abcd");
    // when
    User savedUser = userRepository.save(user);
    // then
    assertThat(user.getUserName()).isEqualTo(savedUser.getUserName());
    assertThat(user.getEmail()).isEqualTo(savedUser.getEmail());
    assertThat(user.getHashedPW()).isEqualTo(savedUser.getHashedPW());
    assertThat(user.getSalt()).isEqualTo(savedUser.getSalt());
  }

  @Test
  @Transactional
  void delete() {
    // when
    userRepository.delete(defaultUser);
    // then
    assertThat(userRepository.findUserById(defaultUser.getId()).isPresent()).isFalse();
  }

  @Test
  void findUserById() {
    // when
    User findUser = userRepository.findUserById(userId).get();
    // then
    assertThat(findUser).isEqualTo(defaultUser);
  }

  @Test
  void findUserByEmail() {
    // when
    User findUser = userRepository.findUserByEmail(email).get();
    // then
    assertThat(findUser).isEqualTo(defaultUser);
  }
}
