package com.wowtown.wowtownbackend.user.infra;

import com.wowtown.wowtownbackend.user.application.common.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.SecureRandom;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {
  static final int SALT_SIZE = 16;

  @Override
  public String encode(String password, String salt) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      String hashedPW = password;

      for (int i = 0; i < 10000; i++) {
        hashedPW += salt;
        md.update(hashedPW.getBytes());
        hashedPW = byteToString(md.digest());
      }

      return hashedPW;
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public String getSalt() {
    SecureRandom rnd = new SecureRandom();
    byte[] toSalt = new byte[SALT_SIZE];
    rnd.nextBytes(toSalt);
    return byteToString(toSalt);
  }

  private String byteToString(byte[] arr) {
    StringBuilder sb = new StringBuilder();

    for (byte item : arr) {
      sb.append(String.format("%02x", item));
    }
    return sb.toString();
  }
}
