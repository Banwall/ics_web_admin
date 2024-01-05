package ics.manager.mypage;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ics.core.util.SessionUtil;
import ics.core.util.StringUtil;
import ics.manager.mypage.dto.ManagerCompanyModReqDTO;
import ics.manager.mypage.dto.MyCompanyInfoResDTO;
import ics.manager.mypage.dto.MyInfoModProcReqDTO;
import ics.manager.mypage.dto.MyInfoResDTO;
import ics.manager.mypage.dto.MyMeberListResDTO;
import ics.manager.mypage.dto.MyInfoPwdModProcReqDTO;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/manager")
@Slf4j
public class ManagerMypageRestController {

	@Autowired
	private ManagerMypageService managerMypageService;
	
	/**
	 * 마이페이지 - 회사 정보 가져오기
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mypage/companyInfo")
	@ResponseBody
	public HashMap getCompanyInfo() throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		MyCompanyInfoResDTO myCompanyInfo = managerMypageService.getMyCompanyInfo(SessionUtil.getCompanyIdx());
		
		dataMap.put("myCompanyInfo", myCompanyInfo);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 마이페이지 - 내정보 가져오기
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mypage/myInfo")
	@ResponseBody
	public HashMap getMyInfo() throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		MyInfoResDTO myInfo = managerMypageService.getMyInfo(SessionUtil.getIdx());
		
		dataMap.put("myInfo", myInfo);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 마이페이지 - 회사의 회원 정보 목록
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mypage/memberList")
	@ResponseBody
	public HashMap getMyMemberList() throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
	
		List<MyMeberListResDTO> myMemberList = managerMypageService.getMyMemberList(SessionUtil.getCompanyIdx());
		
		dataMap.put("myMemberList", myMemberList);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}

	
	/**
	 * 마이페이지 - 회사 정보 수정
	 * @param memberCompanyModReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/mypage/companyModProc")
	@ResponseBody
	public ResponseEntity companyModProc(@RequestBody @Valid ManagerCompanyModReqDTO managerCompanyModReqDTO) throws Exception{
		HashMap resultMap = new HashMap();
		
		log.info("실증 기업 정보 수정");
		managerCompanyModReqDTO.setCompanyIdx(SessionUtil.getCompanyIdx());
		managerCompanyModReqDTO.setModifier(SessionUtil.getIdx());
		log.info(managerCompanyModReqDTO.toString());
		
		managerMypageService.companyModProc(managerCompanyModReqDTO);
		//resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 마이페이지 - 내정보 변경 저장
	 * @param myInfoModProcReqDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mypage/myInfoModProc")
	@ResponseBody
	public ResponseEntity myInfoModProc(@RequestBody @Valid MyInfoModProcReqDTO myInfoModProcReqDTO) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		
		myInfoModProcReqDTO.setMemberIdx(SessionUtil.getIdx());

		log.info("마이페이지 - 내정보 변경 저장");
		log.info(myInfoModProcReqDTO.toString());
		
		managerMypageService.myInfoModProc(myInfoModProcReqDTO);
		SessionUtil.setName(myInfoModProcReqDTO.getMemberName());
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 마이페이지 - 내 비밀번호 변경 수정
	 * @param myInfoPwdModProcReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/mypage/myInfoPwdModProc")
	@ResponseBody
	public ResponseEntity myInfoPwdModProc(@RequestBody MyInfoPwdModProcReqDTO myInfoPwdModProcReqDTO) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		myInfoPwdModProcReqDTO.setMemberIdx(SessionUtil.getIdx());
		myInfoPwdModProcReqDTO.setModifier(SessionUtil.getIdx());
		myInfoPwdModProcReqDTO.setEncMemberPwd(StringUtil.encrypt(myInfoPwdModProcReqDTO.getMemberPwd()));
		
		log.info("마이페이지 - 내 비밀번호 변경 수정");
		log.info(myInfoPwdModProcReqDTO.toString());
		
		managerMypageService.myInfoPwdModProc(myInfoPwdModProcReqDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

}
