package com.example.introductionspring.service;

import com.example.introductionspring.domain.Member;
import com.example.introductionspring.repository.MemberRepository;
import com.example.introductionspring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
@Transactional // jpa를 사용하려면 Transactional이 있어야 한다.
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * DI에는 필드, setter, 생성자 주입 이렇게 3가지 방법이 있음.
     */
//    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /**
    * 회원 가입
    */
    public Long join(Member member){
        /**
         * AOP
         *
         * 호출 시간을 측정
         * 공통 관심사항(cross-cutting concern) vs 핵심 관심 사항(core concern)
         * 회원 가입 시간, 회원 조회 시간 측정
         */

        /**
         * AOP가 필요한 이유
         *  시간 측정은 핵심 관심사항이 아님
         *  시간을 측정하는 로직은 공통 관심사항
         *  try 문 안에 핵심 관심 사항, catch 안에 공통 관심 사항이 있으면 유지 보수가 어려움
         *  시간 측정을 하는 로직을 별도 공통 로직으로 만들기 매우 어려움
         *  시간 측정 로직을 변경하면 모든 로직을 찾아가 변경해야 함.
         */
        long start = System.currentTimeMillis();
        try{
            // 같은 이름이 있는 중복 회원은 안됨
            // ctrl + a/t + v -> 원하는 리턴 값 자동으로 반환해줌
            // ctrl + a/t + shift + T -> Refactor this -> method -> extract method
            validateDuplicateMember(member); // 중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        }finally{
            long finish = System.currentTimeMillis();
            long timeMs = finish-start;
            System.out.println("join = " + timeMs + "ms");
        }


    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
