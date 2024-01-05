package ics.operator.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class OperatorMainService {
	
	@Autowired
	private OperatorMainMapper operatorMainMapper;

	/**
	 * 승인된 운영사/회원사의 센서 수
	 * @param companyIdx 
	 * @return
	 * @throws Exception
	 */
	public int getSensorUseApprovalCount(Integer companyIdx) throws Exception {
		return operatorMainMapper.getSensorUseApprovalCount(companyIdx);
	}

	/**
	 * 발생한 알람갯수(일) 
	 * @param companyIdx 
	 * @return
	 * @throws Exception
	 */
	public int getAlarmLogCount(Integer companyIdx) throws Exception{
		return operatorMainMapper.getAlarmLogCount(companyIdx);
	}

	/**
	 * 등록된 알람설정수량
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public int getAlarmCount(Integer companyIdx) throws Exception {
		return operatorMainMapper.getAlarmCount(companyIdx);
	}

	/**
	 * 대시보드 - 센서정보 수신 상태
	 * @param statusCntReqDTO
	 * @return
	 * @throws Exception
	 */
	public int getSensorHourStatusCount(StatusCntReqDTO statusCntReqDTO) throws Exception {
		return operatorMainMapper.getSensorHourStatusCount(statusCntReqDTO);
	}
	
	/**
	 * 센서 경고 현황
	 * @param companyIdx 
	 * @return
	 * @throws Exception
	 */
	public List<SensorWarnLogResDTO> getSensorWarnLog(Integer companyIdx) throws Exception {
		return operatorMainMapper.getSensorWarnLog(companyIdx);
	}

	/**
	 * 실시간 센싱현황 - 외부온도, 외부습도, 미세먼지 
	 * @param sensorRealTimeReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorRealTimeResDTO> getSensorRealTime(SensorRealTimeReqDTO sensorRealTimeReqDTO) throws Exception {
		return operatorMainMapper.getSensorRealTime(sensorRealTimeReqDTO);
	}

	/**
	 * 함체의 센서 데이터
	 * @param enclosureIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorDataResDTO> getSensorDataOfEnclosure(Integer enclosureIdx) throws Exception{
		return operatorMainMapper.getSensorDataOfEnclosure(enclosureIdx);
	}

	/**
	 * 센싱 실시간 로그데이터	
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorRealLogResDTO> getSensorLogLimit(Integer companyIdx) throws Exception {
		return operatorMainMapper.getSensorLogLimit(companyIdx);
	}

	/**
	 * 함체의 센서 정보 : 함체 클릭
	 * @param sensorId
	 * @return
	 * @throws Exception
	 */
	public List<SensorInfoListResDTO> getSensorInfoList(String sensorId) throws Exception {
		return operatorMainMapper.getSensorInfoList(sensorId);
	}

	/**
	 * 
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<FirelogResDTO> getFireLog(Integer companyIdx) throws Exception {
		return operatorMainMapper.getFireLog(companyIdx);
	}

	
	/**
	 * 로그 1일 : 지금시각 - 1day
	 * @param sensorDayLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorDayLogResDTO> getSensorDayLog(SensorDayLogReqDTO sensorDayLogReqDTO) throws Exception {
		return operatorMainMapper.getSensorDayLog(sensorDayLogReqDTO);
	}

	/**
	 * 로그 1일 : 1일
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorTodayLogResDTO> getSensorTodayLog(Integer companyIdx) throws Exception {
		return operatorMainMapper.getSensorTodayLog(companyIdx);
	}

	/**
	 * 화장실의 idx 구하기
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getToiletIdx() throws Exception{
		return operatorMainMapper.getToiletIdx();
	}

	/**
	 * 화장실의 값 구하기
	 * @param toiletIdx
	 * @return
	 * @throws Exception
	 */
	public List<ToiletResDTO> getToiletValue(Integer toiletIdx) throws Exception{
		return operatorMainMapper.getToiletValue(toiletIdx);
	}

	public Integer getUserCount() throws Exception{
		
		return operatorMainMapper.getUserCount();
	}


}
