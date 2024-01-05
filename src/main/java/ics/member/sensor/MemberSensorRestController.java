package ics.member.sensor;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

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

import ics.api.sensor.SensorService;
import ics.api.sensor.dto.SensorIsIdReqDTO;
import ics.core.error.ErrorCode;
import ics.core.error.exception.CustomValidException;
import ics.core.util.PageUtil;
import ics.core.util.SessionUtil;
import ics.core.util.StringUtil;
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
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/member/sensor")
@Slf4j
public class MemberSensorRestController {

	@Autowired
	private MemberSensorService memberSensorService;
	
	@Autowired
	private SensorService sensorService;
		
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
		Integer logCount = memberSensorService.getSensorLogListCount(sensorLogSearchReqDTO);
		
		PageUtil pageInfo = new PageUtil(sensorLogSearchReqDTO.getNowPage(), logCount, sensorLogSearchReqDTO.getListCount(), sensorLogSearchReqDTO.getPageGroup());
		sensorLogSearchReqDTO.setStartNum(pageInfo.getStartNum());
		sensorLogSearchReqDTO.setEndNum(pageInfo.getEndNum());
		
		//	로그 조회
		List<SensorLogSearchResDTO> sensorLogList = memberSensorService.getSensorLogList(sensorLogSearchReqDTO);
		dataMap.put("page", pageInfo.getPageInfo());
		dataMap.put("sensorLogList", sensorLogList);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 데이터 검색 : 검색된 데이터 중 최근 30개
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
		
		List<SensorSearchChartLogResDTO> sensorLogChart = memberSensorService.getSearchChartLog(sensorSearchChartLogReqDTO);
		
		dataMap.put("sensorLogChart", sensorLogChart);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원사 관리 - 센서 정보, 센서관리 - 센서목록
	 * companyIdx로 센서 조회 - sensor의 use_yn = 'Y'
	 * @param sensorListReqDTO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/listOfCompany")
	@ResponseBody
	public ResponseEntity sensorOfCompanyIdx(@ModelAttribute SensorListReqDTO sensorListReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();

		Integer companyIdx = SessionUtil.getCompanyIdx();
		//	회원의 idx로 센서 갯수 조회 : companyIdx
		Integer sensorCount = memberSensorService.getSensorCntOfCompanyIdx(companyIdx);
		PageUtil pageInfo = new PageUtil(sensorListReqDTO.getNowPage(), sensorCount, sensorListReqDTO.getListCount(), sensorListReqDTO.getPageGroup());
		sensorListReqDTO.setStartNum(pageInfo.getStartNum());
		sensorListReqDTO.setEndNum(pageInfo.getEndNum());
		sensorListReqDTO.setCompanyIdx(companyIdx);
		
		//	회원사의 idx로 센서 조회
		List<SensorListResDTO> sensorList = memberSensorService.getSensorOfCompanyIdx(sensorListReqDTO);
		
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
		Integer logCount = memberSensorService.getSensorLogTodayCount(sensorLogReqDTO);
		PageUtil pageInfo = new PageUtil(sensorLogReqDTO.getNowPage(), logCount, sensorLogReqDTO.getListCount(), sensorLogReqDTO.getPageGroup());
		sensorLogReqDTO.setStartNum(pageInfo.getStartNum());
		sensorLogReqDTO.setEndNum(pageInfo.getEndNum());
		
		//	1일 센서 로그 조회
		List<SensorLogResDTO> sensorLogList = memberSensorService.getSensorLogToday(sensorLogReqDTO);

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
		List<SensorLogResDTO> sensorLogChart = memberSensorService.getSensorLogChart(sensorIdx);
		dataMap.put("sensorLogChart", sensorLogChart);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 회원사 관리 - 센서 등록
	 * @param sensorAddProcReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/addProc")
	@ResponseBody
	public ResponseEntity sensorAddProc(@RequestBody @Valid SensorAddProcReqDTO  sensorAddProcReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		sensorAddProcReqDTO.setCompanyIdx(SessionUtil.getCompanyIdx());
		log.info("센서 등록");
		log.info(sensorAddProcReqDTO.toString());
		
		SensorIsIdReqDTO dto = SensorIsIdReqDTO.builder()
				.sensorId(sensorAddProcReqDTO.getSensorId())
				.sensorTypeIdx(sensorAddProcReqDTO.getSensorTypeIdx())
				.build();
		Integer count = sensorService.sensorIsId(dto);
		if(count > 0) {
			String str = "센서 아이디 : " + dto.getSensorId() + " 센서 타입 : "  + dto.getSensorTypeIdx() + " 중복";
			throw new CustomValidException(str, ErrorCode.SENSOR_DUPLICATE);
		}
		
		memberSensorService.sensorAddProc(sensorAddProcReqDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원사 관리 - 센서 정보 조회
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/info")
	@ResponseBody
	public ResponseEntity sensorInfo(@RequestParam Integer sensorIdx) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();

		SensorInfoResDTO info = memberSensorService.getSensorInfo(sensorIdx);
	
		dataMap.put("sensorInfo", info);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 회원사 관리 - 센서 수정
	 * @param sensorModProcReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/modProc")
	@ResponseBody
	public ResponseEntity sensorModProc( @RequestBody @Valid SensorModProcReqDTO  sensorModProcReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		sensorModProcReqDTO.setCompanyIdx(SessionUtil.getCompanyIdx());
		log.info("센서 수정");
		log.info(sensorModProcReqDTO.toString());
		
		memberSensorService.sensorModProc(sensorModProcReqDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원사 관리 - 센서 삭제
	 * @param sensorIdx
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/delProc")
	@ResponseBody
	public ResponseEntity sensorDelProc(@RequestBody SensorDelProcReqDTO sensorDelProcReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		sensorDelProcReqDTO.setMemberIdx(SessionUtil.getIdx());
		log.info("센서 삭제");
		log.info("센서 삭제 : " + sensorDelProcReqDTO.toString());
		
		memberSensorService.sensorDelProc(sensorDelProcReqDTO);
				
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	
	/**
	 * 센서 승인 요청
	 * @param sensorApprovalReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/approval")
	@ResponseBody
	public ResponseEntity sensorApproval(@RequestBody SensorApprovalReqDTO sensorApprovalReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		
		sensorApprovalReqDTO.setModifier(SessionUtil.getIdx());
		log.info("센서 승인 요청");
		log.info("센서 승인 요청 : " + sensorApprovalReqDTO.toString());
		
		memberSensorService.sensorApproval(sensorApprovalReqDTO);
			
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	

}
