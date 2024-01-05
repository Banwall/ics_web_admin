package ics.startuppark.sensor.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 데이터 검색 : 로그 전체 조회
 * @author Koreasoft
 *
 */
@Getter
@Setter
public class SensorLogSearchResDTO {
	
	private String	sensorLogValue;
	private Integer	sensorIdx;
	private String	sensorLogCreateDate;
	private String	sensorLocationDescription;
	private String	sensorTypeName;
	private String	sensorTypeCode;
	private String	sensorId;
	private String	companyName;
	private String	sensorApprovalDate;

}
