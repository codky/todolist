package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodolistApplicationTests {

	@Autowired
	private TodoRepository todoRepository;
	
	@Test
	@Disabled
	void testJpa() {
		TodoEntity todo1 = new TodoEntity();
		todo1.setContent("밥먹기");
		todo1.setCompleted(Boolean.TRUE);
		this.todoRepository.save(todo1);
		
		TodoEntity todo2 = new TodoEntity();
		todo2.setContent("todolist 시작하기");
		todo2.setCompleted(Boolean.FALSE);
		this.todoRepository.save(todo2);
	}
	
	@Test
	void testData() {
		for(int i=1; i<=300; i++) {
			TodoEntity todo1 = new TodoEntity();
			todo1.setContent("테스트 데이터입니다. ["+i+"]");
			todo1.setCompleted(Boolean.TRUE);
			this.todoRepository.save(todo1);
		}
	}

}
