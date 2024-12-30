package com.example.demo.user;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomOauth2UserService extends DefaultOAuth2UserService {
	
	private final UserRepository userRepository;

	@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes :" + oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = switch (provider) {
	        case "google" -> new GoogleUserDetails(oAuth2User.getAttributes());
	        
	        // 추가적으로 네이버, 카카오 로그인 처리 가능
	        default -> throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다");
        };

        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + oAuth2UserInfo.getProviderId();


        // 기존 사용자 검색
        Optional<SiteUser> findUser = userRepository.findByEmail(oAuth2UserInfo.getEmail()); // 이메일 기반으로 중복 사용자 처리
        System.out.println("Find User: " + findUser.isPresent()); // 사용자 조회 결과 디버깅
        SiteUser user;

        if (findUser.isPresent()) {
        	user = findUser.get();
        	// 기존 사용자의 provider/provderId를 업데이트 (새로운 소셜 플랫폼 연결)
        	if(!user.getProvider().equals(provider)) {
        		user.setProvider(provider);
        		user.setProviderId(oAuth2UserInfo.getProviderId());
        		userRepository.save(user);
        	}
        } else {
        	// 새로운 사용자 생성
        	user = SiteUser.builder()
        			.loginId(loginId)
        			.nickname(oAuth2UserInfo.getName())
        			.email(email)
        			.provider(provider)
        			.providerId(oAuth2UserInfo.getProviderId())
        			.role(UserRole.USER)
        			.build();
        }

        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }
}
