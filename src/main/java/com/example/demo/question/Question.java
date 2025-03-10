package com.example.demo.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.example.demo.answer.Answer;
import com.example.demo.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // 스프링부트가 Quetion 클래스를 엔티티로 인식
public class Question {
	@Id // 기본키 중복X
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 1씩 증가하여 저장, 고유한 번호
	private Integer id; // 고유번호
	
	@Column(length = 200)
	private String subject; // 제목
	
	@Column(length = 4000) // 최대 4000자로 제한
	private String content; // 내용
	
	private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) 
    private List<Answer> answerList; 
	// Answer 객체들로 구성된 answerList를 Question 엔티티의 속성으로 추가하고 @OneToMany 애너테이션을 설정했다. 
	// 이제 질문에서 답변을 참조하려면 question.getAnswerList()를 호출하면 된다. 
	// @OneToMany 애너테이션에 사용된 mappedBy는 참조 엔티티의 속성명을 정의한다. 
	// 즉, Answer 엔티티에서 Question 엔티티를 참조한 속성인 question을 mappedBy에 전달해야 한다.
	// cascade = CascadeType.REMOVE) => 답변이 달린 질문이 삭제되면 답변도 삭제 시킨다.
	
	@ManyToOne //사용자 한명이 여러 질문을 작성할 수 있으므로 @ManyToOne 어노테이션 적용
	private SiteUser author;
	
	@ManyToMany
	Set<SiteUser> voter;
	
}
