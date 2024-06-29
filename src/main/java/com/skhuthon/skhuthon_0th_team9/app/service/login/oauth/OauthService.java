package com.skhuthon.skhuthon_0th_team9.app.service.login.oauth;

import com.skhuthon.skhuthon_0th_team9.app.dto.user.login.OAuthDto;

public interface OauthService {

    OAuthDto.LoginResponse getAccessToken(String code);
    OAuthDto.UserResponse getUserInfo(String accessToken);
}
