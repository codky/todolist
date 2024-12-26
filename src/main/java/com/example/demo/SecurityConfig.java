package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링의 환경설정 파일임을 의미
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 하고, 스프링 시큐리티를 활성화 한다. 내부적으로 SecurityFilterChain 클래스가 동작하여 모든 요청 URL에 이 클래스가 필터로 적용되어 URL별로 특별한 설정을 할 수 있게 된다.
public class SecurityConfig {
	
	// 스프링 시큐리티의 세부설정은 @Bean 애너테이션을 통해 SecurityFilterChain 빈을 생성하여 설정한다.
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 인증되지 않은 모든 페이지의 요청을 허락한다. 로그인하지 않더라도 모든페이지에 접근할 수 있다.
		http
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
		;
		
		return http.build();
				
	}

}
