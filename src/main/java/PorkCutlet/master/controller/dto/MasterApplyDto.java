package PorkCutlet.master.controller.dto;

import javax.validation.constraints.NotEmpty;

import PorkCutlet.master.domain.MasterApply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterApplyDto {
	private Long id;
	private String userNickName;

	@NotEmpty(message = "내용을 입력해주세요.")
	private String content;

	public static MasterApplyDto from(MasterApply masterApply) {
		return new MasterApplyDto(masterApply.getId(), masterApply.getUser().getNickName(), masterApply.getContent());
	}
}
