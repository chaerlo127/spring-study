package com.example.introductionspring;

import com.example.introductionspring.repository.MemberRepository;
import com.example.introductionspring.repository.MemoryMemberRepository;
import com.example.introductionspring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
