# Todolist 프로젝트 소개

간편하게 회원가입 후 자신만의 할 일 리스트를 만들고 수정하거나 메모를 추가하거나 즐겨찾기하며 관리

<br>


## 프로젝트 기간
2024.12 ~ 2025.1

<br>

## 사용 기술
  ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)    <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">  <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">   <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">  <br> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">   <img src="https://img.shields.io/badge/oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white"> 

  JPA + Spring Security  + Spring OAuth2 Client

<br>

## 패키지 구조
```
src
┣ main
┃ ┣ java
┃ ┃ ┗ com
┃ ┃   ┗ example
┃ ┃     ┗ demo
┃ ┃       ┣ answer
┃ ┃       ┃ ┣ Answer.java                 // 답변 도메인 엔티티
┃ ┃       ┃ ┣ AnswerController.java       // 답변 관련 컨트롤러
┃ ┃       ┃ ┣ AnswerRepository.java       // 답변 관련 JPA 리포지토리
┃ ┃       ┃ ┗ AnswerService.java          // 답변 관련 서비스
┃ ┃       ┣ question
┃ ┃       ┃ ┣ Question.java               // 질문 도메인 엔티티
┃ ┃       ┃ ┣ QuestionController.java     // 질문 관련 컨트롤러
┃ ┃       ┃ ┣ QuestionRepository.java     // 질문 관련 JPA 리포지토리
┃ ┃       ┃ ┗ QuestionService.java        // 질문 관련 서비스
┃ ┃       ┣ user
┃ ┃       ┃ ┣ CustomOauth2UserService.java // OAuth2 사용자 서비스
┃ ┃       ┃ ┣ CustomUserDetails.java       // 커스텀 사용자 세부 정보
┃ ┃       ┃ ┣ GoogleUserDetails.java       // Google 사용자 세부 정보
┃ ┃       ┃ ┣ KakaoUserDetails.java        // Kakao 사용자 세부 정보
┃ ┃       ┃ ┣ NaverUserDetails.java        // Naver 사용자 세부 정보
┃ ┃       ┃ ┣ OAuth2UserInfo.java          // OAuth2 사용자 정보 인터페이스
┃ ┃       ┃ ┣ SiteUser.java                // 사용자 엔티티
┃ ┃       ┃ ┣ UserController.java          // 사용자 관련 컨트롤러
┃ ┃       ┃ ┣ UserCreateForm.java          // 사용자 생성 폼
┃ ┃       ┃ ┣ UserRepository.java          // 사용자 JPA 리포지토리
┃ ┃       ┃ ┣ UserRole.java                // 사용자 역할 Enum
┃ ┃       ┃ ┣ UserSecurityService.java     // 사용자 인증 서비스
┃ ┃       ┃ ┗ UserService.java             // 사용자 서비스
┃ ┃       ┣ DataNotFoundException.java     // 데이터 없을 때의 예외 처리
┃ ┃       ┣ SecurityConfig.java            // Spring Security 설정
┃ ┃       ┣ TodoController.java            // Todo 관련 컨트롤러
┃ ┃       ┣ TodoEntity.java                // Todo 엔티티
┃ ┃       ┣ TodolistApplication.java       // 메인 애플리케이션
┃ ┃       ┣ TodoRepository.java            // Todo JPA 리포지토리
┃ ┃       ┗ TodoService.java               // Todo 서비스
┃ ┗ resources
┃   ┣ static                              // 정적 리소스 디렉토리
┃   ┃ ┣ bootstrap.min.css                 // 부트스트랩 CSS
┃   ┃ ┣ bootstrap.min.js                  // 부트스트랩 JS
┃   ┃ ┣ index.html                        // 기본 HTML 파일
┃   ┃ ┗ style.css                         // 커스텀 스타일시트
┃   ┣ templates                           // 템플릿 디렉토리 (Thymeleaf 사용)
┃   ┃ ┣ form_errors.html                  // 폼 에러 템플릿
┃   ┃ ┣ index.html                        // 메인 페이지 템플릿
┃   ┃ ┣ layout.html                       // 공통 레이아웃 템플릿
┃   ┃ ┣ login_form.html                   // 로그인 폼 템플릿
┃   ┃ ┣ navbar.html                       // 네비게이션 바 템플릿
┃   ┃ ┣ question_detail.html              // 질문 상세 페이지
┃   ┃ ┣ question_list.html                // 질문 목록 페이지
┃   ┃ ┣ signup_form.html                  // 회원가입 폼 템플릿
┃   ┃ ┣ todolist.html                     // Todo 리스트 페이지
┃   ┃ ┗ todo_detail.html                  // Todo 상세 페이지
┃   ┣ application.properties              // 애플리케이션 기본 설정
┃   ┗ application.properties.example      // 설정 예제 파일
┗ test
  ┗ java
    ┗ com
      ┗ example
        ┗ demo
          ┗ TodolistApplicationTests.java // 테스트 클래스

```

<br>

## 메인
![image](https://github.com/user-attachments/assets/ef47c242-260b-4032-9004-97d1c3fdd28d)

## 로그인 화면
![image](https://github.com/user-attachments/assets/90cbe75b-70f0-4016-9ca6-69db62858557)

## 로그인 이후 작업 페이지로 이동
![image](https://github.com/user-attachments/assets/6c284cd2-fa39-433d-a6f7-bb87c43866e0)

## 메모 및 메모 수정 기능
![image](https://github.com/user-attachments/assets/37c715de-a472-4bbe-83d2-fb9c4e5ebc6b)

## 할일 수정 기능
![image](https://github.com/user-attachments/assets/565362e8-8fcc-4ee2-ae87-b5b773479708)

## 구글 로그인
![image](https://github.com/user-attachments/assets/faa01979-5797-4efc-acbf-f025e470db85)
![image](https://github.com/user-attachments/assets/4a346497-204b-4997-a10f-6ea58ff1455a)

## 네이버 로그인
![image](https://github.com/user-attachments/assets/c8c26814-5e6b-4a39-876e-7460391c2c28)
![image](https://github.com/user-attachments/assets/4a733098-6493-4d1c-9477-235c5a19fb9b)

## 카카오 로그인
![image](https://github.com/user-attachments/assets/9062f4c8-efff-436a-9521-640d98bdd8c0)
![image](https://github.com/user-attachments/assets/cf779ee3-08ac-4ed0-8230-8667092558e7)
![image](https://github.com/user-attachments/assets/b3d67564-71e3-4a11-86ed-cd7e79381018)


## 개발 예정 기능
1. "작업"과 "오늘 할 일"을 분리하여 오늘 할 일에서 완료하지 못한 작업은 "작업"으로 이동
2. "중요" 표시한 작업은 따로 확인
3. 질문 및 답변 게시판 / 댓글 대댓글 기능 추가



