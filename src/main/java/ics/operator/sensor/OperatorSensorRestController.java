package ics.operator.sensor;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ics.core.util.PageUtil;
import ics.core.util.SessionUtil;
import ics.core.util.StringUtil;
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
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/operator/sensor")
@Slf4j
public class OperatorSensorRestController {
	
	@Autowired
	private OperatorSensorService operatorSensorService;
	
	@GetMapping(value = "/listOfCompany")
	@ResponseBody
	public ResponseEntity sensorOfCompanyIdx(@ModelAttribute SensorListReqDTO sensorListReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();

		Integer companyIdx = SessionUtil.getCompanyIdx();
		//	회원의 idx로 센서 갯수 조회 : companyIdx
		Integer sensorCount = operatorSensorService.getSensorCntOfCompanyIdx(companyIdx);
		PageUtil pageInfo = new PageUtil(sensorListReqDTO.getNowPage(), sensorCount, sensorListReqDTO.getListCount(), sensorListReqDTO.getPageGroup());
		sensorListReqDTO.setStartNum(pageInfo.getStartNum());
		sensorListReqDTO.setEndNum(pageInfo.getEndNum());
		sensorListReqDTO.setCompanyIdx(companyIdx);
		
		//	회원사의 idx로 센서 조회
		List<SensorListResDTO> sensorList = operatorSensorService.getSensorOfCompanyIdx(sensorListReqDTO);
		
		dataMap.put("sensorList", sensorList);
		dataMap.put("page", pageInfo.getPageInfo());
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 센서 - 센서 상세 정보 - 로그조회
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/logOfSensorIdx")
	@ResponseBody
	public ResponseEntity logOfSensorIdx(@ModelAttribute SensorLogReqDTO sensorLogReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		sensorLogReqDTO.setStartDate(StringUtil.getTodayDash());
		
		//	1일 센서 조회 (조회일자) - 센서 로그 수 
		Integer logCount = operatorSensorService.getSensorLogTodayCount(sensorLogReqDTO);
		PageUtil pageInfo = new PageUtil(sensorLogReqDTO.getNowPage(), logCount, sensorLogReqDTO.getListCount(), sensorLogReqDTO.getPageGroup());
		sensorLogReqDTO.setStartNum(pageInfo.getStartNum());
		sensorLogReqDTO.setEndNum(pageInfo.getEndNum());
		
		//	1일 센서 로그 조회
		List<SensorLogResDTO> sensorLogList = operatorSensorService.getSensorLogToday(sensorLogReqDTO);

		dataMap.put("sensorLogList", sensorLogList);
		dataMap.put("page", pageInfo.getPageInfo());
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		 
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 센서관리 : 차트용 센서 로그 조회 30
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/logChart")
	@ResponseBody
	public ResponseEntity logOfSensorIdx(@RequestParam Integer sensorIdx) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
				
		//	차트용 센서 로그 조회
		List<SensorLogResDTO> sensorLogChart = operatorSensorService.getSensorLogChart(sensorIdx);
		dataMap.put("sensorLogChart", sensorLogChart);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 데이터 : 설치 위치에 따른 센서 목록 조회 
	 * @param stationIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/select")
	@ResponseBody
	public ResponseEntity getSensorSelect(@RequestParam Integer stationIdx) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
						
		//	센서 셀렉트
		List<SensorSelectResDTO> sensorSelectList = operatorSensorService.getSensorSelectList(stationIdx);
		dataMap.put("sensorSelectList", sensorSelectList);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	

	/**
	 * 데이터 검색 : 로그 전체 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/logList")
	@ResponseBody
	public ResponseEntity getSensorLogList(@ModelAttribute SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		//	로그 갯수 조회
		Integer logCount = operatorSensorService.getSensorLogListCount(sensorLogSearchReqDTO);
		
		PageUtil pageInfo = new PageUtil(sensorLogSearchReqDTO.getNowPage(), logCount, sensorLogSearchReqDTO.getListCount(), sensorLogSearchReqDTO.getPageGroup());
		sensorLogSearchReqDTO.setStartNum(pageInfo.getStartNum());
		sensorLogSearchReqDTO.setEndNum(pageInfo.getEndNum());
		
		//	로그 조회
		List<SensorLogSearchResDTO> sensorLogList = operatorSensorService.getSensorLogList(sensorLogSearchReqDTO);
		dataMap.put("page", pageInfo.getPageInfo());
		dataMap.put("sensorLogList", sensorLogList);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 센서 데이터 검색 : 검색된 데이터 중 최근 30개
	 * @param sensorSearchChartLogReqDTO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/searchChartLog")
	@ResponseBody
	public ResponseEntity getSearchChartLog(@ModelAttribute SensorSearchChartLogReqDTO sensorSearchChartLogReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		List<SensorSearchChartLogResDTO> sensorLogChart = operatorSensorService.getSearchChartLog(sensorSearchChartLogReqDTO);
		
		dataMap.put("sensorLogChart", sensorLogChart);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 센서 알람설정 정보 조회
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/alarmInfo")
	@ResponseBody
	public ResponseEntity getSensorAlarmInfo(@RequestParam Integer sensorIdx) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		SensorAlarmInfoResDTO dto = operatorSensorService.getSensorAlarmInfo(sensorIdx);
		dataMap.put("alarmInfo", dto);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 센서관리 - 센서 알람 설정"
	 * @param sensorAlarmProcReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/alarmProc")
	@ResponseBody
	public ResponseEntity alarmProc(@RequestBody SensorAlarmProcReqDTO sensorAlarmProcReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		sensorAlarmProcReqDTO.setModifier(SessionUtil.getIdx());
		log.info("센서관리 - 센서 알람 설정");
		log.info(sensorAlarmProcReqDTO.toString());
		operatorSensorService.alarmProc(sensorAlarmProcReqDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
}
