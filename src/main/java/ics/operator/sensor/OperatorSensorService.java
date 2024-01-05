package ics.operator.sensor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ics.operator.sensor.dto.EnclosureListDTO;
import ics.operator.sensor.dto.SensorAlarmInfoResDTO;
import ics.operator.sensor.dto.SensorAlarmProcReqDTO;
import ics.operator.sensor.dto.SensorListReqDTO;
import ics.operator.sensor.dto.SensorListResDTO;
import ics.operator.sensor.dto.SensorLogReqDTO;
import ics.operator.sensor.dto.SensorLogResDTO;
import ics.operator.sensor.dto.SensorLogSearchReqDTO;
import ics.operator.sensor.dto.SensorLogSearchResDTO;
import ics.operator.sensor.dto.SensorSearchChartLogReqDTO;
import ics.operator.sensor.dto.SensorSearchChartLogResDTO;
import ics.operator.sensor.dto.SensorSelectResDTO;

@Service
public class OperatorSensorService {
	
	@Autowired
	private OperatorSensorMapper operatorSensorMapper;

	/**
	 * 함체 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<EnclosureListDTO> getEnclosureListAll() throws Exception {
		return operatorSensorMapper.getEnclosureListAll();
	}

	/**
	 * 미세먼지, 온도, 습도만 있는 함체 조회
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<EnclosureListDTO> getEnclosureList(Integer companyIdx) throws Exception {
		return operatorSensorMapper.getEnclosureList(companyIdx);
	}

	/**
	 * 회원사의 idx로 센서 조회
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorCntOfCompanyIdx(Integer companyIdx) throws Exception {
		return operatorSensorMapper.getSensorCntOfCompanyIdx(companyIdx);
	}

	/**
	 * 회원사의 idx로 센서 조회
	 * @param sensorListReqDTO
	 * @return
	 */
	public List<SensorListResDTO> getSensorOfCompanyIdx(SensorListReqDTO sensorListReqDTO) {
		return operatorSensorMapper.getSensorOfCompanyIdx(sensorListReqDTO);
	}

	/**
	 * 로그 갯수 조회
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorLogTodayCount(SensorLogReqDTO sensorLogReqDTO) throws Exception {
		return operatorSensorMapper.getSensorLogTodayCount(sensorLogReqDTO);
	}

	/**
	 * 로그 조회
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogResDTO> getSensorLogToday(SensorLogReqDTO sensorLogReqDTO) throws Exception {
		return operatorSensorMapper.getSensorLogToday(sensorLogReqDTO);
	}

	/**
	 * 
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogResDTO> getSensorLogChart(Integer sensorIdx) throws Exception {
		return operatorSensorMapper.getSensorLogChart(sensorIdx);
	}

	/**
	 * 데이터 : 설치 위치에 따른 센서 목록 조회 : stationIdx 로 조회 
	 * 센서 선택 - 사용중, 승인된 센서 목록 조회
	 * @param stationIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorSelectResDTO> getSensorSelectList(Integer stationIdx) throws Exception {
		return operatorSensorMapper.getSensorSelectList(stationIdx);
	}

	/**
	 * 로그 갯수 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorLogListCount(SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception {
		return operatorSensorMapper.getSensorLogListCount(sensorLogSearchReqDTO);
	}

	/**
	 * 로그 목록 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 */
	public List<SensorLogSearchResDTO> getSensorLogList(SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception{
		return operatorSensorMapper.getSensorLogList(sensorLogSearchReqDTO);
	}

	/**
	 * 센서 데이터 검색 : 검색된 데이터 중 최근 30개
	 * @param sensorSearchChartLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorSearchChartLogResDTO> getSearchChartLog(SensorSearchChartLogReqDTO sensorSearchChartLogReqDTO) throws Exception{
		return operatorSensorMapper.getSearchChartLog(sensorSearchChartLogReqDTO);
	}

	
	/**
	 * 센서 알람설정 정보 조회
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	public SensorAlarmInfoResDTO getSensorAlarmInfo(Integer sensorIdx) throws Exception {
		return operatorSensorMapper.getSensorAlarmInfo(sensorIdx);
	}

	/**
	 * 센서관리 - 센서 알람 설정
	 * @param sensorAlarmProcReqDTO
	 * @throws Exception
	 */
	public void alarmProc(SensorAlarmProcReqDTO sensorAlarmProcReqDTO) throws Exception {
		operatorSensorMapper.alarmProc(sensorAlarmProcReqDTO);
	}

}
