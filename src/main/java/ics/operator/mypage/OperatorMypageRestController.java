package ics.operator.mypage;

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
import lombok.extern.slf4j.Slf4j;
import ics.operator.mypage.dto.MyMeberListResDTO;
import ics.operator.mypage.dto.CompanyModReqDTO;
import ics.operator.mypage.dto.MyCompanyInfoResDTO;

@Slf4j
@RestController
@RequestMapping("/operator")
public class OperatorMypageRestController {

	
	@Autowired
	private OperatorMypageService operatorMypageService;

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
		MyCompanyInfoResDTO myCompanyInfo = operatorMypageService.getMyCompanyInfo(SessionUtil.getCompanyIdx());
		
		dataMap.put("myCompanyInfo", myCompanyInfo);
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
	
		List<MyMeberListResDTO> myMemberList = operatorMypageService.getMyMemberList(SessionUtil.getCompanyIdx());
		
		dataMap.put("myMemberList", myMemberList);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 마이페이지 - 실증수용기관 정보 수정
	 * @param memberCompanyModReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/mypage/companyModProc")
	@ResponseBody
	public ResponseEntity companyModProc(@RequestBody @Valid CompanyModReqDTO companyModReqDTO) throws Exception{
		HashMap resultMap = new HashMap();
		
		companyModReqDTO.setCompanyIdx(SessionUtil.getCompanyIdx());
		companyModReqDTO.setModifier(SessionUtil.getIdx());
		log.info("마이페이지 - 실증수용기관 정보 수정");
		log.info(companyModReqDTO.toString());
		
		operatorMypageService.companyModProc(companyModReqDTO);
		SessionUtil.setCompanyName(companyModReqDTO.getCompanyName());
		//resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	

}
