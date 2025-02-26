package com.example.demo.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.answer.Answer;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	private Specification<Question> search(String kw) {
		return new Specification<>() {
			
			//q: Root 자료형으로 기준이되는 Question 엔티티의 객체를 의미하며 질문 제목과 내용을 검색하기 위해 필요하다.
			//u1: Question 엔티티와 SiteUser 엔티티를 아우터 조인하여 만든 SiteUser 엔티티의 객체. author 속성으로 연결되어 있다.(q.join("author"))
			//a: Question 엔티티와 Answer 엔티티를 아우터 조인하여 만든 Answer 엔티티의 객체. answerList 속성으로 연결(q.join("answerList"))
			//u2: a 객체와 SiteUser 엔티티와 아우터 조인하여 만든 SiteUser 엔티티의 객체. 답변 작성자를 검색할 때 사용
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), //제목
						cb.like(q.get("content"), "%" + kw + "%"),		//내용
						cb.like(u1.get("nickname"), "%" + kw + "%"),	//질문 작성자
						cb.like(a.get("content"), "%" + kw + "%"),		//답변 내용
						cb.like(u2.get("nickname"), "%" + kw + "%"));	//답변 작성자
			}
		};
	}
	
	public List<Question> getList() {
		return questionRepository.findAll();
	}
	
	public Page<Question> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Question> spec = search(kw);
		return questionRepository.findAll(spec, pageable);
	}

	public Question getQuestion(Integer id) {
		Optional<Question> oq = questionRepository.findById(id);
		if(oq.isPresent()) {
			return oq.get();
		} else {
			throw new DataNotFoundException("question not found"); 
		}
	}
	
	public void create(String subject, String content, SiteUser user) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		question.setAuthor(user);
		questionRepository.save(question);
	}

	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		questionRepository.save(question);
	}
	
	public void delete(Question question) {
		questionRepository.delete(question);
	}
	
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}

}
