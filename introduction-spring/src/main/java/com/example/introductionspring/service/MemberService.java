package com.example.introductionspring.service;

import com.example.introductionspring.domain.Member;
import com.example.introductionspring.repository.MemberRepository;
import com.example.introductionspring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
    * 회원 가입
    */
    public Long join(Member member){
        // 같은 이름이 있는 중복 회원은 안됨
        // ctrl + a/t + v -> 원하는 리턴 값 자동으로 반환해줌
        // ctrl + a/t + shift + T -> Refactor this -> method -> extract method
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
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
