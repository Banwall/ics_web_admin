package ics.api.member.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberIsIdReqDTO {

	@NotBlank(message="MEMBER_ID_BLANK")
	private String memberId;

}
