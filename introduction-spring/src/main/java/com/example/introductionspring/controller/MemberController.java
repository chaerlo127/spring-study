package com.example.introductionspring.controller;

import com.example.introductionspring.domain.Member;
import com.example.introductionspring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * 객체를 생성해서 spring 컨테이너가 관리를 해줌
 * 스프링 빈 등록 방법
 * - 컴포넌트(@Component) 스캔, 자동 의존관계 설정 (@Controller, @Service, @Repository)
 *   @Component가 있으면 자동으로 스프링 빈으로 등록
 *   @Autowired가 Controller Service, Repository 를 연결해주는 역할
 * - 자바 코드로 직접 스프링 빈 등록
 */
@Controller
public class MemberController {
    private MemberService memberService;


    /**
     * 멤버 서비스 객체를 가져다 준다.
     * 의존 관계를 주입해준다. (Dependency Injection)
     */
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);

        return "redirect:/";
    }
}
