package com.example.introductionspring.repository;

import com.example.introductionspring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 리포지토리에 구현 클래스 없이 인터페이스 만으로 개발을 완료할 수 있다.
 * 기본 CRUD 기능도 스프링 데이터 JPA가 모두 제공
 *
 * 스프링 데이터 JPA가 SpringDataMemberRepository를 스프링 빈으로 자동 등록해준다.
 *
 *
 * 실무에서는 JPA와 스프링 JPA를 기본적으로 사용하고, 복잡한 동적 쿼리는 Querydsl이란느 라이브러리를 사용
 * Querydsㅣ을 사용하면 쿼리도 자바 코드로 안전하게 작성이 가능하고, 동적 쿼리도 편리하게 작성할 수 있다.
 *
 * 이 조합으로 해결하기 어려운 쿼리는 => JPA가 제공하는 네이티브 쿼리, JDBC Template 사용
 */
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {


    // select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
}
