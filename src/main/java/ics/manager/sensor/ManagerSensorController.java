package ics.manager.sensor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.core.util.StringUtil;
import ics.manager.sensor.dto.SensorSelectResDTO;
import ics.manager.user.ManagerUserService;
import ics.manager.user.dto.CompanyApprovalListResDTO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/manager")
@Slf4j
public class ManagerSensorController {
	
	@Autowired
	private ManagerUserService managerUserService;
	
	@Autowired
	private ManagerSensorService managerSensorService;
	
	/**
	 * 센서관리 페이지로 이동
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sensor/list")
	public String sensorList(Model model) throws Exception{
		List<CompanyApprovalListResDTO> companyApprovalList = managerUserService.getCompanyApprovalList("1");
		model.addAttribute("companyApprovalList", companyApprovalList);
		return "manager/sensor/managerSensorList";
	}
	
	/**
	 * 데이터 검색 페이지로 이동
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sensor/data")
	public String sensorData(Model model) throws Exception{
		//	회원사 조회
		//	1 운영사, 회원사 / 2, 운영사, 3 관리사 , 4 운영사/회원사 중 승인 된 회사
		List<CompanyApprovalListResDTO> companyApprovalList = managerUserService.getCompanyApprovalList("4");
		//	센서 조회
		List<SensorSelectResDTO> sensorSelectList = managerSensorService.getSensorSelectList(0);
		
		model.addAttribute("endDate", StringUtil.getTodayDash());
		model.addAttribute("startDate", StringUtil.getTodayDash());
		model.addAttribute("sensorSelectList", sensorSelectList);
		model.addAttribute("companyApprovalList", companyApprovalList);
		return "manager/sensor/managerSensorData";
	}
}
