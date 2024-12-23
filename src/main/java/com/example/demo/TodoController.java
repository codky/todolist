package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TodoController {
	
	private final TodoRepository todoRepository;
	
	@RequestMapping("/todo")
	public String list(Model model) {
		List<ToDoEntity> toDoEntityList = this.todoRepository.findAll();
		model.addAttribute("toDoEntityList", toDoEntityList);
		return "todoList";
	}

    @RequestMapping("/")
    public String root(){
        return "redirect:/todo";
    }
}
