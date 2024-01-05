package ics.startuppark.user;

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
public class StartupParkUserController {
	
	@Autowired
	private StartupParkUserService managerUserService;

	/**
	 * 회원사 관리
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user")
	public String managerUser(Model model) throws Exception{
		List<CompanyApprovalListResDTO> companyApprovalList = managerUserService.getCompanyApprovalList("1");
		model.addAttribute("companyApprovalList", companyApprovalList);
		return "startupPark/user/startupParkUser";
	}
		
}
