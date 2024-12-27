package com.example.demo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {

	Optional<SiteUser> findByusername(String username);
	Optional<SiteUser> findById(Long id); // 수정: Long 타입으로 변경
}
