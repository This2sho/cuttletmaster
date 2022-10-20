package PorkCutlet.master.controller.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class UserUpdateDto {
    @NotEmpty(message = "현재 비밀번호를 입력해주세요.")
    private String password;
    private String newPassword;
    private String newPasswordCheck;
    private String nickName;
}
