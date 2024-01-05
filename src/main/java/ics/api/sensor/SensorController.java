package ics.api.sensor;


import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import ics.api.sensor.dto.SensorIsIdReqDTO;
import ics.api.sensor.dto.SensorLogCsvReqDTO;
import ics.api.sensor.dto.SensorLogCsvResDTO;
import ics.api.sensor.dto.SensorTypeAddReqDTO;
import ics.api.sensor.dto.SensorTypeListResDTO;
import ics.core.error.ErrorCode;
import ics.core.error.exception.CustomValidException;
import ics.core.util.SessionUtil;
import ics.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/sensor")
@Slf4j
public class SensorController {
	
	@Autowired
	private SensorService sensorService;
	
	/**
	 * csv 다운로드
	 * @param response
	 * @param sensorLogCsvReqDTO
	 * @throws Exception
	 */
    @RequestMapping(value = "/downloadCSV")
    public void downloadCSV(HttpServletResponse response,
    		@ModelAttribute SensorLogCsvReqDTO sensorLogCsvReqDTO) throws Exception {
    	
    	log.info("CSV 파일 다운로드");
    	log.info(sensorLogCsvReqDTO.toString());
      
    	List<SensorLogCsvResDTO> dtoList = sensorService.getSensorLogList(sensorLogCsvReqDTO);
    	
    	log.info("조회된 파일 수 : " + dtoList.size());
    	
    	String fileName = sensorLogCsvReqDTO.getFileName()+".csv";
    	
    	//	인코딩
    	String encordedFilename = URLEncoder.encode(fileName,"UTF-8").replace("+", "%20");

    	response.setHeader("Content-Disposition",   "attachment;filename=" + encordedFilename + ";filename*= UTF-8''" + encordedFilename);

        //	new String(변수.getBytes("KSC5601"),"8859_1")
        ICsvMapWriter mapWrite =  new CsvMapWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] str = {"회원사", "센서 아이디", "센서 종류", "설치 위치", "수신 시간", "수신 값", "승인 일자"};
        String[] header = new String[str.length];
        for(int i=0; i<str.length; i++) {
        	header[i] = new String(StringUtil.strFmt(str[i]));
        }
        
        mapWrite.writeHeader(header);
        
        for(SensorLogCsvResDTO dto : dtoList) {
            Map<String, Object> tempMap = new HashMap<String, Object>();
            tempMap.put(header[0], StringUtil.strFmt(dto.getCompanyName()));
            tempMap.put(header[1], StringUtil.strFmt(dto.getSensorId()));
            tempMap.put(header[2], StringUtil.strFmt(dto.getSensorTypeName()));
            tempMap.put(header[3], StringUtil.strFmt(dto.getSensorLocationDescription()));
            tempMap.put(header[4], StringUtil.strFmt(dto.getSensorLogCreateDate()));
            tempMap.put(header[5], StringUtil.strFmt(StringUtil.typeValue(dto.getSensorTypeCode(), dto.getSensorLogValue())));
            tempMap.put(header[6], StringUtil.strFmt(dto.getSensorApprovalDate()));
            mapWrite.write(tempMap, header);
        }
        
        mapWrite.close();
    }
    
    
	/**
	 * 센서 중복 체크
	 * @param sensorIsIdReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/isId")
	@ResponseBody
	public ResponseEntity sensorIsId(@RequestBody @Valid SensorIsIdReqDTO sensorIsIdReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		boolean isSensor = false;
		
		Integer count = sensorService.sensorIsId(sensorIsIdReqDTO);
				
		if(count > 0) {
			isSensor = false;
		}else {
			//	중복이 없으면
			isSensor = true;
		}
		
		dataMap.put("isSensor", isSensor);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 센서 타입 목록 가져오기
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/typeList")
	public ResponseEntity sensorTypeList() throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		List<SensorTypeListResDTO> sensorTypeList = sensorService.getSensorTypeList();
		
		dataMap.put("sensorTypeList", sensorTypeList);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 센서 타입 코드 등록
	 * @param sensorTypeAddReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="typeAdd")
	public ResponseEntity sensorTypeAdd(@RequestBody @Valid SensorTypeAddReqDTO sensorTypeAddReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		Integer cnt = sensorService.getIsSensorType(sensorTypeAddReqDTO);
		
		if(cnt > 0) {
			throw new CustomValidException("센서 타입 코드 중복 : " + sensorTypeAddReqDTO.getSensorTypeCode() , ErrorCode.SENSOR_TYPE_DUPLICATE);
		}
		sensorTypeAddReqDTO.setRegistrant(SessionUtil.getIdx());
		log.debug("센서 코드 등록 : " + sensorTypeAddReqDTO.toString());
		
		sensorService.sensorTypeAdd(sensorTypeAddReqDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/*
	@GetMapping(value="/log/alarm")
	@ResponseBody
	public ResponseEntity getSensorLogAlarm(@RequestParam Integer companyIdx) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		log.info("센서 로그 알람 조회 : " + companyIdx);	//	1 전체, 2 내 센서
		
		List<SensorLogAlarmResDTO> list = sensorService.getSensorLogAlarm(companyIdx);
		
		dataMap.put("list", list);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	*/
}

