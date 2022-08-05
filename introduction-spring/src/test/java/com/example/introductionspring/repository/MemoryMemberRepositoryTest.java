package com.example.introductionspring.repository;

import com.example.introductionspring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import java.util.List;
/*
* test는 순서 보장이 안됨. 순서 상관 없이 method 별로 따로 동작 가능 하도록 설계를 해아한다.
* 서로 의존관계 없이 설계가 가능해야 한다.
* 따라서 test가 끝나면 데이터를 clear 해줘야함.
* */
public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();


    /*
    * test 끝날 때마다 이 method 호출 됨.
    * 저장소를 지워줌
    * */
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }
    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        /*
        * (@Nullable Object expected, Object actual)
        * */
//        Assertions.assertEquals(member, result);
        //assertEquals도 사용하지만 주로 실무 Test 시에 assertThat를 사용
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }


}
