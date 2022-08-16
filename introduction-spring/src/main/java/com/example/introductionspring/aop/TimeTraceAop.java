package com.example.introductionspring.aop;

import net.bytebuddy.implementation.bytecode.Throw;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
/**
 * AOP(Aspect Oriented Programming) : 관심 측정 프로그램
 *
 * 원하는 곳에 공통 관심 사항 적용
 *
 * 호출 시간을 측정
 * 공통 관심사항(cross-cutting concern) vs 핵심 관심 사항(core concern)
 * 회원 가입 시간, 회원 조회 시간 측정
 *
 * Aop 적용 후에는 가짜 proxy member service를 생성하고  joinPoint.proceed()를 통해서 실제 memberService와 연결함.
 */

/**
 * AOP가 필요한 이유
 *  시간 측정은 핵심 관심사항이 아님
 *  시간을 측정하는 로직은 공통 관심사항
 *  try 문 안에 핵심 관심 사항, catch 안에 공통 관심 사항이 있으면 유지 보수가 어려움
 *  시간 측정을 하는 로직을 별도 공통 로직으로 만들기 매우 어려움
 *  시간 측정 로직을 변경하면 모든 로직을 찾아가 변경해야 함.
 */

/**
 * 해결
 * 핵심 관심 사항과 시간을 측정하는 공통 관심 사항 분리
 * 시간을 측정하는 로직을 별도의 공통 로직으로 생성
 * 핵심 관심 사항을 깔끔하게 유지 가능
 * 변경이 필요하면 이 로직만 변경 가능 (TimeTraceAop class만 변경하면 됨)
 * 원하는 적용 대상을 선택 가능 (@Around annotation 을 통해서)
 */

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* com.example.introductionspring..*(..))")
    public Object execut(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try{
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
