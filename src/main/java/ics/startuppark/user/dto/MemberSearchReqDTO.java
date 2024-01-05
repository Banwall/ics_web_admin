package ics.startuppark.user.dto;

import lombok.*;

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
