package ics.manager.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.core.util.SessionUtil;
import ics.manager.mypage.dto.MyCompanyInfoResDTO;
import ics.manager.mypage.dto.MyInfoResDTO;

@Controller
@RequestMapping("/manager")
public class ManagerMypageController {

	@Autowired
	private ManagerMypageService managerMypageService;
	
	/**
	 * 마이 페이지
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mypage/info")
	public String info(Model model) throws Exception{
		MyCompanyInfoResDTO myCompanyInfo = managerMypageService.getMyCompanyInfo(SessionUtil.getCompanyIdx());
		MyInfoResDTO myInfo = managerMypageService.getMyInfo(SessionUtil.getIdx());
		
		model.addAttribute("myCompanyInfo",myCompanyInfo);
		model.addAttribute("myInfo",myInfo);
		return "manager/mypage/managerMypageInfo";
	}

	
}
