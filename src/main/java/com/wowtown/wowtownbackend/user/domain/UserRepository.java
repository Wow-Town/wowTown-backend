package com.wowtown.wowtownbackend.user.domain;

import java.util.Optional;

public interface UserRepository {
  User save(User toSave);

  void delete(User user);

  void deleteAll();

  Optional<User> findUserById(Long userId);

  Optional<User> findUserByEmail(String email);
}
