package PorkCutlet.master.controller.dto;

import lombok.Data;


@Data
public class UserUpdateDto {
    private String password;
    private String passwordCheck;
    private String nickName;
}
