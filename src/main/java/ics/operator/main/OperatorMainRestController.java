package ics.operator.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ics.core.util.SessionUtil;
import ics.core.util.StringUtil;
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
import ics.operator.main.dto.StatusCntResDTO;
import ics.operator.main.dto.TodayResDTO;
import ics.operator.main.dto.ToiletResDTO;
import ics.operator.main.dto.SensorTodayLogResDTO;

@RestController
@RequestMapping("/operator/main")
public class OperatorMainRestController {
	
	@Autowired
	private OperatorMainService operatorMainService;
	
	/**
	 * 대시보드 - 현재일자가표시되며, 현재기준 등록된 센서의 총 수량, 발생한 알람갯수(일) 등록된 알람설정수량
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/today")
	@ResponseBody
	public HashMap todayInfo() throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		Integer companyIdx = SessionUtil.getCompanyIdx();

		int sensorCount = operatorMainService.getSensorUseApprovalCount(companyIdx);
		int alarmLogCount = operatorMainService.getAlarmLogCount(companyIdx);
		int alarmCount = operatorMainService.getAlarmCount(companyIdx);
		
		TodayResDTO dto = TodayResDTO.builder()
				.sensorCount(sensorCount)
				.alarmLogCount(alarmLogCount)
				.alarmCount(alarmCount)
				.today(StringUtil.getTodayDash())
				.build();

		resultMap.put("data", dto);
		resultMap.put("result", result);
		
		return resultMap;
	}
	
	/**
	 * 대시보드 - 센서정보 수신상태
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value ="/statusCnt")
	@ResponseBody
	public HashMap statusRate()throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		
		Integer companyIdx = SessionUtil.getCompanyIdx();
		
		HashMap timeMap = StringUtil.getBeforeOneHour();
		StatusCntReqDTO statusCntReqDTO = StatusCntReqDTO.builder()
				.startTime(String.valueOf(timeMap.get("startTime")))
				.endTime(String.valueOf(timeMap.get("endTime")))
				.companyIdx(companyIdx)
				.build();
		
		int sensorTotalCount = operatorMainService.getSensorUseApprovalCount(companyIdx);
		int sensorStatusCount = operatorMainService.getSensorHourStatusCount(statusCntReqDTO);
		Integer userCount = operatorMainService.getUserCount();
		StatusCntResDTO dto = 
				StatusCntResDTO
				.builder()
				.sensorStatusCount(sensorStatusCount)
				.sensorTotalCount(sensorTotalCount)
				.userCount(userCount)
				.build();

		resultMap.put("result", result);
		resultMap.put("data", dto);
		
		return resultMap;
	}
	
	/**
	 * 센서 경고 현황
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value ="/sensorWarnLog")
	@ResponseBody
	public HashMap sensorWarnLog() throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		Integer companyIdx = SessionUtil.getCompanyIdx();
		
		List<SensorWarnLogResDTO> list = operatorMainService.getSensorWarnLog(companyIdx);
		dataMap.put("warnLogList", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 실시간 센싱현황 - 외부온도, 외부습도, 미세먼지 
	 * @param enclosureIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/sensorRealTime")
	@ResponseBody
	public HashMap sensorRealTime(@RequestParam(value="enclosureIdx")Integer enclosureIdx) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		Integer companyIdx = SessionUtil.getCompanyIdx();
		
		//	2	외부 온도	OUTER_TEMPERATURE
		//	4	외부 습도	OUTER_HUMIDITY
		//	5	미세먼지	MICRO_DUST
		
		//	외부 온도 조회
		SensorRealTimeReqDTO tempDTO = SensorRealTimeReqDTO.builder()
				.enclosureIdx(enclosureIdx)
				.sensorTypeIdx(2)
				.companyIdx(companyIdx)
				.build();
		List <SensorRealTimeResDTO> tempList = operatorMainService.getSensorRealTime(tempDTO);
		
		//	외부 습도 조회
		SensorRealTimeReqDTO humidityDTO = SensorRealTimeReqDTO.builder()
				.enclosureIdx(enclosureIdx)
				.sensorTypeIdx(4)
				.companyIdx(companyIdx)
				.build();
		List <SensorRealTimeResDTO> humidityList = operatorMainService.getSensorRealTime(humidityDTO);
		
		//	미세먼지 조회
		SensorRealTimeReqDTO dustDTO = SensorRealTimeReqDTO.builder()
				.enclosureIdx(enclosureIdx)
				.sensorTypeIdx(5)
				.companyIdx(companyIdx)
				.build();
		List <SensorRealTimeResDTO> dustList = operatorMainService.getSensorRealTime(dustDTO);
		
		dataMap.put("dustList", dustList);
		dataMap.put("tempList", tempList);
		dataMap.put("humidityList", humidityList);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
		
	/**
	 * 함체의 센서 데이터
	 * @param enclosureIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/sensorData")
	@ResponseBody
	public HashMap sensorData(@RequestParam(value="enclosureIdx") Integer enclosureIdx) throws Exception {
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		List<SensorDataResDTO> list = operatorMainService.getSensorDataOfEnclosure(enclosureIdx);
		dataMap.put("sensorDataList", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 센싱 실시간 로그데이터	
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/sensorRealLog")
	@ResponseBody
	public HashMap sensorRealLog() throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		Integer companyIdx = SessionUtil.getCompanyIdx();
		
		//	1 경고(임계치 초과), 2 일반
		List <SensorRealLogResDTO> list = operatorMainService.getSensorLogLimit(companyIdx);
		dataMap.put("realLogList", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}	
	
	/**
	 * 함체의 센서 정보 : 함체 클릭
	 * @param sensorId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/sensorInfoList")
	@ResponseBody
	public HashMap sensorInfoList(
			@RequestParam(value="sensorId") String sensorId,
			@RequestParam(value="dataType") String dataType) throws Exception {
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		List<SensorInfoListResDTO> list = operatorMainService.getSensorInfoList(sensorId);
		
		dataMap.put("dataType", dataType);
		dataMap.put("sensorInfoList", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}	
	
	/**
	 * 알람발생 로그(1일)
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/fireLog")
	@ResponseBody
	public HashMap fireLog() throws Exception {
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();

		Integer companyIdx = SessionUtil.getCompanyIdx();

		List<FirelogResDTO> list = operatorMainService.getFireLog(companyIdx);
		dataMap.put("sensorLog", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 로그 1일 : 지금시각 - 1day
	 * @return
	 * @throws Exception
	 */
	
	/*
	 * @GetMapping(value = "/sensorDayLog")
	 * 
	 * @ResponseBody public HashMap getSensorDayLog() throws Exception { boolean
	 * result = true; HashMap resultMap = new HashMap(); HashMap dataMap = new
	 * HashMap();
	 * 
	 * Integer companyIdx = SessionUtil.getCompanyIdx(); HashMap m =
	 * StringUtil.getTodayAndYesterDay(); SensorDayLogReqDTO sensorDayLogReqDTO =
	 * SensorDayLogReqDTO.builder() .startDay(String.valueOf(m.get("startDay")))
	 * .endDay(String.valueOf(m.get("endDay"))) .companyIdx(companyIdx) .build();
	 * 
	 * List<SensorDayLogResDTO> list =
	 * operatorMainService.getSensorDayLog(sensorDayLogReqDTO);
	 * dataMap.put("sensorLog", list); resultMap.put("result", result);
	 * resultMap.put("data", dataMap); return resultMap; }
	 */
	 
	/**
	 * 로그 1일 : 1일
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/sensorTodayLog")
	@ResponseBody
	public HashMap getSensorTodayLog() throws Exception {
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();

		Integer companyIdx = SessionUtil.getCompanyIdx();

		List<SensorTodayLogResDTO> list = operatorMainService.getSensorTodayLog(companyIdx);
		dataMap.put("sensorLog", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 화장실 센서 값
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/sensorToilet")
	@ResponseBody
	public ResponseEntity getSensorToilet() throws Exception {
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		List<Integer> idxList =  operatorMainService.getToiletIdx();
		
		List list = new ArrayList();
		for(int i=0; i<idxList.size(); i++) {
			Integer toiletIdx = idxList.get(i);
			List<ToiletResDTO> toList = operatorMainService.getToiletValue(toiletIdx);
			list.add(toList);
		}
		
		dataMap.put("list", list);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}
