package com.example.demo;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.SiteUser;
import com.example.demo.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TodoController {
	
	private final TodoService todoService;
	private final UserService userService;
	
	@RequestMapping("/todo")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		Page<ToDoEntity> paging = this.todoService.getList(page);
		LocalDate date = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		String formattedDateTime =  date.format(formatter);
		
		String dayOfWeekInKorea = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		
		model.addAttribute("paging", paging);
		model.addAttribute("formattedDateTime", formattedDateTime);
		model.addAttribute("dayOfWeekInKorea", dayOfWeekInKorea);
		return "todoList";
	}

    @RequestMapping("/")
    public String root(){
        return "redirect:/todo";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/todo/create")
    public String todoCreate(@RequestParam("content") String content, Principal principal) {
    	
    	SiteUser siteUser = this.userService.getUser(principal.getName());
    	
    	// Todo :  아이템 삽입
    	this.todoService.create(content, siteUser);
    	
    	// 다시 원래 화면으로 리다이렉트
    	return "redirect:/todo";
    }
    
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/todo/delete/{id}")
    public String todoDelete(@PathVariable("id") Integer id, Principal principal) {
    	
    	SiteUser siteUser = this.userService.getUser(principal.getName());
    	
    	this.todoService.delete(id, siteUser);
    	
    	return "redirect:/todo";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/todo/update/{id}")
    public String todoUpdate(@RequestBody String content, @PathVariable("id") Integer id, Principal principal) {
    	
    	SiteUser siteUser = this.userService.getUser(principal.getName());
    	
    	this.todoService.update(id, content, siteUser);
    	
		return "redirect:/todo";
    }
    
    @GetMapping(value = "/todo/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
    	
    	ToDoEntity todoItem = this.todoService.getTodoItem(id);
    	model.addAttribute("todoItem", todoItem);
    	
    	return "todo_detail";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/todo/detail/memo/{id}")
    public String memoUpdate(@RequestBody String memo, @PathVariable("id") Integer id, Principal principal) {
    	
    	SiteUser siteUser = this.userService.getUser(principal.getName());
    	
    	this.todoService.updateMemo(id, memo, siteUser);
    	
		return "redirect:/todo";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/todo/complete/{id}")
    public String todoComplete(@PathVariable("id") Integer id, Principal principal) {
    	
    	ToDoEntity todoItem = this.todoService.getTodoItem(id);
    	
    	this.todoService.updateComplete(id);
    	
		return "redirect:/todo";
    }
}
