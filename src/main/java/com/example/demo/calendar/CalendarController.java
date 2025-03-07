package com.example.demo.calendar;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.todo.TodoEntity;
import com.example.demo.todo.TodoRequest;
import com.example.demo.todo.TodoService;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CalendarController {
	
	private final TodoService todoService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/calendar/view")
	public String list(Model model, Principal principal) {
		return "calendar_view";
	}

    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/todos")
    public ResponseEntity<List<Map<String, Object>>> getTodosForCalendar(Principal principal) {
    	SiteUser user = userService.getUser(principal.getName());
    	List<TodoEntity> todos = todoService.getAllTodosForUser(user);
    	List<Map<String, Object>> events = new ArrayList<>();
    	for (TodoEntity todo : todos) {
    		Map<String, Object> event = new HashMap<>();
    		event.put("id", todo.getId());
    		event.put("title", todo.getContent());
    		//할 일의 생성일을 시작 날짜로 사용하고, 하루 이벤트로 처리
    		event.put("start", todo.getCreateDate().toString());
    		event.put("end", todo.getCreateDate().plusDays(1).toString());
    		//완료 여부에 따라 색상 지정
    		event.put("color", todo.getCompleted() ? "#28a745" : "#dc3545");
    		events.add(event);
    	}
    	return ResponseEntity.ok(events);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/todos")
    public ResponseEntity<TodoEntity> createTodoFromCalendar(@RequestBody TodoRequest todoRequest, Principal principal) {
    	SiteUser siteUser = userService.getUser(principal.getName());
        TodoEntity todo = new TodoEntity();
        todo.setContent(todoRequest.getTitle());
        // 날짜는 ISO 형식("yyyy-MM-dd")로 전달된다고 가정
        todo.setCreateDate(LocalDate.parse(todoRequest.getStart()));
        todo.setCompleted(false);
        todo.setAuthor(siteUser);
        TodoEntity saved = todoService.save(todo);
        return ResponseEntity.ok(saved);
    }
}
