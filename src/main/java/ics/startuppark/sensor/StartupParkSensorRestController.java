package ics.startuppark.sensor;

import ics.core.util.PageUtil;
import ics.core.util.SessionUtil;
import ics.core.util.StringUtil;
import ics.startuppark.sensor.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/startupPark/sensor")
@Slf4j
public class StartupParkSensorRestController {
	
	@Autowired
	private StartupParkSensorService managerSensorService;
	
	/**
	 * 회원사의 idx로 센서 조회
	 * 승인 요청 또는 승인 된 센서만
	 * @param sensorListReqDTO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/listOfCompany")
	@ResponseBody
	public HashMap sensorOfCompanyIdx(@ModelAttribute SensorListReqDTO sensorListReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		//System.out.println(sensorListReqDTO.toString());
		Integer sensorCount = managerSensorService.getSensorCntOfCompanyIdx(sensorListReqDTO.getCompanyIdx());
		PageUtil pageInfo = new PageUtil(sensorListReqDTO.getNowPage(), sensorCount, sensorListReqDTO.getListCount(), sensorListReqDTO.getPageGroup());
				
		sensorListReqDTO.setStartNum(pageInfo.getStartNum());
		sensorListReqDTO.setEndNum(pageInfo.getEndNum());
		
		List<SensorListResDTO> sensorList = managerSensorService.getSensorOfCompanyIdx(sensorListReqDTO);
		
		dataMap.put("sensorList", sensorList);
		dataMap.put("page", pageInfo.getPageInfo());
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 회원사의 센서 로그 조회
	 * @param sensorLogReqDTO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/logOfSensorIdx")
	@ResponseBody
	public HashMap logOfSensorIdx(@ModelAttribute SensorLogReqDTO sensorLogReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		sensorLogReqDTO.setStartDate(StringUtil.getTodayDash());
		
		//	1일 센서 조회 (조회일자) - 센서 로그 수 
		Integer logCount = managerSensorService.getSensorLogTodayCount(sensorLogReqDTO);
		PageUtil pageInfo = new PageUtil(sensorLogReqDTO.getNowPage(), logCount, sensorLogReqDTO.getListCount(), sensorLogReqDTO.getPageGroup());
		sensorLogReqDTO.setStartNum(pageInfo.getStartNum());
		sensorLogReqDTO.setEndNum(pageInfo.getEndNum());
		
		//	1일 센서 로그 조회
		List<SensorLogResDTO> sensorLogList = managerSensorService.getSensorLogToday(sensorLogReqDTO);

		dataMap.put("sensorLogList", sensorLogList);
		dataMap.put("page", pageInfo.getPageInfo());
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}

	/**
	 * 센서관리 : 차트용 센서 로그 조회 30
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/logChart")
	@ResponseBody
	public HashMap logOfSensorIdx(@RequestParam Integer sensorIdx) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
				
		//	차트용 센서 로그 조회
		List<SensorLogResDTO> sensorLogChart = managerSensorService.getSensorLogChart(sensorIdx);
		dataMap.put("sensorLogChart", sensorLogChart);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 센서 조회 : SELECT
	 * @param sensorSelectReqDTO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/select")
	@ResponseBody
	public HashMap getSensorSelect(@RequestParam Integer companyIdx) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
						
		//	센서 셀렉트
		List<SensorSelectResDTO> sensorSelectList = managerSensorService.getSensorSelectList(companyIdx);
		dataMap.put("sensorSelectList", sensorSelectList);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 센서 데이터 검색 :  센서별, 일자별 로그 전체 조회
	 * @param sensorLogSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/logList")
	@ResponseBody
	public HashMap getSensorLogList(@ModelAttribute SensorLogSearchReqDTO sensorLogSearchReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		//	로그 갯수 조회
		Integer logCount = managerSensorService.getSensorLogListCount(sensorLogSearchReqDTO);
		
		PageUtil pageInfo = new PageUtil(sensorLogSearchReqDTO.getNowPage(), logCount, sensorLogSearchReqDTO.getListCount(), sensorLogSearchReqDTO.getPageGroup());
		sensorLogSearchReqDTO.setStartNum(pageInfo.getStartNum());
		sensorLogSearchReqDTO.setEndNum(pageInfo.getEndNum());
		
		//	로그 조회
		List<SensorLogSearchResDTO> sensorLogList = managerSensorService.getSensorLogList(sensorLogSearchReqDTO);
		dataMap.put("page", pageInfo.getPageInfo());
		dataMap.put("sensorLogList", sensorLogList);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 센서 데이터 검색 : 검색된 데이터 중 최근 30개
	 * @param sensorLogSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/searchChartLog")
	@ResponseBody
	public HashMap getSearchChartLog(@ModelAttribute SensorSearchChartLogReqDTO sensorSearchChartLogReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		List<SensorSearchChartLogResDTO> sensorLogChart = managerSensorService.getSearchChartLog(sensorSearchChartLogReqDTO);
		
		dataMap.put("sensorLogChart", sensorLogChart);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 센서 승인/미승인 요청
	 * @param sensorApprovalReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/approval")
	@ResponseBody
	public HashMap approval(@RequestBody SensorApprovalReqDTO sensorApprovalReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		sensorApprovalReqDTO.setModifier(SessionUtil.getIdx());
		log.info("센서 승인/미승인 요청");
		log.info(sensorApprovalReqDTO.toString());
		
		managerSensorService.approvalProc(sensorApprovalReqDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
}
