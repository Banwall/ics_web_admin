package ics.qst.vo;

import lombok.Data;

@Data
public class Board {

	/* 테이블 컬럼 */
	private int idx;
	private String title;
	private String comment;
	private String writer;
	private String history;
	private String cdt;
	private String mdt;

	/* 추가 */
	private String flag;
	private int nextIdx;
	private String nextTitle;
	private int prevIdx;
	private String prevTitle;
}