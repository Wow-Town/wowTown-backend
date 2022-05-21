package com.wowtown.wowtownbackend.user.application.common;

import com.wowtown.wowtownbackend.user.domain.User;

public interface JwtTokenProvider {
  public String createAccessToken(String userEmail);

  public String createRefreshToken(String userEmail);

  public User getAuthenticatedUser(String token);

  public String getUserEmail(String token);

  public boolean validateToken(String accessToken);
}
