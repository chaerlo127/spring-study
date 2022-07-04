package com.example.spring_security.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue()); // token
//        System.out.println("userRequest: " + userRequest.getClientRegistration().getRegistrationId());
        System.out.println("getAttributes: " + super.loadUser(userRequest).getAttributes());
        return super.loadUser(userRequest);
    }
}

// username = google_[sub 번호]
// password = "암호화(겟인데어)
// email = [google email]
// role = ROLE_USER
// provider = google
// providerId = [sub 번호]
