package com.example.demo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {

	//Optional<SiteUser> findByusername(String username);
	Optional<SiteUser> findByEmail(String email);
	Optional<SiteUser> findByLoginId(String loginId);
	Optional<SiteUser> findByEmailAndProvider(String email, String provider);
}
