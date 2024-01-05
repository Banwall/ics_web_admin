package ics.manager.sensor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
}
