package ics.api.sensor;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ics.api.sensor.dto.SensorIsIdReqDTO;
import ics.api.sensor.dto.SensorLogAlarmResDTO;
import ics.api.sensor.dto.SensorLogCsvReqDTO;
import ics.api.sensor.dto.SensorLogCsvResDTO;
import ics.api.sensor.dto.SensorTypeAddReqDTO;
import ics.api.sensor.dto.SensorTypeListResDTO;

@Mapper
public interface SensorMapper {

	List<SensorLogCsvResDTO> getSensorLogList(SensorLogCsvReqDTO sensorLogCsvReqDTO) throws Exception;


	/**
	 * 센서 중복 체크 : sensorId + sensorTypeIdx
	 * @param sensorIsIdReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer sensorIsId(SensorIsIdReqDTO sensorIsIdReqDTO) throws Exception;


	/**
	 * 센서 타입 목록 가져오기
	 * @return
	 * @throws Exception
	 */
	List<SensorTypeListResDTO> getSensorTypeList() throws Exception;


	public Integer getIsSensorType(SensorTypeAddReqDTO sensorTypeAddReqDTO) throws Exception;


	public void sensorTypeAdd(SensorTypeAddReqDTO sensorTypeAddReqDTO) throws Exception;
	
	//List<SensorLogAlarmResDTO> getSensorLogAlarm(Integer companyIdx) throws Exception;

	
}
