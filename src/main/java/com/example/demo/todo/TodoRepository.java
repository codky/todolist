package com.example.demo.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.user.SiteUser;

public interface TodoRepository extends JpaRepository<TodoEntity, Integer>{
	
	Page<TodoEntity> findAll(Pageable pageable);
	
	Page<TodoEntity> findByAuthor(SiteUser author, Pageable pageable); // 사용자별 데이터를 가져오는 쿼리
}
