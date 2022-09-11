package com.wowtown.wowtownbackend.common.email;

public interface InviteEmailSender {
  void invite(String toSendEmail, String inviteCode);
}
