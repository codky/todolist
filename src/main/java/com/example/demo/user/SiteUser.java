package com.example.demo.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class SiteUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // Primary Key
	
	@Column(unique = true)
	private String loginId; // 로그인 식별자 (google_xxx, naver_xxx)
	
	@Column
	private String nickname;
	
	private String password;
	
	@Column(unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;

	private String provider; // 소셜 로그인 제공자 (google, naver, kakao)
	
	private String providerId; // 로그인 유저의 고유 ID
	
}
