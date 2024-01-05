package ics.api.sensor;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ics.api.sensor.dto.SensorIsIdReqDTO;
import ics.api.sensor.dto.SensorLogCsvReqDTO;
import ics.api.sensor.dto.SensorLogCsvResDTO;
import ics.api.sensor.dto.SensorTypeAddReqDTO;
import ics.api.sensor.dto.SensorTypeListResDTO;

@Service
public class SensorService {

	@Autowired
	private SensorMapper sensorMapper;
	
	public List<SensorLogCsvResDTO> getSensorLogList(SensorLogCsvReqDTO sensorLogCsvReqDTO) throws Exception {
		return sensorMapper.getSensorLogList(sensorLogCsvReqDTO);
	}

	/**
	 * 센서 중복 체크 : sensorId + sensorTypeIdx
	 * @param sensorIsIdReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer sensorIsId(SensorIsIdReqDTO sensorIsIdReqDTO) throws Exception {		
		return sensorMapper.sensorIsId(sensorIsIdReqDTO);
	}

	/**
	 * 센서 타입 목록 가져오기
	 * @return
	 * @throws Exception
	 */
	public List<SensorTypeListResDTO> getSensorTypeList() throws Exception {
		return sensorMapper.getSensorTypeList();
	}

	/**
	 * 센서 타입 중복 확인 - 갯수 조회
	 * @param sensorTypeAddReqDTO
	 * @return
	 */
	public Integer getIsSensorType(SensorTypeAddReqDTO sensorTypeAddReqDTO) throws Exception {
		return sensorMapper.getIsSensorType(sensorTypeAddReqDTO);
	}

	/**
	 * 센서 타입 등록
	 * @param sensorTypeAddReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void sensorTypeAdd(SensorTypeAddReqDTO sensorTypeAddReqDTO) throws Exception {
		sensorMapper.sensorTypeAdd(sensorTypeAddReqDTO);
	}

	
	
	/*
	public List<SensorLogAlarmResDTO> getSensorLogAlarm(Integer companyIdx) throws Exception {
		
		return sensorMapper.getSensorLogAlarm(companyIdx);
	}
	*/
}
