package com.example.demo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.example.demo.user.SiteUser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodoService {
	private final TodoRepository todoRepository;
	
	public Page<TodoEntity> getList(SiteUser author, int page) {
	    System.out.println("Author ID: " + author.getId());
	    System.out.println("Author Username: " + author.getUsername());
	    Pageable pageable = PageRequest.of(page, 10);
	    Page<TodoEntity> result = this.todoRepository.findByAuthor(author, pageable);
	    System.out.println("Result Size: " + result.getContent().size());
	    return result;
	}
	
	public void create(String content, SiteUser author) {
		TodoEntity todoEntity = new TodoEntity();
		todoEntity.setContent(content);
		todoEntity.setCompleted(false);
		todoEntity.setAuthor(author);
		todoEntity.setCreateDate(LocalDate.now());
		this.todoRepository.save(todoEntity);
	}

	@Transactional
	public void delete(Integer id, SiteUser author) {
		// TODO Auto-generated method stub
		TodoEntity todoEntity = todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다. id =" + id));
		
		this.todoRepository.delete(todoEntity);
		
	}

	@Transactional
	public void update(Integer id, String content, SiteUser author) {
		// TODO Auto-generated method stub
		TodoEntity todoEntity = todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다. id =" + id));
		
		todoEntity.setUpdateDate(LocalDate.now());
		todoEntity.setContent(content);
		
		this.todoRepository.save(todoEntity);
	}
	
	public TodoEntity getTodoItem(Integer id) {
		Optional<TodoEntity> todoItem = this.todoRepository.findById(id);
		if (todoItem.isPresent()) {
			return todoItem.get();
		} else {
			throw new DataNotFoundException("TodoItem not found");
		}
	}
	
	@Transactional
	public void updateMemo(Integer id, String memo, SiteUser author) {
		// TODO Auto-generated method stub
		TodoEntity todoEntity = todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다. id =" + id));
		
		todoEntity.setUpdateDate(LocalDate.now());
		todoEntity.setMemo(memo);
		
		this.todoRepository.save(todoEntity);
	}
	
	@Transactional
	public void updateComplete(Integer id) {
		// TODO Auto-generated method stub
		TodoEntity todoEntity = todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다. id =" + id));
		
		Boolean completed = false;
		completed = todoEntity.getCompleted() ? false : true;
		
		todoEntity.setUpdateDate(LocalDate.now());
		todoEntity.setCompleted(completed);
		
		this.todoRepository.save(todoEntity);
	}
	
	public TodoEntity getTodoItemForUser(Integer id, SiteUser user) {
		TodoEntity todoItem = this.todoRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Todo Item not found"));
		
		// 현재 로그인한 사용자가 소유자인지 확인
		if (!todoItem.getAuthor().getId().equals(user.getId())) {
			throw new AccessDeniedException("You are not authorized to access this resource");
		}
		
		return todoItem;
	}

    public void updateOrder(List<Map<String, Integer>> updatedOrder) {
        for (Map<String, Integer> item : updatedOrder) {
            Integer id = item.get("id");
            Integer orderIndex = item.get("orderIndex");

            TodoEntity todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
            
            todo.setOrderIndex(orderIndex);
            todoRepository.save(todo); // 순서 업데이트
        }
    }

    public void togglePriority(TodoEntity todoItem) {
        todoItem.setPriority(!todoItem.getPriority()); // 현재 상태를 반대로 변경
        todoItem.setUpdateDate(LocalDate.now()); // 수정 날짜 갱신
        this.todoRepository.save(todoItem); // 저장
    }
}
