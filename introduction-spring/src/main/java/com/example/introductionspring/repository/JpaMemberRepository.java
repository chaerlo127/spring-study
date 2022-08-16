package com.example.introductionspring.repository;

import com.example.introductionspring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * JdbcTemplate으로 코드는 확 줄었지만, sql을 작성해야한다는 단점이 있음
 * JPA는 기존의 코드 반복은 물론이고, SQL도 JPA가 직접 만들어 실행해줌
 * 객체 중심의 설계로 패러다임을 전환할 수 있음
 * 개발 생산성을 높일 수 있음.
 */
public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }
    // pk 가 아닌 나머지 jpa 들은 query를 작성해주어야 한다.
    @Override
    public Member save(Member member) {
        em.persist(member); // persist 지속하다. 영쇽하다.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
