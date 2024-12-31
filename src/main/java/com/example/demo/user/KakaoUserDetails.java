package com.example.demo.user;

import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KakaoUserDetails implements OAuth2UserInfo {
	
	private Map<String, Object> attributes;
	
	
	@Override
	public String getProvider() {
		return "kakao";
	}


	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("id"));
	}


	@Override
	public String getEmail() {
		return (String) ((Map) attributes.get("kakao_account")).get("email");
	}


	@Override
	public String getName() {
		return (String) ((String) attributes.get("name"));
	}
	
	

}
