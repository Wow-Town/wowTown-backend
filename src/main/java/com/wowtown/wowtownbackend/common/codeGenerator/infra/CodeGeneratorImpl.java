package com.wowtown.wowtownbackend.common.codeGenerator.infra;

import com.wowtown.wowtownbackend.common.codeGenerator.CodeGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CodeGeneratorImpl implements CodeGenerator {
  @Override
  public String generateRandomCode() {
    // ASCII range – alphanumeric (0-9, a-z, A-Z)
    final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    SecureRandom random = new SecureRandom();
    StringBuilder sb = new StringBuilder();

    // each iteration of the loop randomly chooses a character from the given
    // ASCII range and appends it to the `StringBuilder` instance

    for (int i = 0; i < 10; i++) {
      int randomIndex = random.nextInt(chars.length());
      sb.append(chars.charAt(randomIndex));
    }

    return sb.toString();
  }
}
