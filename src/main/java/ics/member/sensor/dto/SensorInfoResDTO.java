package ics.member.sensor.dto;

import lombok.Data;

@Data
/**
 * 센서 정보 조회
 * @author Koreasoft
 *
 */
public class SensorInfoResDTO {

	private Integer sensorIdx;
	private String	sensorCreateDate;
	private String	sensorId;
	private String	sensorLocationDescription;
	private String	sensorReceiveDate;
	private String	sensorValue;
	private String	sensorRequestApprovalDate;
	private String	sensorApprovalStatus;
	private String	sensorApprovalDate;
	private String	sensorTypeName;
	private String	sensorTypeCode;
	private Integer	sensorTypeIdx;
	private String	sensorStationIdx;
	private String	sensorStationName;
	private Integer sensorThreshold;
	
	
}
