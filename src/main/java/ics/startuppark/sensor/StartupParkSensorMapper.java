package ics.startuppark.sensor;

import ics.startuppark.sensor.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StartupParkSensorMapper {

	/**
	 * 함체 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<EnclosureListDTO> getEnclosureListAll() throws Exception;

	/**
	 * 미세먼지, 온도, 습도만 있는 함체 조회
	 * @return
	 */
	public List<EnclosureListDTO> getEnclosureList() throws Exception;

	/**
	 * 미세먼지, 온도, 습도의 센서 타입 조회
	 * @return
	 * @throws Exception
	 */
	public List<SensorTypeListDTO> getSensorTypeInTempHumiDust() throws Exception;

	/**
	 * 전체 센서 타입 조회
	 * @return
	 * @throws Exception
	 */
	public List<SensorTypeListDTO> getSensorTypeListAll() throws Exception;

	/**
	 * 회원의 idx로 센서 갯수 조회 : companyIdx
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorCntOfCompanyIdx(Integer companyIdx) throws Exception;
	
	/**
	 * 회원사의 idx로 센서 조회
	 * @param sensorListReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorListResDTO> getSensorOfCompanyIdx(SensorListReqDTO sensorListReqDTO) throws Exception;

	/**
	 * 센서 로그 수 조회(조회일자-금일)
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorLogTodayCount(SensorLogReqDTO sensorLogReqDTO) throws Exception;

	/**
	 * 1일 센서 조회 - 센서 목록
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogResDTO> getSensorLogToday(SensorLogReqDTO sensorLogReqDTO) throws Exception;

	/**
	 * 센서 로그 차트 
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogResDTO> getSensorLogChart(Integer sensorIdx) throws Exception;

	/**
	 * 센서 조회 : SELECT
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<SensorSelectResDTO> getSensorSelectList(Integer companyIdx) throws Exception;

	/**
	 * 로그 갯수 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getSensorLogListCount(SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception;

	/**
	 * 로그 수 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<SensorLogSearchResDTO> getSensorLogList(SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception;

	/**
	 * 센서 데이터 검색 : 검색된 데이터 중 최근 30개
	 * @param sensorSearchChartLogReqDTO
	 * @return
	 */
	public List<SensorSearchChartLogResDTO> getSearchChartLog(SensorSearchChartLogReqDTO sensorSearchChartLogReqDTO);

	/**
	 * 센서 승인 / 미승인 요청
	 * @param sensorApprovalReqDTO
	 * @throws Exception
	 */
	public void approvalProc(SensorApprovalReqDTO sensorApprovalReqDTO) throws Exception;

}
