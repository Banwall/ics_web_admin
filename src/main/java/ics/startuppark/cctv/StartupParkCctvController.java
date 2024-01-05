package ics.startuppark.cctv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/startupPark")
@Slf4j
public class StartupParkCctvController {

	@RequestMapping("/cctv")
	public String main(Model model)throws Exception{
		
		return "startupPark/cctv/startupParkCctv";
	}
	
}
