package ics.startuppark.main;

import ics.qst.vo.DeviceList;
import ics.startuppark.main.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StartupParkMainService {

	@Autowired
	private StartupParkMainMapper startupParkMainMapper;
	
	/**
	 * 승인된 운영사/회원사의 센서 수
	 * @return
	 * @throws Exception
	 */
	public int getSensorUseApprovalCount() throws Exception {
		return startupParkMainMapper.getSensorUseApprovalCount();
	}

	/**
	 * 가입한 운영사/회원사의 수
	 * @return
	 * @throws Exception
	 */
	public int getCompanyCount() throws Exception {
		return startupParkMainMapper.getCompanyCount();
	}

	/**
	 * 서비스 수
	 * @return
	 * @throws Exception
	 */
	public int getServiceCount() throws Exception {
		return startupParkMainMapper.getServiceCount();
	}

	/**
	 * 센서정보 수신상태 : 최근 한 시간동안 데이터를 수신한 센서 수
	 * @param statusRateTimeDTO
	 * @return
	 * @throws Exception
	 */
	public int getSensorHourStatusCount(StatusRateTimeDTO statusRateTimeDTO) throws Exception {
		return startupParkMainMapper.getSensorHourStatusCount(statusRateTimeDTO);
	}

	/**
	 * 센서 경고 현황
	 * @return
	 * @throws Exception
	 */
	public List<SensorWarnLogResDTO> getSensorWarnLog() throws Exception {
		LocalDate now = LocalDate.now();

		String startDate = now.toString().concat(" 00:00:00");
		String endDate = now.toString().concat(" 23:59:59");

		return startupParkMainMapper.getSensorWarnLog(startDate, endDate);
	}

	/**
	 * 센서 타입별 센서 로그 수 - 현재일자
	 * @return
	 * @throws Exception
	 */
	public List<SensorTypeCntResDTO> getSensorTypeCnt() throws Exception {
		return startupParkMainMapper.getSensorTypeCnt();
	}

	/**
	 * 센싱 실시간 로그데이터
	 * @return
	 * @throws Exception
	 */
	public List<SensorRealLogResDTO> getSensorLogLimit() throws Exception {
		return startupParkMainMapper.getSensorLogLimit();
	}

	/**
	 * 센서별 현재데이터
	 * @param sensorType
	 * @return
	 */
	public List<SensorCurrentValueResDTO> getSensorCurrentValue(Integer sensorType) throws Exception{
		return startupParkMainMapper.getSensorCurrentValue(sensorType);
	}

	/**
	 * 실시간 센싱현황 - 외부온도, 외부습도, 미세먼지
	 * @param sensorRealTimeDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorRealTimeResDTO> getSensorRealTime(SensorRealTimeDTO sensorRealTimeDTO) throws Exception {
		return startupParkMainMapper.getSensorRealTime(sensorRealTimeDTO);
	} 

	/**
	 * 함체의 센서 데이터
	 * @return
	 * @throws Exception
	 */
	public List<SensorDataResDTO> getSensorDataOfEnclosure() throws Exception {
		return startupParkMainMapper.getSensorDataOfEnclosure();
	}
	
	/**
	 * 함체의 센서 통신 여부
	 * @return
	 * @throws Exception
	 */
	public List<DeviceList> getAliveDataOfEnclosure() throws Exception {
		return startupParkMainMapper.getAliveDataOfEnclosure();
	}

	/**
	 * 
	 * @param sensorWeekDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorWeekResDTO> getSensorWeek(SensorWeekDTO sensorWeekDTO) throws Exception {
		return startupParkMainMapper.getSensorWeek(sensorWeekDTO);
	}

	public List<SensorInfoListResDTO> getSensorInfoList(String sensorId) throws Exception {
		return startupParkMainMapper.getSensorInfoList(sensorId);
	}

	/**
	 * 1일 접속 현황
	 * @return
	 * @throws Exception
	 */
	public Integer getUserCount() throws Exception{
		
		return startupParkMainMapper.getUserCount();
	}

}
