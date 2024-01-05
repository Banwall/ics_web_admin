package ics.startuppark.user;

import ics.startuppark.user.dto.CompanyApprovalListResDTO;
import ics.startuppark.user.dto.CompanyResDTO;
import ics.startuppark.user.dto.MemberSearchReqDTO;
import ics.startuppark.user.dto.MemberSearchResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/startupPark")
public class StartupParkUserRestContoller {
	
	@Autowired
	private StartupParkUserService startupParkUserService;
	
	/**
	 *	회원사 승인/미승인 조회
	 * @param roleSearchType
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/companyApprovalList")
	@ResponseBody
	public HashMap companyApprovalList(@RequestParam String roleSearchType) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		List<CompanyApprovalListResDTO> companyApprovalList = startupParkUserService.getCompanyApprovalList(roleSearchType);
		dataMap.put("companyApprovalList", companyApprovalList);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 회원사의 회원 정보
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/companyInfo")
	@ResponseBody
	public HashMap companyInfo(@RequestParam Integer companyIdx) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		CompanyResDTO companyInfo = startupParkUserService.getCompanyInfo(companyIdx);
		dataMap.put("companyInfo", companyInfo);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}	
	
	@GetMapping(value="/memberList")
	@ResponseBody
	public HashMap userMemberList(@ModelAttribute MemberSearchReqDTO memberSearchReqDTO) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		List<MemberSearchResDTO> memberList = startupParkUserService.getMemberList(memberSearchReqDTO);
		dataMap.put("memberList", memberList);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}	
}
