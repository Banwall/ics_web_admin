package ics.manager.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchReqDTO {

	private Integer startNum;
	private Integer endNum;
	private Integer nowPage;
	private Integer listCount;
	private Integer pageGroup;
	
	private Integer companyIdx;
	
}
