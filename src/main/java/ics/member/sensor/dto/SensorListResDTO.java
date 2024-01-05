package ics.member.sensor.dto;

import lombok.Data;

/**
 * 센서 목록
 *
 */
@Data
public class SensorListResDTO {

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
	
}
