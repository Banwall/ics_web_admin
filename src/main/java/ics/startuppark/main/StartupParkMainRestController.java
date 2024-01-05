package ics.startuppark.main;

import ics.core.util.StringUtil;
import ics.qst.vo.DeviceList;
import ics.startuppark.main.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/startupPark/main")
public class StartupParkMainRestController {
	
	@Autowired
	private StartupParkMainService startupParkMainService;

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

		int sensorCount = startupParkMainService.getSensorUseApprovalCount();
		int companyCount = startupParkMainService.getCompanyCount();
		int serviceCount = startupParkMainService.getServiceCount();
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

		int sensorTotalCount = startupParkMainService.getSensorUseApprovalCount();
		int sensorStatusCount = startupParkMainService.getSensorHourStatusCount(statusRateTimeDTO);
		Integer userCount = startupParkMainService.getUserCount();
		
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
		
		List<SensorWarnLogResDTO> list = startupParkMainService.getSensorWarnLog();
		dataMap.put("warnLogList", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 실시간 센싱현황 - 온도, 습도
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/sensorRealTime")
	@ResponseBody
	public HashMap sensorRealTime(@RequestParam(value="sensorIdx")Integer sensorIdx) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		//	1	내부 온도	INNER_TEMPERATURE
		//	3	내부 습도	INNER_HUMIDITY

		// PLOE 1~4번 센서를 찾을 수 있는 값이 필요함

		//	온도 조회
		SensorRealTimeDTO tempDTO = SensorRealTimeDTO.builder()
				.sensorIdx(sensorIdx)
				.sensorTypeIdx(1)
				.build();
		List <SensorRealTimeResDTO> tempList = startupParkMainService.getSensorRealTime(tempDTO);
		
		//	습도 조회
		SensorRealTimeDTO humidityDTO = SensorRealTimeDTO.builder()
				.sensorIdx(sensorIdx)
				.sensorTypeIdx(3)
				.build();
		List <SensorRealTimeResDTO> humidityList = startupParkMainService.getSensorRealTime(humidityDTO);
		
		dataMap.put("tempList", tempList);
		dataMap.put("humidityList", humidityList);
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
		List <SensorCurrentValueResDTO> list = startupParkMainService.getSensorCurrentValue(sensorType);
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
		List <SensorTypeCntResDTO> list = startupParkMainService.getSensorTypeCnt();
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
		
		//	내부 온도 조회
		SensorWeekDTO tempDTO = SensorWeekDTO.builder()
				.startDay(String.valueOf(dayMap.get("startDay")))
				.endDay(String.valueOf(dayMap.get("endDay")))
				.sensorTypeIdx(1)
				.build();
		List <SensorWeekResDTO> tempList = startupParkMainService.getSensorWeek(tempDTO);
			
		//	내부 습도 조회
		SensorWeekDTO humidityDTO = SensorWeekDTO.builder()
				.startDay(String.valueOf(dayMap.get("startDay")))
				.endDay(String.valueOf(dayMap.get("endDay")))
				.sensorTypeIdx(3)
				.build();
		List <SensorWeekResDTO> humidityList = startupParkMainService.getSensorWeek(humidityDTO);
		
		dataMap.put("tempList",tempList);
		dataMap.put("humidityList",humidityList);
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
		List <SensorRealLogResDTO> list = startupParkMainService.getSensorLogLimit();
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
	public HashMap sensorData() throws Exception {
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		List<SensorDataResDTO> list = startupParkMainService.getSensorDataOfEnclosure();
		dataMap.put("sensorDataList", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 함체의 센서 통신 여부
	 * @param enclosureIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/aliveData")
	@ResponseBody
	public HashMap aliveData() throws Exception {
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		List<DeviceList> list = startupParkMainService.getAliveDataOfEnclosure();
		dataMap.put("aliveDataList", list);
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
		
		List<SensorInfoListResDTO> list = startupParkMainService.getSensorInfoList(sensorId);
		
		dataMap.put("dataType", dataType);
		dataMap.put("sensorInfoList", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
}