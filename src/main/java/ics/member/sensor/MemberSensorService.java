package ics.member.sensor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ics.member.sensor.dto.SensorAddProcReqDTO;
import ics.member.sensor.dto.SensorApprovalReqDTO;
import ics.member.sensor.dto.SensorDelProcReqDTO;
import ics.member.sensor.dto.SensorInfoResDTO;
import ics.member.sensor.dto.SensorListReqDTO;
import ics.member.sensor.dto.SensorListResDTO;
import ics.member.sensor.dto.SensorLogReqDTO;
import ics.member.sensor.dto.SensorLogResDTO;
import ics.member.sensor.dto.SensorLogSearchReqDTO;
import ics.member.sensor.dto.SensorLogSearchResDTO;
import ics.member.sensor.dto.SensorModProcReqDTO;
import ics.member.sensor.dto.SensorSearchChartLogReqDTO;
import ics.member.sensor.dto.SensorSearchChartLogResDTO;
import ics.member.sensor.dto.SensorSelectResDTO;
import ics.member.sensor.dto.SensorTypeListResDTO;

@Service
public class MemberSensorService {
	
	@Autowired
	private MemberSensorMapper memberSensorMapper;
	
	/**
	 * 데이터 검색 : 센서 선택 - 사용중, 승인된 센서 목록 조회
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorSelectResDTO> getSensorSelectList(Integer companyIdx) throws Exception {
		return memberSensorMapper.getSensorSelectList(companyIdx);
	}

	/**
	 * 데이터 검색 - 로그 갯수 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorLogListCount(SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception {
		return memberSensorMapper.getSensorLogListCount(sensorLogSearchReqDTO);
	}

	/**
	 * 데이터 검색 - 로그 목록 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 */
	public List<SensorLogSearchResDTO> getSensorLogList(SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception{
		return memberSensorMapper.getSensorLogList(sensorLogSearchReqDTO);
	}

	/**
	 * 데이터 검색 : 검색된 데이터 중 최근 30개
	 * @param sensorSearchChartLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorSearchChartLogResDTO> getSearchChartLog(SensorSearchChartLogReqDTO sensorSearchChartLogReqDTO) throws Exception{
		return memberSensorMapper.getSearchChartLog(sensorSearchChartLogReqDTO);
	}

	/**
	 * 회원사 관리 - 센서 정보, 센서관리 - 센서목록
	 * companyIdx로 센서 조회 - sensor의 use_yn = 'Y'
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorCntOfCompanyIdx(Integer companyIdx) throws Exception {
		return memberSensorMapper.getSensorCntOfCompanyIdx(companyIdx);
	}

	/**
	 * 회원사 관리 - 센서 정보, 센서관리 - 센서목록
	 * companyIdx로 센서 조회 - sensor의 use_yn = 'Y'
	 * @param sensorListReqDTO
	 * @return
	 */
	public List<SensorListResDTO> getSensorOfCompanyIdx(SensorListReqDTO sensorListReqDTO) {
		return memberSensorMapper.getSensorOfCompanyIdx(sensorListReqDTO);
	}
	
	/**
	 * 센서관리 - 로그 갯수 조회
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorLogTodayCount(SensorLogReqDTO sensorLogReqDTO) throws Exception {
		return memberSensorMapper.getSensorLogTodayCount(sensorLogReqDTO);
	}

	/**
	 * 센서관리 - 로그 조회
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogResDTO> getSensorLogToday(SensorLogReqDTO sensorLogReqDTO) throws Exception {
		return memberSensorMapper.getSensorLogToday(sensorLogReqDTO);
	}

	/**
	 * 센서관리 - 로그 차트 조회
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogResDTO> getSensorLogChart(Integer sensorIdx) throws Exception {
		return memberSensorMapper.getSensorLogChart(sensorIdx);
	}

	/**
	 * 센서 타입 목록
	 * @return
	 * @throws Exception
	 */
	public List<SensorTypeListResDTO> getSensorTypeListAll() throws Exception {
		return memberSensorMapper.getSensorTypeListAll();
	}

	/**
	 * 회원사 관리 - 센서 등록
	 * @param sensorAddProcReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void sensorAddProc(SensorAddProcReqDTO sensorAddProcReqDTO) throws Exception {
		memberSensorMapper.sensorAddProc(sensorAddProcReqDTO);
	}

	/**
	 * 회원사 관리 - 센서 정보 조회
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	public SensorInfoResDTO getSensorInfo(Integer sensorIdx) throws Exception {
		return memberSensorMapper.getSensorInfo(sensorIdx);
	}
	
	/**
	 * 회원사 관리 - 센서 수정
	 * @param sensorModProcReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void sensorModProc(SensorModProcReqDTO sensorModProcReqDTO) throws Exception {
		memberSensorMapper.sensorModProc(sensorModProcReqDTO);
	}

	/**
	 * 회원사 관리 - 센서 삭제
	 * @param sensorDelProcReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void sensorDelProc(SensorDelProcReqDTO sensorDelProcReqDTO) throws Exception {
		memberSensorMapper.sensorDelProc(sensorDelProcReqDTO);
	}

	/**
	 * 센서 승인 요청
	 * @param sensorApprovalReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void sensorApproval(SensorApprovalReqDTO sensorApprovalReqDTO) throws Exception{
		memberSensorMapper.sensorApproval(sensorApprovalReqDTO);
		
	}

	
	
}
