package ics.member.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.core.util.SessionUtil;
import ics.member.mypage.dto.MyInfoResDTO;


@Controller
@RequestMapping("/member")
public class MemberMypageController {

	@Autowired
	private MemberMypageService memberMypageService;
	
	/**
	 * 마이페이지
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mypage/info")
	public String info(Model model) throws Exception{
		MyInfoResDTO myInfo = memberMypageService.getMyInfo(SessionUtil.getIdx());
		model.addAttribute("myInfo", myInfo);
		return "member/mypage/memberMypageInfo";
	}

}
