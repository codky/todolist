package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodoService {
	private final TodoRepository todoRepository;
	
	public List<ToDoEntity> getList() {
		return this.todoRepository.findAll();
	}
	
	public void create(String content) {
		ToDoEntity todoEntity = new ToDoEntity();
		todoEntity.setContent(content);
		todoEntity.setCompleted(false);
		this.todoRepository.save(todoEntity);
	}

	@Transactional
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		ToDoEntity todoEntity = todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다. id =" + id));
		
		this.todoRepository.delete(todoEntity);
		
	}

	@Transactional
	public void update(Integer id, String content) {
		// TODO Auto-generated method stub
		ToDoEntity todoEntity = todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다. id =" + id));
		
		todoEntity.setContent(content);
		
		this.todoRepository.save(todoEntity);
		
	}
}
