package ics.operator.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.core.util.SessionUtil;
import ics.operator.sensor.OperatorSensorService;
import ics.operator.sensor.dto.EnclosureListDTO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/operator")
@Slf4j
public class OperatorMainController {
	
	@Autowired
	private OperatorSensorService operatorSensorService;

	/**
	 * 운영사 - 메인
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/main")
	public String main(Model model)throws Exception{
		Integer companyIdx = SessionUtil.getCompanyIdx();
		
		//	미세먼지, 온도, 습도만 있는 함체 조회
		List<EnclosureListDTO> list = operatorSensorService.getEnclosureList(companyIdx);
		model.addAttribute("enclosureList", list);			//	실시간 센싱현황 조회
		return "operator/main/operatorMain";
	}
	
	
}
