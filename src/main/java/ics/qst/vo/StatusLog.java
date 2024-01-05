package ics.qst.vo;

import lombok.Data;

@Data
public class StatusLog {

	/* 테이블 컬럼 */
	private String code;
	private String value;
	private String cdt;
	private String mdt;
	
	/* 추가 */
	private String codeNm;
	private String grpCd;
	private String grpNm;
	private String ip;
	private String useYn;
	private String viewYn;
}