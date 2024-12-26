package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ToDoEntity {
	
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
	
	@ManyToOne
	private SiteUser author; // 사용자 한명이 여러개의 엔티티 작성할 수 있기 때문

}
