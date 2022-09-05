package com.wowtown.wowtownbackend.user.application.common;

import com.wowtown.wowtownbackend.user.domain.User;

public interface JwtTokenProvider {
  String createAccessToken(String userEmail);

  String createRefreshToken(String userEmail);

  User getAuthenticatedUser(String token);

  String getUserEmail(String token);

  boolean validateToken(String accessToken);

  String updateAccessToken(String refreshToken);
}
