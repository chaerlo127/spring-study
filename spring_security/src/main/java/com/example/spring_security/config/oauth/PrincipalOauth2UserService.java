package com.example.spring_security.config.oauth;

import com.example.spring_security.config.auth.PrincipalDetails;
import com.example.spring_security.model.User;
import com.example.spring_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    private UserRepository userRepository;
    // Security Config 순환 참조가 걸려 진행이 안됨.
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public PrincipalOauth2UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    // 구글로 부터 받은 userRequest 데이터에 대한 후처리가 되는 함수
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration()); // registrationId로 어떤 OAuth 로 로그인 가능한지 나옴.
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue()); // token
//        System.out.println("userRequest: " + userRequest.getClientRegistration().getRegistrationId());


        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -< code를 리턴 (OAuth - Client 라이브러리) -> AccessToken 요청
        // User Request 정보 -> 회원 프로필 정보를 받아야 함 (loadUser 함수) -> 구글로 부터 회원 프로필 받아준다.


        System.out.println("getAttributes: " + oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getClientId();//google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider+"_"+providerId;//google_[sub 번호]
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email  = oAuth2User.getAttribute("email");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}

// username = google_[sub 번호]
// password = "암호화(겟인데어)
// email = [google email]
// role = ROLE_USER
// provider = google
// providerId = [sub 번호]
