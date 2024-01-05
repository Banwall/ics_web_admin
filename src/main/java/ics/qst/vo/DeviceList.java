package ics.qst.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeviceList {

	/* 테이블 컬럼 */
	private int code;

	private String codeNm;

	private String grpCd;

	private String grpNm;

	private String ip;

	private String useYn;

	private String deviceSpot;

	private String installation;

	private String useYnReason;

	/* 추가 */

	private int timeDiff;

	private String flag;

	private String value;

	private String cdt;

	private String mdt;

	private Integer cpu;

	private String ram;

	private String ramPersent;

	private String hdd;

	private String hddPersent;

	private String sucCnt;

	private String failCnt;

	private String idx;

	private String selfGrpNm;
	
	private List<DeviceList> deviceList = new ArrayList<>();
	private List<DeviceList> chkCodeList = new ArrayList<>();
	private String[] chkList;
}