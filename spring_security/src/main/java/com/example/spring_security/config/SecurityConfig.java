package com.example.spring_security.config;


import com.example.spring_security.config.oauth.PrincipalOauth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity// 스프링 시큐리티 필터가 스프링 필터 체인에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize/postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PrincipalOauth2UserService principalOauth2UserService;

    public SecurityConfig(PrincipalOauth2UserService principalOauth2UserService){
        this.principalOauth2UserService = principalOauth2UserService;
    }

    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/loginForm")// 권한이 필요한 곳에서는(user, admin, manager) login 화면으로 이동한다.
                .loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
                .defaultSuccessUrl("/")
                //oauth 네이버, 카카오, 구글 로그인 가능하도록 만듦
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                //1. 코드 받기(인증) 2. 엑세스 토큰(권한)
                // 3. 사용자프로필 정보를 가져와서 4. 그 정보를 토대로 회원가입 자동으로 진행시킴
                // 4-2. 사용자 정보가 모자라면 (이메일, 전화번호, 이름, 아이디) 추가직인 구성이 필요하면 추가적인 회원가입 창이 나옴.
                // 구글 로그인이 완료가 되면 엑세스 토큰 + 사용자 프로필 정보를 한번에 받음.
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
        ;
    }
}
