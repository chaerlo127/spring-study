package com.example.introductionspring;
/**
 * 위 패키지 하위 클래스들을 확인하여 스프링 빈으로 자동 등록해줌
 * ex> com.example에서는 스프링 빈으로 등록이 되지 않음
 * 
 * 스프링은 스프링 컨테이너에 빈을 등록할 때, 기본으로 싱글톤으로 등록함.
 * 유일하게 하나만 등록해서 공유함
 * 같은 스프링 빈이면 모두 같은 인스턴스
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntroductionSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntroductionSpringApplication.class, args);
	}

}
