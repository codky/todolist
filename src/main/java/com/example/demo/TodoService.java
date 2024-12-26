package com.example.demo;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.user.SiteUser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodoService {
	private final TodoRepository todoRepository;
	
	public Page<ToDoEntity> getList(int page) {
		Pageable pageable = PageRequest.of(page, 10);
		
		return this.todoRepository.findAll(pageable);
	}
	
	public void create(String content, SiteUser author) {
		ToDoEntity todoEntity = new ToDoEntity();
		todoEntity.setContent(content);
		todoEntity.setCompleted(false);
		todoEntity.setAuthor(author);
		todoEntity.setCreateDate(LocalDate.now());
		this.todoRepository.save(todoEntity);
	}

	@Transactional
	public void delete(Integer id, SiteUser author) {
		// TODO Auto-generated method stub
		ToDoEntity todoEntity = todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다. id =" + id));
		
		this.todoRepository.delete(todoEntity);
		
	}

	@Transactional
	public void update(Integer id, String content, SiteUser author) {
		// TODO Auto-generated method stub
		ToDoEntity todoEntity = todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다. id =" + id));
		
		todoEntity.setUpdateDate(LocalDate.now());
		todoEntity.setContent(content);
		
		this.todoRepository.save(todoEntity);
		
	}
}
