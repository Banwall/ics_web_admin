package ics.api.member;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ics.api.member.dto.MemberAddProcReqDTO;
import ics.api.member.dto.MemberDelProcReqDTO;
import ics.api.member.dto.MemberIsIdReqDTO;
import ics.api.member.dto.MemberModProcReqDTO;
import ics.api.member.dto.MemberPwdModProcDTO;
import ics.api.member.dto.MemberUserInfoResDTO;
import ics.core.error.ErrorCode;
import ics.core.error.exception.CustomValidException;
import ics.core.util.SessionUtil;
import ics.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/member")
@Slf4j
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * 아이디 중복 체크
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/isId")
	@ResponseBody
	public ResponseEntity memberIsid(@RequestBody @Valid MemberIsIdReqDTO memberIsIdReqDTO) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		log.info("회원 아이디 중복 체크");
		log.info(memberIsIdReqDTO.toString());
				
		Integer cnt = memberService.memberIsId(memberIsIdReqDTO);
		boolean isId = cnt == 0? true : false;
		dataMap.put("isId", isId);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원 신규 등록
	 * @param memberAddProcReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/addProc")
	@ResponseBody
	public ResponseEntity memberAddProc(@RequestBody @Valid MemberAddProcReqDTO memberAddProcReqDTO) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		log.info("회원 신규 등록");
		log.info(memberAddProcReqDTO.toString());
		
		MemberIsIdReqDTO dto = MemberIsIdReqDTO.builder()
				.memberId(memberAddProcReqDTO.getMemberId()).build();
		
		Integer cnt = memberService.memberIsId(dto);
		boolean isId = cnt == 0? true : false;
		
		if(!isId) {
			throw new CustomValidException(memberAddProcReqDTO.getMemberId() + " 이미 사용중", ErrorCode.MEMBER_ID_DUPLICATE);
		}
		
		memberAddProcReqDTO.setEncMemberPwd(StringUtil.encrypt(memberAddProcReqDTO.getMemberPwd()));
		
		memberService.memberAddProc(memberAddProcReqDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원 정보 삭제
	 * @param memberDelProcReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/delProc")
	@ResponseBody
	public ResponseEntity userDelProc(@RequestBody MemberDelProcReqDTO memberDelProcReqDTO) throws Exception{
		
		HashMap resultMap = new HashMap();
		memberDelProcReqDTO.setModifier(SessionUtil.getIdx());

		log.info("회원 정보 삭제");
		log.info(memberDelProcReqDTO.toString());
				
		memberService.memberDelProc(memberDelProcReqDTO);

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원 정보 조회
	 * @param memberIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/info")
	@ResponseBody
	public ResponseEntity userInfo(@RequestParam Integer memberIdx) throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		MemberUserInfoResDTO info = memberService.getMemberInfo(memberIdx);
		
		dataMap.put("info", info);

		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 회원 정보 수정
	 * @param memberModProcReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/modProc")
	@ResponseBody
	public ResponseEntity memberModProc(@RequestBody @Valid MemberModProcReqDTO memberModProcReqDTO) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		memberModProcReqDTO.setModifier(SessionUtil.getIdx());
		log.info("회원 정보 수정");
		log.info(memberModProcReqDTO.toString());
		
		memberService.memberModProc(memberModProcReqDTO);
		Integer flag = 0;		
		if(memberModProcReqDTO.getMemberIdx().intValue() == SessionUtil.getIdx().intValue()) {
			flag = 1;
			SessionUtil.setName(memberModProcReqDTO.getMemberName());
			dataMap.put("memberName", memberModProcReqDTO.getMemberName());
		}
		
		dataMap.put("flag", flag);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원 비밀번호 수정
	 * @param memberPwdModProcDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/pwdModProc")
	@ResponseBody
	public ResponseEntity pwdModProc(@RequestBody @Valid MemberPwdModProcDTO memberPwdModProcDTO) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		memberPwdModProcDTO.setModifier(SessionUtil.getIdx());
		memberPwdModProcDTO.setEncMemberPwd(StringUtil.encrypt(memberPwdModProcDTO.getMemberPwd()));
		
		log.info("회원 비밀번호 수정");
		log.info(memberPwdModProcDTO.toString());
		
		memberService.memberPwdModProc(memberPwdModProcDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
}
