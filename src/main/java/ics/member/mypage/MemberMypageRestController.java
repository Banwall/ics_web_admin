package ics.member.mypage;

import java.util.HashMap;

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
import ics.member.mypage.dto.MyInfoModProcReqDTO;
import ics.member.mypage.dto.MyInfoPwdModProcReqDTO;
import ics.member.mypage.dto.MyInfoResDTO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/member")
public class MemberMypageRestController {
	
	@Autowired
	private MemberMypageService memberMypageService;

	/**
	 * 마이페이지 - 내정보 가져오기
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mypage/myInfo")
	@ResponseBody
	public ResponseEntity getMyInfo() throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		MyInfoResDTO myInfo = memberMypageService.getMyInfo(SessionUtil.getIdx());
		
		dataMap.put("myInfo", myInfo);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
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
		
		memberMypageService.myInfoModProc(myInfoModProcReqDTO);
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
	@PostMapping(value="/mypage/myInfoPwdModProc", produces = "application/json")
	@ResponseBody
	public ResponseEntity myInfoPwdModProc(@RequestBody @Valid MyInfoPwdModProcReqDTO myInfoPwdModProcReqDTO) throws Exception  {
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		myInfoPwdModProcReqDTO.setMemberIdx(SessionUtil.getIdx());
		myInfoPwdModProcReqDTO.setModifier(SessionUtil.getIdx());
		myInfoPwdModProcReqDTO.setEncMemberPwd(StringUtil.encrypt(myInfoPwdModProcReqDTO.getMemberPwd()));
		
		log.info("마이페이지 - 내 비밀번호 변경 수정");
		log.info(myInfoPwdModProcReqDTO.toString());
		
		memberMypageService.myInfoPwdModProc(myInfoPwdModProcReqDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
	
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}
