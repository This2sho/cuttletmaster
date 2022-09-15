package PorkCutlet.master.controller;

import PorkCutlet.master.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class UserDto {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickName;

    public static UserDto from(User user) {
        return new UserDto(user.getLoginId(), user.getPassword(), user.getNickName());
    }
}
