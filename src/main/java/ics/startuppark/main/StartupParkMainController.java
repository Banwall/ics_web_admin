package ics.startuppark.main;

import ics.startuppark.sensor.StartupParkSensorService;
import ics.startuppark.sensor.dto.EnclosureListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 관리기관 (관리사) - 대시보드
 * ROLE_A 
 * @author Koreasoft
 *
 */
@Controller
@RequestMapping("/startupPark")
@Slf4j
public class StartupParkMainController {

	@Autowired
	private StartupParkSensorService startupParkSensorService;

	@RequestMapping("/main")
	public String main(Model model) throws Exception {
		//	온도, 습도 센서만 조회
		List<EnclosureListDTO> list = startupParkSensorService.getEnclosureList();

		model.addAttribute("sensorList", list);			//	실시간 센싱현황 조회
		return "startupPark/main/startupParkMain";
	}
}
