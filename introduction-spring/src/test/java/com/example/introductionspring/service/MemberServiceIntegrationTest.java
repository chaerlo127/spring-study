package com.example.introductionspring.service;

import com.example.introductionspring.domain.Member;
import com.example.introductionspring.repository.MemberRepository;
import com.example.introductionspring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *  Spring 코드 소스와 연결된 파일을 통해
 *  db와 연결하여 test를 진행할 수 있다.
 *
 *  스프링 통합 테스트 class이다.
 *
 *  스프링 통합 테스트보다 순수한 단위 테스트가 더 낫다.
 *  단위 단위로 쪼개며, container 없이 Test가 진행될 수 있도록 훈련을 해야한다. 좋은 테스트일 확률이 높음.
 */

@SpringBootTest
@Transactional // 이 어노테이션 덕분에 AfterEach, BeforeEach 사용하지 않아도 됨.
    /*
    * 이미 들어있는 DB의 정보를 AfterEach, BeforeEach를 하지 않아도 되는 어노테이션
    * 데이터 베이스는 Transactional이라는 개념이 잇음 (Auto commit, commit)
    * Test를 실행하면 commit을 해주고, Test가 끝나면 rollback을 해준다. 넣었던 데이터가 깔끔히 저장이 안되고 지워지게 된다.
    *
    *
    * [테스트 케이스에 이 어노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고,
    * 테스트 완료 후에 항상 롤백한다. 이렇게 하면 다른테스트에 영향을 주지 않는다. (DB에 데이터가 남지 않으므로)
    */
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    /**
     * 대부분 test를 진행할 때, 같은 DB를 사용하지 않고
     * Test DB를 따로 구축 후 사용한다.
     */
    @Test
    void 회원가입() {
        //given 주어지면
        Member member = new Member();
        member.setName("hello");

        //when 실행되면
        Long saveId = memberService.join(member);

        //then 결과 값
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        try{
//            memberService.join(member2);
//            fail("예외가 발생해야 합니다.");
//        }catch (IllegalStateException e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}