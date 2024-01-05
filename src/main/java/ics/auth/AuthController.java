package ics.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ics.qst.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.core.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AuthController {

	@Autowired
	private StationService stationService;

	@Value("${aes.salt}")
	private String salt;
	
	@RequestMapping("/robots.txt")
	public String robotst()throws Exception{
		return "robots.txt";
	}
	
	@RequestMapping("/")
	public String index(Model model) throws Exception{
		String url = null;
		
		if(SessionUtil.hasSession()) {
			String role_id = SessionUtil.getRoleId();
			if(role_id.contains("ROLE_A")) {
				//	관리사
				url = "redirect:/manager/main"; 
			}else if(role_id.equals("ROLE_O")) {
				//	운영사
				Integer idx = SessionUtil.getCompanyIdx();
				
				if(idx == 2) {
					url = "redirect:/operator/main"; 
				} else {
					url = "redirect:/operator/other";
				}
				
			}else if(role_id.equals("ROLE_M")) {
				//	회원사
				url = "redirect:/member/user"; 
			}
		}else {
			url = "redirect:/auth/login/form";
		}
		return url;
	}
		
	/**
	 * 로그인
	 * @param model
	 * @return
	 */
	@RequestMapping("/auth/login/form")
	public String loginForm(Model model, HttpServletRequest req, HttpServletResponse res) throws Exception{
		String url = "auth/login/authLoginForm";
		if(SessionUtil.hasSession()) {
			String role_id = SessionUtil.getRoleId();
			
			if(role_id.contains("ROLE_A")) {
				//	관리사
				url = "redirect:/manager/main"; 
			}else if(role_id.equals("ROLE_O")) {
				//	운영사
				Integer idx = SessionUtil.getCompanyIdx();
				
				if(idx == 2) {
					url = "redirect:/operator/main"; 
				} else {
					url = "redirect:/operator/other";
				}
				
			}else if(role_id.equals("ROLE_M")) {
				//	회원사
				url = "redirect:/member/user"; 
			}
		}
		model.addAttribute("stationList", stationService.getStationList());
		return url;
	}

	
	
}
