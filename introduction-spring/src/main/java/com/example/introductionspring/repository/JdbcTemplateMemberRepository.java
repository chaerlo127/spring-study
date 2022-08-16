package com.example.introductionspring.repository;

import com.example.introductionspring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 순수 jdbc와 동일한 환경설정
 * JDBCTemplate MyBatis 같은 라이브러리는 jdbc api 에서 반복된 코드를 제거해줌.
 * SQL은 직접 작성
 */
public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    // 생성자가 하나만 있으면 @Autowired 생략 가능능
    public JdbcTemplateMemberRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName()); // 위 4줄은 insert 문

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> members =  jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return members.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> members =  jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return members.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member where id = ?", memberRowMapper());
    }


    // 객체 생성인 것 은 이 메소드에서
    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
