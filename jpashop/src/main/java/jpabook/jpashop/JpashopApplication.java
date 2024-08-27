package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
@SpringBootApplication : 이 패키지와 하위 패키지를 모두 스프링의 컴포넌트 스캔을 하여 스프링 빈에 자동 등록
 */
@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	// v1. 엔티티를 반환하는 방법
//	@Bean
//	Hibernate5JakartaModule hibernate5Module() {
//		return new Hibernate5JakartaModule();
//	}
}
