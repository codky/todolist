package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링의 환경설정 파일임을 의미
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 하고, 스프링 시큐리티를 활성화 한다. 내부적으로 SecurityFilterChain 클래스가 동작하여 모든 요청 URL에 이 클래스가 필터로 적용되어 URL별로 특별한 설정을 할 수 있게 된다.
@EnableMethodSecurity(prePostEnabled = true) // 로그인 여부를 판별할때 사용하는 @PreAuthorize 애너테이션을 사용하기 위해 필요한 설정
public class SecurityConfig {
	
	// 스프링 시큐리티의 세부설정은 @Bean 애너테이션을 통해 SecurityFilterChain 빈을 생성하여 설정한다.
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 인증되지 않은 모든 페이지의 요청을 허락한다. 로그인하지 않더라도 모든페이지에 접근할 수 있다.
		http
			.csrf((csrf) -> csrf.disable()) // CSRF를 비활성화 (개발환경에서만)
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
			.formLogin(formLogin -> formLogin // 로그인 설정
					.loginPage("/user/login") // 로그인 페이지 URL
					.defaultSuccessUrl("/todo/list")) // 로그인 성공 시 이동할 페이지는 루트 URL
			.logout((logout) -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true))
		;
		
		http
			.oauth2Login((auth) -> auth
					.loginPage("/user/login") // OAuth 로그인 페이지
					.defaultSuccessUrl("/todo/list")
					.failureUrl("/user/login?error=true") // 로그인 실패시 이동
					.permitAll());
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
