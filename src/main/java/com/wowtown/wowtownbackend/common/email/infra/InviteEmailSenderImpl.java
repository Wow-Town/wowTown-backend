package com.wowtown.wowtownbackend.common.email.infra;

import com.wowtown.wowtownbackend.common.email.InviteEmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Log4j2
@Component
@RequiredArgsConstructor
public class InviteEmailSenderImpl implements InviteEmailSender {
  private final JavaMailSender javaMailSender;

  private String setFrom = "devconf5296@gmail.com"; // 보내는 이메일

  private String title = "WowTown 확인 메일"; // 메일 제목(생략 가능)

  public void invite(String toSendEmail, String inviteCode) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

      messageHelper.setFrom(this.setFrom);
      messageHelper.setTo(toSendEmail);
      messageHelper.setSubject(this.title);
      messageHelper.setText(this.getContent(inviteCode));

      javaMailSender.send(message);
    } catch (Exception e) {
      log.info(e.getMessage());
      log.info("메시지 전송 오류");
    }
  }

  private String getContent(String inviteCode) {

    return "WowTown 입장코드 입니다.\n" + inviteCode;
  }
}
