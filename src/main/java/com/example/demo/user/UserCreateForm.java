package com.example.demo.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
	
	@Size(min = 3, max = 25, message = "사용자 ID는 3자 이상, 25자 이하로 입력해야 합니다.") 
	@NotEmpty(message = "사용자ID는 필수항목입니다.")
	private String loginId;
	
	@Size(min = 3, max = 25, message = "별명은 3자 이상, 25자 이하로 입력해야 합니다.") 
	@NotEmpty(message = "별명은 필수항목입니다.")
	private String nickname;
	
	@Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.") 
	 @NotEmpty(message = "비밀번호는 필수항목입니다.")
	private String password1;
	
	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
	private String password2;
	
	@Email(message = "유효한 이메일 주소를 입력해야 합니다.")
    @NotEmpty(message = "이메일은 필수항목입니다.")
	private String email;
}
