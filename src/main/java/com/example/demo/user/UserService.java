package com.example.demo.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder; // SecurityConfig에서 빈으로 등록한 Password Encoder객체를 주입받아 사용
	
	public SiteUser create(String loginId, String nickname, String email, String password) {
		SiteUser user = new SiteUser();
		user.setLoginId(loginId);
		user.setNickname(nickname);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(UserRole.USER);
		this.userRepository.save(user);
		return user;
	}
	
	public SiteUser getUser(String loginId) {
        Optional<SiteUser> siteUser = this.userRepository.findByLoginId(loginId);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}
