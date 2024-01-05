package ics.member.sensor;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ics.member.sensor.dto.SensorSelectResDTO;
import ics.member.sensor.dto.SensorTypeListResDTO;
import ics.member.sensor.dto.SensorLogReqDTO;
import ics.member.sensor.dto.SensorLogResDTO;
import ics.api.sensor.dto.SensorIsIdReqDTO;
import ics.member.sensor.dto.SensorAddProcReqDTO;
import ics.member.sensor.dto.SensorApprovalReqDTO;
import ics.member.sensor.dto.SensorDelProcReqDTO;
import ics.member.sensor.dto.SensorInfoResDTO;
import ics.member.sensor.dto.SensorListReqDTO;
import ics.member.sensor.dto.SensorListResDTO;
import ics.member.sensor.dto.SensorLogSearchReqDTO;
import ics.member.sensor.dto.SensorLogSearchResDTO;
import ics.member.sensor.dto.SensorModProcReqDTO;
import ics.member.sensor.dto.SensorSearchChartLogReqDTO;
import ics.member.sensor.dto.SensorSearchChartLogResDTO;

@Mapper
public interface MemberSensorMapper {

	/**
	 * 데이터 검색 : 센서 선택 - 사용중, 승인된 센서 목록 조회
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorSelectResDTO> getSensorSelectList(Integer companyIdx) throws Exception;
	
	/**
	 * 데이터 검색 : 로그 갯수 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorLogListCount(SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception;

	/**
	 * 데이터 검색 : 로그 목록 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogSearchResDTO> getSensorLogList(SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception;

	/**
	 * 데이터 검색 : 검색된 데이터 중 최근 30개 
	 * @param sensorSearchChartLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorSearchChartLogResDTO> getSearchChartLog(SensorSearchChartLogReqDTO sensorSearchChartLogReqDTO) throws Exception;

	/**
	 * 회원사 관리 - 센서 정보, 센서관리 - 센서목록
	 * companyIdx로 센서 조회 - sensor의 use_yn = 'Y'
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorCntOfCompanyIdx(Integer companyIdx) throws Exception;

	/**
	 * 회원사 관리 - 센서 정보, 센서관리 - 센서목록
	 * companyIdx로 센서 조회 - sensor의 use_yn = 'Y'
	 * @param sensorListReqDTO
	 * @return
	 */
	public List<SensorListResDTO> getSensorOfCompanyIdx(SensorListReqDTO sensorListReqDTO);

	/**
	 * 센서관리 - 로그 갯수 조회
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorLogTodayCount(SensorLogReqDTO sensorLogReqDTO) throws Exception;

	/**
	 * 센서관리 - 로그 조회
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogResDTO> getSensorLogToday(SensorLogReqDTO sensorLogReqDTO) throws Exception;

	/**
	 * 센서관리 - 차트 로그 조회
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogResDTO> getSensorLogChart(Integer sensorIdx) throws Exception;

	/**
	 * 센서 타입 목록
	 * @return
	 * @throws Exception
	 */
	public List<SensorTypeListResDTO> getSensorTypeListAll() throws Exception;

	/**
	 * 회원사관리 - 센서정보 - 센서 등록
	 * @param sensorAddProcReqDTO
	 * @throws Exception
	 */
	public void sensorAddProc(SensorAddProcReqDTO sensorAddProcReqDTO) throws Exception;

	/**
	 * 회원사 관리 - 센서 정보 조회
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	public SensorInfoResDTO getSensorInfo(Integer sensorIdx) throws Exception;

	/**
	 * 회원사 관리 - 센서 수정
	 * @param sensorModProcReqDTO
	 * @throws Exception
	 */
	public void sensorModProc(SensorModProcReqDTO sensorModProcReqDTO) throws Exception;

	/**
	 * 회원사 관리 - 센서 삭제
	 * @param sensorDelProcReqDTO
	 * @throws Exception
	 */
	public void sensorDelProc(SensorDelProcReqDTO sensorDelProcReqDTO) throws Exception;

	/**
	 * 센서 승인 요청
	 * @param sensorApprovalReqDTO
	 * @throws Exception
	 */
	public void sensorApproval(SensorApprovalReqDTO sensorApprovalReqDTO) throws Exception;

}
