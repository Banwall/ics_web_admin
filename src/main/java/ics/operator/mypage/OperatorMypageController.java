package ics.operator.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.core.util.SessionUtil;
import ics.operator.mypage.dto.MyCompanyInfoResDTO;


@Controller
@RequestMapping("/operator")
public class OperatorMypageController {
	
	@Autowired
	private OperatorMypageService operatorMypageService;

	/**
	 * 마이페이지
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mypage/info")
	public String info(Model model) throws Exception{
		MyCompanyInfoResDTO myCompanyInfo = operatorMypageService.getMyCompanyInfo(SessionUtil.getCompanyIdx());
		model.addAttribute("myCompanyInfo", myCompanyInfo);
		return "operator/mypage/operatorMypageInfo";
	}
}
