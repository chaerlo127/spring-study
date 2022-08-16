package com.example.introductionspring;

import com.example.introductionspring.repository.JpaMemberRepository;
import com.example.introductionspring.repository.MemberRepository;
import com.example.introductionspring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * OCP : Open - Closed Principle 개방 폐쇄 원칙
 * 확장에는 열려있고 수정에는 닫혀있다.
 *
 * DI(Dependencies Injection)을 사용하면 기존 코드를 사용하지 않고 설정만으로 구현 클래스 변경 가능
 */
@Configuration
public class SpringConfig {
//    private final DataSource dataSource;
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    private final EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
// return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}
