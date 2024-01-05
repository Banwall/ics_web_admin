package ics.startuppark.sensor;

import ics.core.util.StringUtil;
import ics.startuppark.sensor.dto.SensorSelectResDTO;
import ics.startuppark.user.StartupParkUserService;
import ics.startuppark.user.dto.CompanyApprovalListResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/startupPark")
@Slf4j
public class StartupParkSensorController {
	
	@Autowired
	private StartupParkUserService managerUserService;
	
	@Autowired
	private StartupParkSensorService managerSensorService;
	
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
		return "startupPark/sensor/startupParkSensorList";
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
		return "startupPark/sensor/startupParkSensorData";
	}
}
