package ics.member.user;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ics.core.util.PageUtil;
import ics.core.util.SessionUtil;
import ics.member.user.dto.MemberCompanyDTO;
import ics.member.user.dto.MemberCompanyModReqDTO;
import ics.member.user.dto.MemberListReqDTO;
import ics.member.user.dto.MemberListResDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberUserRestController {

	@Autowired
	private MemberUserService memberUserService;
	
	/**
	 * 회원사 관리 - 회사 정보
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/user/companyInfo")
	@ResponseBody
	public ResponseEntity companyInfo() throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		MemberCompanyDTO myCompanyInfo = memberUserService.getCompanyInfo(SessionUtil.getCompanyIdx());
		
		dataMap.put("myCompanyInfo", myCompanyInfo);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원사 관리 - 회원 목록 조회
	 * @param memberListReqDTO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/user/memberList")
	@ResponseBody
	public ResponseEntity userList(@ModelAttribute MemberListReqDTO memberListReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		memberListReqDTO.setCompanyIdx(SessionUtil.getCompanyIdx());
		
		Integer memberTotalCount = memberUserService.getMemberTotalCount(memberListReqDTO);
		PageUtil pageUtil = new PageUtil(memberListReqDTO.getNowPage(), memberTotalCount, memberListReqDTO.getListCount(), memberListReqDTO.getPageGroup());
		
		memberListReqDTO.setStartNum(pageUtil.getStartNum());
		memberListReqDTO.setEndNum(pageUtil.getEndNum());
		
		List<MemberListResDTO> memberList = memberUserService.getMemberList(memberListReqDTO);
		
		dataMap.put("page", pageUtil.getPageInfo());
		dataMap.put("memberList", memberList);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원사관리 - 실증기업 정보 수정
	 * @param memberCompanyModReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/user/companyModProc")
	@ResponseBody
	public ResponseEntity companyModProc(@RequestBody @Valid MemberCompanyModReqDTO memberCompanyModReqDTO) throws Exception{
		
		log.info("실증 기업 정보 수정");
		log.info(memberCompanyModReqDTO.toString());
		
		HashMap resultMap = new HashMap();
		memberCompanyModReqDTO.setCompanyIdx(SessionUtil.getCompanyIdx());
		memberCompanyModReqDTO.setModifier(SessionUtil.getIdx());
		memberUserService.companyModProc(memberCompanyModReqDTO);
		
		SessionUtil.setCompanyName(memberCompanyModReqDTO.getCompanyName());
		
		//resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
}
