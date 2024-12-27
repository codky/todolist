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

        OAuth2UserInfo oAuth2UserInfo = null;

        // 뒤에 진행할 다른 소셜 서비스 로그인을 위해 구분 => 구글
        if(provider.equals("google")){
        	System.out.println("구글 로그인");
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());

        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        System.out.println("Generated loginId: " + loginId); // loginId 디버깅
        String name = oAuth2UserInfo.getName();

        // 기존 사용자 검색
        Optional<SiteUser> findMember = userRepository.findByusername(loginId);
        System.out.println("Find Member: " + findMember.isPresent()); // 사용자 조회 결과 디버깅
        SiteUser member;

        if (findMember.isEmpty()) {
            member = SiteUser.builder()
                .username(loginId) // 고유 ID
                .email(email)
                .provider(provider)
                .providerId(providerId)
                .role(UserRole.USER)
                .build();
            userRepository.save(member);
        } else {
            // 이미 존재하는 사용자
            member = findMember.get();
        }

        return new CustomOauth2UserDetails(member, oAuth2User.getAttributes());
    }
	
	

}
