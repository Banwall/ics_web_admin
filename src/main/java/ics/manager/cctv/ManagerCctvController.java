package ics.manager.cctv;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/manager")
@Slf4j
public class ManagerCctvController {

	@RequestMapping("/cctv")
	public String main(Model model)throws Exception{
		
		return "manager/cctv/managerCctv";
	}
	
}
