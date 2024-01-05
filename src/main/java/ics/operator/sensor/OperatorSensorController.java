package ics.operator.sensor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.core.util.StringUtil;

import ics.operator.cctv.OperatorCctvService;
import ics.operator.cctv.dto.StationListResDTO;
import ics.operator.sensor.dto.SensorSelectResDTO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/operator")
@Slf4j
public class OperatorSensorController {
	
	@Autowired
	private OperatorCctvService operatorCctvService;
	
	@Autowired
	private OperatorSensorService operatorSensorService;

	/**
	 * 센서 관리
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sensor/list")
	public String sensorList(Model model)throws Exception{
		return "operator/sensor/operatorSensorList";
	}
	
	/**
	 * 데이터 검색
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sensor/data")
	public String sensorData(Model model)throws Exception{
		//	stationIdx 로 센서 조회
		//	설치 위치 조회	
		List<StationListResDTO> stationList = operatorCctvService.getStationList();
		StationListResDTO dto = stationList.get(0);
		//	센서 조회	- 센서 선택 - 사용중, 승인된 센서 목록 조회
		List<SensorSelectResDTO> sensorSelectList = operatorSensorService.getSensorSelectList(dto.getStationIdx());
		model.addAttribute("sensorSelectList",sensorSelectList);
		model.addAttribute("stationList", stationList);
		
		model.addAttribute("endDate", StringUtil.getTodayDash());
		model.addAttribute("startDate", StringUtil.getTodayDash());
		return "operator/sensor/operatorSensorData";
	}
}
