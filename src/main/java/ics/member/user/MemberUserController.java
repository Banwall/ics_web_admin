package ics.member.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.core.util.SessionUtil;
import ics.member.sensor.MemberSensorService;
import ics.member.sensor.dto.SensorTypeListResDTO;
import ics.member.user.dto.MemberCompanyDTO;
import ics.member.user.dto.StationListResDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원사 관리
 * @author Koreasoft
 *
 */
@Controller
@RequestMapping("/member")
@Slf4j
public class MemberUserController {
	
	@Autowired
	private MemberUserService memberUserService;
	
	@Autowired
	private MemberSensorService memberSensorService;

	/**
	 * 회원사 관리
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user")
	public String user(Model model)throws Exception{
		MemberCompanyDTO myCompanyInfo = memberUserService.getCompanyInfo(SessionUtil.getCompanyIdx());
		List<SensorTypeListResDTO> sensorTypeList = memberSensorService.getSensorTypeListAll();
		List<StationListResDTO> stationList = memberUserService.getStationList();
		model.addAttribute("stationList", stationList);
		model.addAttribute("myCompanyInfo", myCompanyInfo);
		model.addAttribute("sensorTypeList",sensorTypeList);
		return "member/user/memberUser";
	}
}