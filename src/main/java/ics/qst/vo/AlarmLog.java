package ics.qst.vo;

import lombok.Data;

@Data
public class AlarmLog {

	/* 테이블 컬럼 */
	private int alarmIdx;
	private int code;
	private String codeNm;
	private String status;
	private String alarmFlag;
	private String cdt;
	
	/* 추가 */
	private String startDate;
	private String endDate;
}