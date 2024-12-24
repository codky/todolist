package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<ToDoEntity, Integer>{
	
	Page<ToDoEntity> findAll(Pageable pageable);

}
