package PorkCutlet.master.controller.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserUpdateDto {
	@NotEmpty(message = "현재 비밀번호를 입력해주세요.")
	private String password;
	private String newPassword;
	private String newPasswordCheck;
	private String nickName;
}
