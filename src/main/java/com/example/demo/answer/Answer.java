package com.example.demo.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.demo.question.Question;
import com.example.demo.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 4000) // 최대 4000자로 제한
    private String content;

    private LocalDateTime createDate;
    
    private LocalDateTime modifyDate;

    @ManyToOne // 하나의 질문에 답변은 여러개 달릴 수 있다. 답변은 Many 질문은 One. N:1 -> 실제데이터베이스에서는 외래키(foreign key)관계가 생성된다.
    private Question question; 
    
    @ManyToOne
	private SiteUser author;
    
    @ManyToMany
	Set<SiteUser> voter;
}
