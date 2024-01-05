package ics.operator.main.dto;

import lombok.Data;

/**
 * 함체의 센서 정보 : 함체 클릭
 * @author Koreasoft
 *
 */
@Data
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
