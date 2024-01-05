package ics.core.util;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import ics.auth.CustomUserDetails;


/**
 * 세션 유틸
 * @author Koreasoft
 *
 */
public class SessionUtil {
	
	/*
	public static List<HashMap> getSensorTypes(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;	
		return user.getSensorTypeList();
	}
	*/
	
	/**
	 * 회원사의 인덱스
	 * @return
	 */
	public static Integer getCompanyIdx() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;	
		return user.getCompanyIdx();
	}
	
	/**
	 * 사용자 세션 비밀번호 저장
	 * @param pwd
	 */
	public static void setPassword(String pwd) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;	
		user.setPwd(pwd);
	}
	
	/**
	 * 사용자 정보 수정
	 * @param new_name	이름
	 * @param new_tel 	전화번호
	 * @param new_email	이메일
	 */
	public static void setData(String new_name, String new_tel, String new_email) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;	
		user.setName(new_name);
		user.setTel(new_tel);
		user.setEmail(new_email);
	}
	
	
	/**
	 * 세션 여부 확인 : 세션이 없으면 false, 세션이 있으면 true
	 * @return
	 */
	public static boolean hasSession() {
		boolean result = false;
		//	null인경우 error 발생. null이므로 세션이 없음
		if( SecurityContextHolder.getContext().getAuthentication() != null) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			result = principal instanceof CustomUserDetails;	
		}
		return result;
	}
	
	/**
	 * 사용자 비밀번호
	 * @return
	 */
	public static String getPassword() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		return user.getPwd();
	}
	
	/**
	 * 사용자 인덱스
	 * memberIdx
	 * @return
	 */
	public static Integer getIdx() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		return user.getIdx();
	}
	
	/**
	 * 사용자 ID
	 * @return
	 */
	public static String getUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		return user.getUsername();
	}
	
	/**
	 * 사용자 권한
	 * @return
	 */
	public static String getRoleId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		return user.getAuth_id();
	}

	/**
	 * 토큰 데이터 저장
	 * @param paramMap
	 */
	public static void setToken(HashMap paramMap) {

		HashMap dataMap = (HashMap) paramMap.get("data");
		String fcmToken = String.valueOf(dataMap.get("fcmToken"));
		String uuid = String.valueOf(dataMap.get("uuid"));
		String alarm = String.valueOf(dataMap.get("alarm"));
		boolean isAlarm = alarm == "Y"? true : false;
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		user.setFcmToken(fcmToken);
		user.setUuid(uuid);
		//user.setAlarm(Boolean.parseBoolean(alarm));
		user.setAlarm(isAlarm);
	}
	
	/**
	 * 세션에 회사이름 저장
	 * @param companyName
	 */
	public static void setCompanyName(String companyName) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		user.setCompanyName(companyName);
	}
	
	/**
	 * 세션에 내정보 - 이름 넣기
	 * @param name
	 */
	public static void setName(String name) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		user.setName(name);
	}

	/**
	 * 알람 여부 정보 가져오기
	 * @return
	 */
	public static boolean getAlarm() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;	
		return user.isAlarm();
	}
	
	/**
	 * 세션 알람 설정
	 * @param paramMap
	 */
	public static void setAlarm(HashMap paramMap) {
		String alarm = String.valueOf(paramMap.get("alarm"));
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		user.setAlarm(Boolean.parseBoolean(alarm));
	}
}
