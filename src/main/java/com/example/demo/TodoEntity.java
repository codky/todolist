package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "to_do_entity") // 정확한 테이블 이름 사용
public class TodoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=200)
	private String content;
	
	@Column(nullable = false)
	private Boolean completed;
	
	private String memo;
	
	private LocalDate createDate;
	
	private LocalDate updateDate;
	
	@Column(nullable = false) // order_index는 NOT NULL이므로 기본값 필요
    private Integer orderIndex = 0; // 기본값 0 설정
	
    @Column(nullable = false)
    private Boolean priority = false; // 우선순위(중요 여부)
	
	@ManyToOne
	@JoinColumn(name="author_id") // 명시
	private SiteUser author; // 사용자 한명이 여러개의 엔티티 작성할 수 있기 때문

}
