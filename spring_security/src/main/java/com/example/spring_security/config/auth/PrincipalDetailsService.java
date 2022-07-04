package com.example.spring_security.config.auth;

import com.example.spring_security.model.User;
import com.example.spring_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login")
// 로그인 요청이 오면 자동으로 PrincipalDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {


    private UserRepository userRepository;
    public PrincipalDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // 시큐리티 세션 = Authentication  = UserDetails
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            return null;
        }else {
            return new PrincipalDetails(user);
        }

    }
}
