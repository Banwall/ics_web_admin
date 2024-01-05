package ics.startuppark.main.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 센서 함체 안의 센서 정보
 * @author Koreasoft
 *
 */
@Getter
@Setter
@ToString
public class SensorInfoListResDTO {
	
	private String	sensorApprovalDate;
	private String	sensorId;
	private String	sensorTypeName;
	private String	sensorTypeCode;
	private String	sensorLocationDescription;
	private String	companyName;
	private String	companyRole;
	private String	requestApprovalDate;
	
}

