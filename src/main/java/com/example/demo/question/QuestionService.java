package com.example.demo.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	public List<Question> getList() {
		return questionRepository.findAll();
	}

	public Question getQuestion(Integer id) {
		Optional<Question> oq = questionRepository.findById(id);
		if(oq.isPresent()) {
			return oq.get();
		} else {
			throw new DataNotFoundException("question not found"); 
		}
	}
	
	public void create(String subject, String content) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		questionRepository.save(question);
	}
}
