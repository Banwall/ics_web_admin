package ics.manager.main;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ics.core.util.StringUtil;
import ics.manager.main.dto.StatusCntResDTO;
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
import ics.manager.main.dto.TodayResDTO;

@RestController
@RequestMapping("/manager/main")
public class ManagerMainRestController {
	
	@Autowired
	private ManagerMainService managerMainService;

	/**
	 * today
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/today")
	@ResponseBody
	public HashMap todayInfo() throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();

		int sensorCount = managerMainService.getSensorUseApprovalCount();
		int companyCount = managerMainService.getCompanyCount();
		int serviceCount = managerMainService.getServiceCount();
		String today = StringUtil.getTodayDash();
		
		TodayResDTO dto = TodayResDTO
				.builder()
				.sensorCount(sensorCount)
				.companyCount(companyCount)
				.serviceCount(serviceCount)
				.today(today)
				.build();

		resultMap.put("result", result);
		resultMap.put("data", dto);
		return resultMap;
	}
	
	/**
	 * 센서정보 수신상태
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value ="/statusCnt")
	@ResponseBody
	public HashMap statusRate()throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		
		HashMap timeMap = StringUtil.getBeforeOneHour();
		StatusRateTimeDTO statusRateTimeDTO = StatusRateTimeDTO.builder()
				.startTime(String.valueOf(timeMap.get("startTime")))
				.endTime(String.valueOf(timeMap.get("endTime")))
				.build();
		
		int sensorTotalCount = managerMainService.getSensorUseApprovalCount();
		int sensorStatusCount = managerMainService.getSensorHourStatusCount(statusRateTimeDTO);
		Integer userCount = managerMainService.getUserCount();
		
		StatusCntResDTO dto = 
				StatusCntResDTO
				.builder()
				.sensorStatusCount(sensorStatusCount)
				.sensorTotalCount(sensorTotalCount)
				.userCount(userCount)		//	1일접속현황
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
		
		List<SensorWarnLogResDTO> list = managerMainService.getSensorWarnLog();
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
		//	2	외부 온도	OUTER_TEMPERATURE
		//	4	외부 습도	OUTER_HUMIDITY
		//	5	미세먼지	MICRO_DUST
		
		//	외부 온도 조회
		SensorRealTimeDTO tempDTO = SensorRealTimeDTO.builder()
				.enclosureIdx(enclosureIdx)
				.sensorTypeIdx(2)
				.build();
		List <SensorRealTimeResDTO> tempList = managerMainService.getSensorRealTime(tempDTO);
		
		//	외부 습도 조회
		SensorRealTimeDTO humidityDTO = SensorRealTimeDTO.builder()
				.enclosureIdx(enclosureIdx)
				.sensorTypeIdx(4)
				.build();
		List <SensorRealTimeResDTO> humidityList = managerMainService.getSensorRealTime(humidityDTO);
		
		//	미세먼지 조회
		SensorRealTimeDTO dustDTO = SensorRealTimeDTO.builder()
				.enclosureIdx(enclosureIdx)
				.sensorTypeIdx(5)
				.build();
		List <SensorRealTimeResDTO> dustList = managerMainService.getSensorRealTime(dustDTO);

		// 로봇 1호기 배터리 조회
		SensorRealTimeDTO bmsDTO = SensorRealTimeDTO.builder()
				.enclosureIdx(enclosureIdx)
				.sensorTypeIdx(18)
				.build();
		List <SensorRealTimeResDTO> bmsList = managerMainService.getSensorRealTime(bmsDTO);
		
		dataMap.put("dustList", dustList);
		dataMap.put("tempList", tempList);
		dataMap.put("humidityList", humidityList);
		dataMap.put("bmsList", bmsList);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 센서별 현재데이터	: 미세먼지, 외부온도, 외부습도
	 * @param enclosureIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/sensorCurrentValue")
	@ResponseBody
	public HashMap sensorCurrentValue(@RequestParam(value="sensorType") Integer sensorType) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		List <SensorCurrentValueResDTO> list = managerMainService.getSensorCurrentValue(sensorType);
		dataMap.put("sensorCurrList", list);
		dataMap.put("sensorTypeIdx", sensorType);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 센서별 데이터 수집율 : 센서 타입별 센서 로그 수 - 현재일자
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/sensorTypeCnt")
	@ResponseBody
	public HashMap sensorTodayRate() throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		List <SensorTypeCntResDTO> list = managerMainService.getSensorTypeCnt();
		dataMap.put("typeLogCntList", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	
	//	요일별 평균 데이터
	@GetMapping(value = "sensorWeek")
	@ResponseBody
	public HashMap sensorWeek() throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();	
		
		HashMap dayMap = StringUtil.getMonToSun();
		
		//	외부 온도 조회
		SensorWeekDTO tempDTO = SensorWeekDTO.builder()
				.startDay(String.valueOf(dayMap.get("startDay")))
				.endDay(String.valueOf(dayMap.get("endDay")))
				.sensorTypeIdx(2)
				.build();
		List <SensorWeekResDTO> tempList = managerMainService.getSensorWeek(tempDTO);
			
		//	외부 습도 조회
		SensorWeekDTO humidityDTO = SensorWeekDTO.builder()
				.startDay(String.valueOf(dayMap.get("startDay")))
				.endDay(String.valueOf(dayMap.get("endDay")))
				.sensorTypeIdx(4)
				.build();
		List <SensorWeekResDTO> humidityList = managerMainService.getSensorWeek(humidityDTO);
			
		//	미세먼지 조회
		SensorWeekDTO dustDTO = SensorWeekDTO.builder()
				.startDay(String.valueOf(dayMap.get("startDay")))
				.endDay(String.valueOf(dayMap.get("endDay")))
				.sensorTypeIdx(5)
				.build();
		List <SensorWeekResDTO> dustList = managerMainService.getSensorWeek(dustDTO);
		
		dataMap.put("tempList",tempList);
		dataMap.put("humidityList",humidityList);
		dataMap.put("dustList",dustList);
		dataMap.put("day",dayMap);
		resultMap.put("data", dataMap);
		resultMap.put("result", result);
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
		//	1 경고(임계치 초과), 2 일반
		List <SensorRealLogResDTO> list = managerMainService.getSensorLogLimit();
		dataMap.put("realLogList", list);
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
		List<SensorDataResDTO> list = managerMainService.getSensorDataOfEnclosure(enclosureIdx);
		dataMap.put("sensorDataList", list);
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
			@RequestParam(value="dataType")String dataType) throws Exception {
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		List<SensorInfoListResDTO> list = managerMainService.getSensorInfoList(sensorId);
		
		dataMap.put("dataType", dataType);
		dataMap.put("sensorInfoList", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
}
