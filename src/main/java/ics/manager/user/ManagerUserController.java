package ics.manager.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.manager.user.dto.CompanyApprovalListResDTO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/manager")
@Slf4j
public class ManagerUserController {
	
	@Autowired
	private ManagerUserService managerUserService;

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
		return "manager/user/managerUser";
	}
		
}
