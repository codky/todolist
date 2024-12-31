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
        // OAuth2User 정보 로드
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes: " + oAuth2User.getAttributes());

        // 소셜 로그인 제공자 정보 가져오기
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(provider, oAuth2User);

        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + oAuth2UserInfo.getProviderId();

        // 사용자 검색 및 처리
        SiteUser user = processUser(email, provider, oAuth2UserInfo, loginId);

        // CustomUserDetails 반환
        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }

    private OAuth2UserInfo getOAuth2UserInfo(String provider, OAuth2User oAuth2User) {
        return switch (provider) {
            case "google" -> new GoogleUserDetails(oAuth2User.getAttributes());
            case "naver" -> new NaverUserDetails(oAuth2User.getAttributes());
            case "kakao" -> new KakaoUserDetails(oAuth2User.getAttributes());
            default -> throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다.");
        };
    }

    private SiteUser processUser(String email, String provider, OAuth2UserInfo oAuth2UserInfo, String loginId) {
        // 이메일과 제공자로 사용자 검색
        Optional<SiteUser> optionalUser = userRepository.findByEmailAndProvider(email, provider);

        if (optionalUser.isPresent()) {
            // 기존 사용자 처리
            SiteUser user = optionalUser.get();
            updateProviderInfoIfNeeded(user, provider, oAuth2UserInfo);
            return user;
        } else {
            // 새 사용자 생성
            return createNewUser(email, provider, oAuth2UserInfo, loginId);
        }
    }

    private void updateProviderInfoIfNeeded(SiteUser user, String provider, OAuth2UserInfo oAuth2UserInfo) {
        // 소셜 제공자 ID 업데이트
        if (!user.getProviderId().equals(oAuth2UserInfo.getProviderId())) {
            user.setProviderId(oAuth2UserInfo.getProviderId());
            userRepository.save(user);
        }
    }

    private SiteUser createNewUser(String email, String provider, OAuth2UserInfo oAuth2UserInfo, String loginId) {
        SiteUser newUser = SiteUser.builder()
                .loginId(loginId)
                .nickname(oAuth2UserInfo.getName())
                .email(email)
                .provider(provider)
                .providerId(oAuth2UserInfo.getProviderId())
                .role(UserRole.USER)
                .build();
        return userRepository.save(newUser);
    }
}