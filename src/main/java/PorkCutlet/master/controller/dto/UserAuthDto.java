package PorkCutlet.master.controller.dto;

import PorkCutlet.master.domain.User;
import PorkCutlet.master.domain.UserType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Data
public class UserAuthDto {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    private String passwordCheck;
    @NotEmpty
    private String nickName;

    public UserAuthDto(String loginId, String password, String nickName) {
        this.loginId = loginId;
        this.password = password;
        this.nickName = nickName;
    }

    public static UserAuthDto from(User user) {
        return new UserAuthDto(user.getLoginId(), user.getPassword(), user.getNickName());
    }
}
