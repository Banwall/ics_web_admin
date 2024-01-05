package ics.manager.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.manager.sensor.ManagerSensorService;
import ics.manager.sensor.dto.EnclosureListDTO;
import ics.manager.sensor.dto.SensorTypeListDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * 관리기관 (관리사) - 대시보드
 * ROLE_A 
 * @author Koreasoft
 *
 */
@Controller
@RequestMapping("/manager")
@Slf4j
public class ManagerMainController {

	@Autowired
	private ManagerSensorService manaSensorService;
	
	@RequestMapping("/main")
	public String main(Model model)throws Exception{
		//	함체 전체 조회
		//List<EnclosureListDTO> listAll = manaSensorService.getEnclosureListAll();
		
		//	미세먼지, 온도, 습도만 있는 함체 조회
		List<EnclosureListDTO> list = manaSensorService.getEnclosureList();

		//	미세먼지, 온도, 습도의 센서 타입 조회
		//List<SensorTypeListDTO> typeList = manaSensorService.getSensorTypeInTempHumiDust();
		
		//model.addAttribute("typeList", typeList);			//	센서의 타입 조회
		//model.addAttribute("enclosureListAll", listAll);	//	함체 조회
		model.addAttribute("enclosureList", list);			//	실시간 센싱현황 조회
		return "manager/main/managerMain";
	}
	
	@RequestMapping("/startupPark")
	public String startupPark(Model model) {

		return "manager/startupPark/startupParkMain";
	}
}
