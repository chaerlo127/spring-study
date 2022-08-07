package com.example.introductionspring;
/**
 * 위 패키지 하위 클래스들을 확인하여 스프링 빈으로 자동 등록해줌
 * ex> com.example에서는 스프링 빈으로 등록이 되지 않음
 * 
 * 스프링은 스프링 컨테이너에 빈을 등록할 때, 기본으로 싱글톤으로 등록함.
 * 유일하게 하나만 등록해서 공유함
 * 같은 스프링 빈이면 모두 같은 인스턴스
 *
 *
 * 컴포넌트 스캔: 실무에서 정형화 된 컨트롤러, 서비스, 리포지토리 같은 코드
 * 스프링 빈: 정형화되지 않거나, 상황에 따라 변경을 해야하는 경우
 *
 *
 * @Autowired 를 통한 DI는 helloController, MemberService 등과 같이 스프링이 관리하는 객체에서만 동작
 * 스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않음.
 * -> 스프리이 관리, 스프링에 등록이 된 객체만 가능(@Service, @Controller, @Repository 등)
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntroductionSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntroductionSpringApplication.class, args);
	}

}
