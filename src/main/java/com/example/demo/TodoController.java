package com.example.demo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TodoController {
	
	private final TodoService todoService;
	
	
	@RequestMapping("/todo")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		Page<ToDoEntity> paging = this.todoService.getList(page);
		model.addAttribute("paging", paging);
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
    
    
    @DeleteMapping("/todo/delete/{id}")
    public String todoDelete(@PathVariable("id") Integer id) {
    	this.todoService.delete(id);
    	
    	return "redirect:/todo";
    }
    
    @PutMapping("/todo/update/{id}")
    public String todoUpdate(@RequestBody String content, @PathVariable("id") Integer id) {
    	this.todoService.update(id, content);
    	
		return "redirect:/todo";
    	
    }
}
