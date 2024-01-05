package ics.operator.sensor.dto;

import lombok.Data;

@Data
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
