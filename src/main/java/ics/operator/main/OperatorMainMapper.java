package ics.operator.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ics.operator.main.dto.FirelogResDTO;
import ics.operator.main.dto.SensorDataResDTO;
import ics.operator.main.dto.SensorDayLogReqDTO;
import ics.operator.main.dto.SensorDayLogResDTO;
import ics.operator.main.dto.SensorInfoListResDTO;
import ics.operator.main.dto.SensorRealLogResDTO;
import ics.operator.main.dto.SensorRealTimeReqDTO;
import ics.operator.main.dto.SensorRealTimeResDTO;
import ics.operator.main.dto.SensorWarnLogResDTO;
import ics.operator.main.dto.StatusCntReqDTO;
import ics.operator.main.dto.ToiletResDTO;
import ics.operator.main.dto.SensorTodayLogResDTO;

@Mapper
public interface OperatorMainMapper {

	/**
	 * 승인된 운영사/회원사의 센서 수
	 * @param companyIdx 
	 * @return
	 * @throws Exception
	 */
	public int getSensorUseApprovalCount(Integer companyIdx) throws Exception;

	/**
	 * 발생한 알람갯수(일)
	 * @param companyIdx 
	 * @return
	 * @throws Exception
	 */
	public int getAlarmLogCount(Integer companyIdx) throws Exception;

	/**
	 * 등록된 알람설정수량
	 * @param companyIdx 
	 * @return
	 * @throws Exception
	 */
	public int getAlarmCount(Integer companyIdx) throws Exception;

	/**
	 * 대시보드 - 센서정보 수신 상태
	 * @param statusCntReqDTO
	 * @return
	 * @throws Exception
	 */
	public int getSensorHourStatusCount(StatusCntReqDTO statusCntReqDTO) throws Exception;
	
	/**
	 * 센서 경고 현황
	 * @param companyIdx 
	 * @return
	 * @throws Exception
	 */
	public List<SensorWarnLogResDTO> getSensorWarnLog(Integer companyIdx) throws Exception;

	/**
	 * 실시간 센싱현황 - 외부온도, 외부습도, 미세먼지 
	 * @param sensorRealTimeReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorRealTimeResDTO> getSensorRealTime(SensorRealTimeReqDTO sensorRealTimeReqDTO) throws Exception;

	/**
	 * 함체의 센서 데이터
	 * @param enclosureIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorDataResDTO> getSensorDataOfEnclosure(Integer enclosureIdx) throws Exception;

	/**
	 * 센싱 실시간 로그데이터	
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorRealLogResDTO> getSensorLogLimit(Integer companyIdx) throws Exception;

	/**
	 * 함체의 센서 정보 : 함체 클릭
	 * @param sensorId
	 * @return
	 * @throws Exception
	 */
	public List<SensorInfoListResDTO> getSensorInfoList(String sensorId) throws Exception;

	/**
	 * 
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<FirelogResDTO> getFireLog(Integer companyIdx) throws Exception;

	/**
	 * 로그 1일 : 지금시각 - 1day
	 * @param sensorDayLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorDayLogResDTO> getSensorDayLog(SensorDayLogReqDTO sensorDayLogReqDTO) throws Exception;

	/**
	 * 로그 1일 : 1일
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorTodayLogResDTO> getSensorTodayLog(Integer companyIdx) throws Exception;

	/**
	 * 화장실의 idx 구하기
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getToiletIdx() throws Exception;

	/**
	 * 화장실의 값 구하기
	 * @param toiletIdx
	 * @return
	 * @throws Exception
	 */
	public List<ToiletResDTO> getToiletValue(Integer toiletIdx) throws Exception;

	public Integer getUserCount() throws Exception;	

}
