package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.foreign.Linker.Option;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerRepository;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;
import com.example.demo.question.QuestionService;
import com.example.demo.todo.TodoEntity;
import com.example.demo.todo.TodoRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class TodolistApplicationTests {

	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@Test
	@Disabled
	void testJpa() {
		TodoEntity todo1 = new TodoEntity();
		todo1.setContent("밥먹기");
		todo1.setCompleted(Boolean.TRUE);
		this.todoRepository.save(todo1);
		
		TodoEntity todo2 = new TodoEntity();
		todo2.setContent("todolist 시작하기");
		todo2.setCompleted(Boolean.FALSE);
		this.todoRepository.save(todo2);
	}
	
	@Test
	@Disabled
	void testData() {
		for(int i=1; i<=300; i++) {
			TodoEntity todo1 = new TodoEntity();
			todo1.setContent("테스트 데이터입니다. ["+i+"]");
			todo1.setCompleted(Boolean.TRUE);
			this.todoRepository.save(todo1);
		}
	}
	
	@Test
	void testBoardData() {
		for (int i = 1; i<= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용없음";
			questionService.create(subject, content, null);
		}
	}
	
    @Test
    @Disabled
    void testJpa2() {        
        Question q1 = new Question();
        q1.setSubject("개발실력 어떻게 늘리나요?");
        q1.setContent("조언을 듣고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장
    }
    
    @Test
    @Disabled
    void testJpa3() {        
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("개발실력 어떻게 늘리나요?", q.getSubject());
    }
    
    @Test
    @Disabled
    void testJpa4() {
    	Optional<Question> oq = this.questionRepository.findById(2);
    	if(oq.isPresent()) {
    		Question q = oq.get();
    		assertEquals("스프링부트 모델 질문입니다.", q.getSubject());
    	}
    }
    
    @Test
    @Disabled
    void testJpa5() {
    	Question q = this.questionRepository.findBySubject("개발실력 어떻게 늘리나요?");
    	assertEquals(1, q.getId());
    }
    
    @Test
    @Disabled
    void testSubecjtAndContent() {
    	Question q = this.questionRepository.findBySubjectAndContent("개발실력 어떻게 늘리나요?", "조언을 듣고 싶습니다.");
    	assertEquals(1, q.getId());
    }
    
    @Test
    @Disabled
    void testSubjectLike() {
    	 List<Question> qList = this.questionRepository.findBySubjectLike("%개발%");
    	 Question q = qList.get(0);
         assertEquals("개발실력 어떻게 늘리나요?", q.getSubject());
    }
    
    @Test
    @Disabled
    void testUpdate() {
    	Optional<Question> oq = this.questionRepository.findById(1);
    	assertTrue(oq.isPresent());
    	Question q = oq.get();
    	q.setSubject("수정된 제목");
    	this.questionRepository.save(q);
    }
    
    @Test
    @Disabled
    void testDelete() {
    	assertEquals(2, this.questionRepository.count());
    	Optional<Question> oq = this.questionRepository.findById(1);
    	assertTrue(oq.isPresent());
    	Question q = oq.get();
    	this.questionRepository.delete(q);
    	assertEquals(1, this.questionRepository.count());
    }
    
    @Test
    @Disabled
    void createAnswer() {
    	Optional<Question> oq = this.questionRepository.findById(6);
    	assertTrue(oq.isPresent());
    	Question q = oq.get();
    	
    	Answer a = new Answer();
    	a.setContent("네 자동으로 생성됩니다.");
    	a.setQuestion(q); // 어떤 질문의 답변인지 알기 위해서 Question 객체 필요.
    	a.setCreateDate(LocalDateTime.now());
    	this.answerRepository.save(a);
    }
    
    @Test
    @Disabled
    void selectAnswer() {
    	Optional<Answer> oa = this.answerRepository.findById(1);
    	assertTrue(oa.isPresent());
    	Answer a = oa.get();
    	assertEquals(6, a.getQuestion().getId());
    }
    
    @Transactional
    @Test
    @Disabled
    void checkQeustion() {
    	Optional<Question> oq = this.questionRepository.findById(6);
    	assertTrue(oq.isPresent());
    	Question q = oq.get();
    	
    	List<Answer> answerList = q.getAnswerList();
    	assertEquals(1, answerList.size());
    	assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }

}
