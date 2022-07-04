package com.example.spring_security.config.auth;

// 시큐리티가 로그인 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료되면 시큐리티 세션을 만들어준다 (Security ContextHolder)
// 오브젝트 => Authentication 타입의 객체
// Authentication 안에 유저 정보가 있어야 한다.
// User 오브젝트의 타입 => UserDetails 타입 객체

import com.example.spring_security.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Security Session => Authentication => UserDetails
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    //일반 로그인
    public PrincipalDetails(User user){
        this.user = user;
    }


    //OAuth 로그인
    public PrincipalDetails(User user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }


    @Override
    public String getName() {
        return null;
//        return (String) attributes.get("sub");
    }

    // 홰당 유저의 권한을 리턴하는 것
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Collection<GrantedAuthority> collect = new ArrayList<>();
       collect.add(new GrantedAuthority() {
           @Override
           public String getAuthority() {
               return user.getRole();
           }
       });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 1년동안 회원이 로그인을 안하면 휴먼계정이 되는 것이다.
        // user.getLoginDate();
        // 현재시간 - 로그인 시간 => 1년을 초과하면 false
        return true;
    }

}
