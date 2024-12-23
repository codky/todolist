package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodoService {
	private final TodoRepository todoRepository;
	
	public List<ToDoEntity> getList() {
		return this.todoRepository.findAll();
	}

}
