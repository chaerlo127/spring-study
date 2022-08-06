package com.example.introductionspring.repository;

import com.example.introductionspring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();

    /*
    * key 값을 생성해주는 것
    * */
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
       return store.values().stream()
                .filter(member -> member.getName().equals(name)) // 하나씩 이름을 비교하여
                .findAny(); // 하나라도 찾으면 return
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        // store.values() -> Map 내부에 저장된 Member 객체 반환
    }

    public void clearStore(){
        store.clear();
    }
}
