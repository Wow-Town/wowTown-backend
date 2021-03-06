package com.wowtown.wowtownbackend.user.domain;

import com.wowtown.wowtownbackend.channel.domain.Channel;
import lombok.Getter;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String userName;

  private String hashedPW;

  private String salt;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserChannel> userChannelList = new ArrayList<>();

  protected User() {}

  public User(String email, String userName) {
    this.email = email;
    this.userName = userName;
    this.createAt = LocalDateTime.now();
    this.updateAt = null;
  }

  public User(String email, String userName, String hashedPW, String salt) {
    this.email = email;
    this.userName = userName;
    this.hashedPW = hashedPW; // sha256 encrypt된 pw들어옴
    this.salt = salt;
    this.createAt = LocalDateTime.now();
    this.updateAt = null;
  }

  public void updateUser(User payload) {
    this.email = payload.getEmail();
    this.userName = payload.getUserName();
    this.updateAt = LocalDateTime.now();
  }

  public void updateUserPW(String updatePW, String updateSalt) {
    this.hashedPW = updatePW;
    this.salt = updateSalt;
    this.updateAt = LocalDateTime.now();
  }

  public void addUserChannel(Channel payload) {
    UserChannel userChannel = new UserChannel();
    userChannel.setUser(this);
    userChannel.setChannel(payload);
    this.userChannelList.add(userChannel);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return this.id == user.id && this.email == user.email;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.email);
  }
}
