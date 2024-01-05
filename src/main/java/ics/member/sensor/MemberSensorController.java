package ics.member.sensor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.core.util.SessionUtil;
import ics.core.util.StringUtil;
import ics.member.sensor.dto.SensorSelectResDTO;
import ics.member.sensor.dto.SensorTypeListResDTO;
import ics.member.user.MemberUserService;
import ics.member.user.dto.StationListResDTO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberSensorController {
	
	@Autowired
	private MemberUserService memberUserService;
	
	@Autowired
	private MemberSensorService memberSensorService;

	/**
	 * 센서 관리
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sensor/list")
	public String list(Model model)throws Exception{
		List<SensorTypeListResDTO> sensorTypeList = memberSensorService.getSensorTypeListAll();
		List<StationListResDTO> stationList = memberUserService.getStationList();
		model.addAttribute("stationList", stationList);
		model.addAttribute("sensorTypeList",sensorTypeList);
		return "member/sensor/memberSensorList";
	}
	
	
	/**
	 * 데이터 검색
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sensor/data")
	public String data(Model model)throws Exception{
		
		//	센서 조회
		List<SensorSelectResDTO> sensorSelectList = memberSensorService.getSensorSelectList(SessionUtil.getCompanyIdx());
		
		model.addAttribute("sensorSelectList",sensorSelectList);
		model.addAttribute("endDate", StringUtil.getTodayDash());
		model.addAttribute("startDate", StringUtil.getTodayDash());
		return "member/sensor/memberSensorData";
	}
	
	
}
