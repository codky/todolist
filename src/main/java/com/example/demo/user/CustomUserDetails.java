package com.example.demo.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomUserDetails implements UserDetails, OAuth2User {

    private final SiteUser user;
    private final Map<String, Object> attributes; // OAuth2 사용자 속성
    private final Collection<? extends GrantedAuthority> authorities; // 권한 정보
    
    // 일반 사용자 생성자
    public CustomUserDetails(SiteUser user, Collection<? extends GrantedAuthority> authorities) {
    	this.user = user;
    	this.attributes = null; // 일반 사용자는 OAuth2 속성이 없음
    	this.authorities = authorities;
    }

    // OAuth2 사용자 생성
    public CustomUserDetails(SiteUser user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes; // OAuth2 속성 저장
        this.authorities = new ArrayList<>();
    }

    // OAuth2 사용자 속성 반환
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // OAuth2 사용자 이름 반환
    @Override
    public String getName() {
    	if(attributes != null) {
    		return (String) attributes.get("name"); // Google의 name 속성을 반환
    	}
    	
    	return user.getNickname(); // 일반 사용자는 nickname 사용
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> "ROLE_" + user.getRole().name());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLoginId(); // 수정된 엔티티에 따라 loginId 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}