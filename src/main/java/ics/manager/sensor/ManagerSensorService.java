package ics.manager.sensor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ics.manager.sensor.dto.EnclosureListDTO;
import ics.manager.sensor.dto.SensorApprovalReqDTO;
import ics.manager.sensor.dto.SensorListReqDTO;
import ics.manager.sensor.dto.SensorListResDTO;
import ics.manager.sensor.dto.SensorLogReqDTO;
import ics.manager.sensor.dto.SensorLogResDTO;
import ics.manager.sensor.dto.SensorLogSearchReqDTO;
import ics.manager.sensor.dto.SensorLogSearchResDTO;
import ics.manager.sensor.dto.SensorSearchChartLogReqDTO;
import ics.manager.sensor.dto.SensorSearchChartLogResDTO;
import ics.manager.sensor.dto.SensorSelectResDTO;
import ics.manager.sensor.dto.SensorTypeListDTO;

@Service
public class ManagerSensorService {

	@Autowired
	private ManagerSensorMapper manSensorMapper;
	
	/**
	 * 함체 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<EnclosureListDTO> getEnclosureListAll() throws Exception {
		return manSensorMapper.getEnclosureListAll();
	}

	/**
	 * 미세먼지, 온도, 습도만 있는 함체 조회
	 * @return
	 * @throws Exception
	 */
	public List<EnclosureListDTO> getEnclosureList() throws Exception {
		return manSensorMapper.getEnclosureList();
	}

	/**
	 * 미세먼지, 온도, 습도의 센서 타입 조회
	 * @return
	 * @throws Exception
	 */
	public List<SensorTypeListDTO> getSensorTypeInTempHumiDust() throws Exception {
		return manSensorMapper.getSensorTypeInTempHumiDust();
	}
	
	/**
	 * 전체 센서 타입 조회
	 * @return
	 * @throws Exception
	 */
	public List<SensorTypeListDTO> getSensorTypeListAll() throws Exception {
		return manSensorMapper.getSensorTypeListAll();
	}
	
	/**
	 * 회원의 idx로 센서 갯수 조회 : companyIdx
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorCntOfCompanyIdx(Integer companyIdx) throws Exception{
		return manSensorMapper.getSensorCntOfCompanyIdx(companyIdx);
	}

	/**
	 * 회원사의 idx로 센서 조회
	 * @param sensorListReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorListResDTO> getSensorOfCompanyIdx(SensorListReqDTO sensorListReqDTO) throws Exception{
		List<SensorListResDTO> list = manSensorMapper.getSensorOfCompanyIdx(sensorListReqDTO);
		return list;
	}

	/**
	 * 1일 센서 조회 (조회일자) - 센서 로그 수
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorLogTodayCount(SensorLogReqDTO sensorLogReqDTO) throws Exception{
		return manSensorMapper.getSensorLogTodayCount(sensorLogReqDTO);
	}

	/**
	 * 1일 센서 조회 - 센서 목록
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogResDTO> getSensorLogToday(SensorLogReqDTO sensorLogReqDTO) throws Exception {
		return manSensorMapper.getSensorLogToday(sensorLogReqDTO);
	}

	/**
	 * 센서 로그 차트 
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogResDTO> getSensorLogChart(Integer sensorIdx) throws Exception {
		return manSensorMapper.getSensorLogChart(sensorIdx);
	}

	/**
	 * 센서 조회 : SELECT
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorSelectResDTO> getSensorSelectList(Integer companyIdx) throws Exception {
		return manSensorMapper.getSensorSelectList(companyIdx);
	}

	/**
	 * 로그 갯수 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorLogListCount(SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception{
		return manSensorMapper.getSensorLogListCount(sensorLogSearchReqDTO);
	}

	/**
	 * 로그 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogSearchResDTO> getSensorLogList(SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception {
		return manSensorMapper.getSensorLogList(sensorLogSearchReqDTO);
	}

	/**
	 * 센서 데이터 검색 : 검색된 데이터 중 최근 30개
	 * @param sensorSearchChartLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorSearchChartLogResDTO> getSearchChartLog(SensorSearchChartLogReqDTO sensorSearchChartLogReqDTO) throws Exception{
		return manSensorMapper.getSearchChartLog(sensorSearchChartLogReqDTO);
	}

	/**
	 * 센서 승인 / 미승인 요청
	 * @param sensorApprovalReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void approvalProc(SensorApprovalReqDTO sensorApprovalReqDTO) throws Exception {
		manSensorMapper.approvalProc(sensorApprovalReqDTO);
		
	}
}
