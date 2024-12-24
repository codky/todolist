package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TodoController {
	
	private final TodoService todoService;
	
	
	@RequestMapping("/todo")
	public String list(Model model) {
		List<ToDoEntity> toDoEntityList = this.todoService.getList();
		model.addAttribute("toDoEntityList", toDoEntityList);
		return "todoList";
	}

    @RequestMapping("/")
    public String root(){
        return "redirect:/todo";
    }
    
    @PostMapping("/todo/create")
    public String todoCreate(@RequestParam("content") String content) {
    	// Todo :  아이템 삽입
    	this.todoService.create(content);
    	
    	// 다시 원래 화면으로 리다이렉트
    	return "redirect:/todo";
    }
}
