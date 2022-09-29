package com.wowtown.wowtownbackend.chatroom.domain;

import com.wowtown.wowtownbackend.avatar.domain.Avatar;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private MessageType type;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "SENDER_ID")
  private Avatar sender;

  @Lob private byte[] message;

  private Integer count; // 읽지 않은 사용자 수

  private LocalDateTime sendAt;

  protected ChatMessage() {}

  public ChatMessage(MessageType type, Avatar sender, byte[] message, Integer count) {
    this.type = type;
    this.sender = sender;
    this.message = message;
    this.count = count;
    this.sendAt = LocalDateTime.now();
  }

  public void decreaseCount() {
    if (this.count != 0) {
      this.count--;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ChatMessage)) {
      return false;
    }
    ChatMessage chatMessage = (ChatMessage) o;
    return this.sender == chatMessage.sender && this.message == chatMessage.message;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.sender, this.message);
  }
}
