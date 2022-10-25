package PorkCutlet.master.controller.dto;

import PorkCutlet.master.domain.User;
import PorkCutlet.master.domain.UserType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class UserInfoDto {
	private Long id;
	private String nickName;

	@Setter(AccessLevel.PRIVATE)
	private boolean admin = false;

	public UserInfoDto(Long id, String nickName) {
		this.id = id;
		this.nickName = nickName;
	}

	public static UserInfoDto from(User user) {
		UserInfoDto userInfoDto = new UserInfoDto(user.getId(), user.getNickName());
		if (user.getUserType() == UserType.ADMIN)
			userInfoDto.setAdmin(true);
		return userInfoDto;
	}

	public boolean isAdmin() {
		return this.admin;
	}
}
