package ics.manager.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ics.manager.main.dto.SensorCurrentValueResDTO;
import ics.manager.main.dto.SensorDataResDTO;
import ics.manager.main.dto.SensorInfoListResDTO;
import ics.manager.main.dto.SensorRealLogResDTO;
import ics.manager.main.dto.SensorRealTimeDTO;
import ics.manager.main.dto.SensorRealTimeResDTO;
import ics.manager.main.dto.SensorTypeCntResDTO;
import ics.manager.main.dto.SensorWarnLogResDTO;
import ics.manager.main.dto.SensorWeekDTO;
import ics.manager.main.dto.SensorWeekResDTO;
import ics.manager.main.dto.StatusRateTimeDTO;

@Mapper
public interface ManagerMainMapper {

	/**
	 * 승인된 운영사/회원사의 센서 수
	 * @return
	 * @throws Exception
	 */
	public int getSensorUseApprovalCount() throws Exception;

	/**
	 * 가입한 운영사/회원사의 수
	 * @return
	 * @throws Exception
	 */
	public int getCompanyCount() throws Exception;

	/**
	 * 서비스 수
	 * @return
	 * @throws Exception
	 */
	public int getServiceCount() throws Exception;

	/**
	 * 센서정보 수신상태 : 최근 한 시간동안 데이터를 수신한 센서 수 
	 * @param statusRateTimeDTO 
	 * @return
	 * @throws Exception
	 */
	public int getSensorHourStatusCount(StatusRateTimeDTO statusRateTimeDTO) throws Exception;

	/**
	 * 센서 경고 현황
	 * @return
	 * @throws Exception
	 */
	public List<SensorWarnLogResDTO> getSensorWarnLog(String startDate, String endDate) throws Exception;

	/**
	 * 센서 타입별 센서 로그 수 - 현재일자
	 * @return
	 * @throws Exception
	 */
	public List<SensorTypeCntResDTO> getSensorTypeCnt() throws Exception;

	/**
	 * 센싱 실시간 로그데이터
	 * @return
	 * @throws Exception
	 */
	public List<SensorRealLogResDTO> getSensorLogLimit() throws Exception;

	/**
	 * 센서별 현재데이터
	 * @param sensorType
	 * @return
	 * @throws Exception
	 */
	public List<SensorCurrentValueResDTO> getSensorCurrentValue(Integer sensorType) throws Exception;

	/**
	 * 실시간 센싱현황 - 외부온도, 외부습도, 미세먼지
	 * @param sensorRealTimeDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorRealTimeResDTO> getSensorRealTime(SensorRealTimeDTO sensorRealTimeDTO) throws Exception;

	/**
	 * 함체의 센서 데이터
	 * @param enclosureIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorDataResDTO> getSensorDataOfEnclosure(Integer enclosureIdx) throws Exception;

	/**
	 * 
	 * @param sensorWeekDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorWeekResDTO> getSensorWeek(SensorWeekDTO sensorWeekDTO) throws Exception;

	public List<SensorInfoListResDTO> getSensorInfoList(String sensorId) throws Exception;

	public Integer getUserCount() throws Exception;
	
	

}
