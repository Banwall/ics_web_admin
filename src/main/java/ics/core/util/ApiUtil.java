package ics.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiUtil {

	//private static String url = "http://qstech.iptime.org:8880";
	private static String url = "http://221.154.134.32:8880";
	
	
	/**
	 * 센서조회	(로그인시 센서 조회하여 세션에 저장)
	 * @return
	 * @throws Exception
	 */
	public static List<HashMap> getSensorTypes()  throws Exception{			
		String m = url+"/v1/api/sensor-types?page=0&pagePer=999999";
		m = m.replaceAll("#page#", "0");
		m = m.replaceAll("#pagePer#", "99999");
		HashMap apiResultMap = sendJsonGet(m, null);
	
		List<HashMap> list = new ArrayList<HashMap>();
		
		Integer responseCode = Integer.parseInt(String.valueOf(apiResultMap.get("responseCode")));
		if (responseCode == 200) {
			HashMap dataMap = (HashMap) apiResultMap.get("data");
			list = (List) dataMap.get("sensorTypes");
		}
		
		return list;
	}
		
	/**
	 * 회원사 목록 조회
	 * @param page
	 * @param pagePer
	 * @return
	 * @throws Exception
	 */
	public static HashMap getCompanys(String page, String pagePer, String role) throws Exception{
		String m = url+"/v1/api/companys?page=#page#&pagePer=#pagePer#&role=#role#";
		m = m.replaceAll("#page#", page);
		m = m.replaceAll("#pagePer#", pagePer);
		if("null".equals(role) || role == null || "".equals(role)) {
			m = m.replace("&role=#role#", "");
		}else {
			m = m.replace("#role#", role);
		}
		return sendJsonGet(m, null);
	}
	
	/**
	 * 센서 함체의 센서 목록 조회
	 * @param sensorId
	 * @param page
	 * @param pagePer
	 * @return
	 * @throws Exception
	 */
	public static HashMap getSensors(String sensorId, String page, String pagePer) throws Exception{
		String m = url+"/v1/api/sensors/#sensorId#?page=#page#&pagePer=#pagePer#";
		m = m.replaceAll("#sensorId", sensorId);
		m = m.replaceAll("#page#", page);
		m = m.replaceAll("#pagePer#", pagePer);
		return sendJsonGet(m, null);
	}
	
	//	CCTV 정보 조회
	public static HashMap getCCTV(String cctvId) throws Exception{
		String m = url+"/v1/api/cctv/#cctvId#";
		m = m.replaceAll("#cctvId#", cctvId);
		return sendJsonGet(m, null);
	}
	
	
	
	/**
	 * 센서 위치
	 * @return
	 */
	public static List<HashMap> getSensorLocation(){
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		
		map.put("type", "1");
		map.put("text", "일반");
		list.add(map);
		
		map = new HashMap();
		map.put("type", "2");
		map.put("text", "인천대입구역");
		list.add(map);
		
		return list;
	}
	
	/**
	 * 센서 목록 조회 
	 * @param companyIdx
	 * @param page
	 * @param pagePer
	 * @return
	 * @throws Exception
	 */
	public static HashMap getSensorList(String companyIdx, String page, String pagePer) throws Exception{
		String m = url+"/v1/api/sensors?companyIdx=#companyIdx#&page=#page#&pagePer=#pagePer#";
		if("null".equals(companyIdx) || companyIdx == null || "".equals(companyIdx)) {
			m = m.replaceAll("companyIdx=#companyIdx#", "");
		}else {
			m = m.replaceAll("#companyIdx#", companyIdx);
		}
		
		m = m.replaceAll("#page#", page);
		m = m.replaceAll("#pagePer#", pagePer);
		return sendJsonGet(m, null);
	}
	
	//	센서 삭제
	public static HashMap sensorDelete(String sensorId, String sensorType) throws Exception{
		String m = url+"/v1/api/sensors/#sensorId#/#sensorType#/delete";
		m = m.replaceAll("#sensorId#", sensorId);
		m = m.replaceAll("#sensorType#", sensorType);
		return sendJsonPost(m, null);
	}
		
	/**
	 * 센서 등록	
	 * @param companyIdx
	 * @param locationIdx
	 * @param sensorId
	 * @param sensorType
	 * @param sensorLocationDescription
	 * @param sensorThreshold
	 * @return
	 * @throws Exception
	 */
	public static HashMap sensors(String companyIdx, String locationIdx, String sensorId, String sensorType, String sensorLocationDescription, String sensorThreshold)throws Exception{
		String m = url+"/v1/api/sensors";
		HashMap<String, Object> paramMap = new HashMap();
		paramMap.put("companyIdx", companyIdx);
		paramMap.put("locationIdx", locationIdx);
		paramMap.put("sensorId", sensorId);
		paramMap.put("sensorType", sensorType);
		paramMap.put("sensorLocationDescription", sensorLocationDescription);
		paramMap.put("sensorThreshold", sensorThreshold);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(paramMap);
		return sendJsonPost(m, json);
	}
	
	/**
	 * 센서 승인 요청
	 * @param sensorId
	 * @param sensorType
	 * @return
	 * @throws Exception
	 */
	public static HashMap sensorRequestApprove(String sensorId, String sensorType)throws Exception{
		String m = url+"/v1/api/sensors/#sensorId#/#sensorType#/request-approve";
		m = m.replaceAll("#sensorId#", sensorId);
		m = m.replaceAll("#sensorType#", sensorType);

		return sendJsonPost(m, null);
	}
	
	/**
	 * 센서 승인
	 * @param sensorId
	 * @param sensorType
	 * @return
	 * @throws Exception
	 */
	public static HashMap sensorApprove(String sensorId, String sensorType)throws Exception{
		String m = url+"/v1/api/sensors/#sensorId#/#sensorType#/approve";
		m = m.replaceAll("#sensorId#", sensorId);
		m = m.replaceAll("#sensorType#", sensorType);
		return sendJsonPost(m, null);
	}
	
	//	센서 미승인
	public static HashMap sensorDisapprove(String sensorId, String sensorType)throws Exception{
		String m = url+"/v1/api/sensors/#sensorId#/#sensorType#/disapprove";
		m = m.replaceAll("#sensorId#", sensorId);
		m = m.replaceAll("#sensorType#", sensorType);
		return sendJsonPost(m, null);
	}
	
	/**
	 * 센서 데이터 목록 조회
	 * @param sensorId
	 * @param sensorType
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public static HashMap sensorValues(String sensorId, String sensorType, HashMap paramMap) throws Exception{
		String m = url+"/v1/api/sensors/#sensorId#/#sensorType#/values";
		m = m.replaceAll("#sensorId#", sensorId);
		m = m.replaceAll("#sensorType#", sensorType);
		if (paramMap != null) {
			String[] str = { "startDate", "endDate", "page", "pagePer" };
			for (int i = 0; i < str.length; i++) {
				if (i == 0) {
					m = m + "?" + str[i] + "=" + paramMap.get(str[i]);
				} else {
					m = m + "&" + str[i] + "=" + paramMap.get(str[i]);
				}
			}
		}
		//	json 값이 없으면 null
		//m = "http://qstech.iptime.org:8880/v1/api/sensors/00002ca7804606c2/INNER_TEMPERATURE/values?startDate=2021-12-17+00:00:00&endDate=2021-12-17+23:59:59&page=0&pagePer=99999";
		return sendJsonGet(m, null);
	}
	
	/**
	 * 알람 목록 조회
	 * @param page
	 * @param pagePer
	 * @return
	 * @throws Exception
	 */
	public static HashMap getAlarms( String page, String pagePer) throws Exception{
		String m = url+"/v1/api/alarms?page=#page#&pagePer=#pagePer#";
		m = m.replaceAll("#page#", page);
		m = m.replaceAll("#pagePer#", pagePer);
		return sendJsonGet(m, null);
	}
	
	/**
	 * 알람 삭제
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public static HashMap alarmDelete(String idx) throws Exception{
		String m = url+"/v1/api/alarms/#idx#/delete";
		m = m.replaceAll("#idx#", idx);
		return sendJsonPost(m, null);
	}
	
	/**
	 * 회원 비밀번호 변경
	 * @param idx
	 * @param pwd
	 * @param new_pwd
	 * @return
	 * @throws Exception
	 */
	public static HashMap memberInfoModifyPwd(String idx, String pwd, String new_pwd) throws Exception{
		String m = url+"/v1/api/members/#idx#/modify";
		m = m.replace("#idx#", idx);
		HashMap<String, Object> paramMap = new HashMap();
		paramMap.put("memberPw", pwd);
		paramMap.put("modifyMemberPw", new_pwd);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(paramMap);
		return sendJsonPost(m, json);
	}
	
	/**
	 * 회원 정보 변경
	 * @param idx
	 * @param pwd
	 * @param name
	 * @param tel
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public static HashMap memberInfoModify(String idx, String pwd,String name, String tel, String email) throws Exception{
		String m = url+"/v1/api/members/#idx#/modify";
		m = m.replace("#idx#", idx);
		HashMap<String, Object> paramMap = new HashMap();
		paramMap.put("memberPw", pwd);
		paramMap.put("modifyMemberName", name);
		paramMap.put("modifyMemberTel", tel);
		paramMap.put("modifyMemberEmail", email);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(paramMap);
		return sendJsonPost(m, json);
	}
	
	/**
	 * 회원사 미승인
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public static HashMap disapprove(String idx) throws Exception {
		String m = url+"/v1/api/members/#idx#/disapprove";
		m = m.replace("#idx#", idx);
		HashMap resultMap = sendJsonPost(m, null);
		return resultMap;
	}	
	

	/**
	 * 회원사 승인
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public static HashMap approve(String idx) throws Exception {
		String m = url+"/v1/api/members/#idx#/approve";
		m = m.replace("#idx#", idx);
		HashMap resultMap = sendJsonPost(m, null);
		return resultMap;
	}
	
	/**
	 * 회원사의 회원 목록 조회
	 * @return
	 * @throws Exception
	 */
	public static HashMap getMembers(String page, String pagePer) throws Exception{
		String m = url+"/v1/api/members?page=#page#&pagePer=#pagePer#";
		m = m.replaceAll("#page#", page);
		m = m.replaceAll("#pagePer#", pagePer);
		
		HashMap resultMap = sendJsonGet(m, null);
		return resultMap;
	}
	
	/**
	 * 회원사의 회원 등록(회원가입)
	 * @param id
	 * @param pwd
	 * @param name
	 * @param tel
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public static HashMap joinProc(String id, String pwd, String name, String tel, String email, String companyIdx) throws Exception {
		String m = url+"/v1/api/members/member";
		HashMap<String, Object> paramMap = new HashMap();
		paramMap.put("memberId", id);
		paramMap.put("memberPw", pwd);
		paramMap.put("memberName", name);
		paramMap.put("memberTel", tel);
		paramMap.put("memberEmail", email);
		paramMap.put("companyIdx", companyIdx);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(paramMap);
	
		HashMap resultMap = sendJsonPost(m, json);
		return resultMap;
	}
	
	
	/**
	 * 로그인 프로세스
	 * @param id
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public static HashMap loginProc(String id, String pwd) throws Exception {
		String m = url+"/v1/api/members/login";
		HashMap<String, Object> paramMap = new HashMap();
		paramMap.put("memberId", id);
		paramMap.put("memberPw", pwd);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(paramMap);
	
		HashMap resultMap = sendJsonPost(m, json);
		return resultMap;
	}
	
	/**
	 * JSON GET 전송
	 * @param sendUrl
	 * @param jsonValue
	 * @return
	 * @throws Exception
	 */
	public static HashMap sendJsonGet(String sendUrl, String jsonValue) throws Exception {
		//	반환 맵
		HashMap returnMap = new HashMap();
		//	결과
		boolean result = true;
		//	데이터를 담을 맵
		HashMap dataMap = new HashMap();
		
		//	응답코드
		Integer responseCode = 0;
		
		URL url = new URL(sendUrl);
		HttpURLConnection conn = null;
						
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setRequestProperty("x-api-key", "incheon-iot-qst");
			conn.setRequestProperty("Accept", "application/json");
			
			if(jsonValue != null) {
				conn.setDoOutput(true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				bw.write(jsonValue.toString());
				bw.flush();
				bw.close();
			}
						
			if(conn.getResponseCode() == 200){
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
				String inputLine;  
				StringBuffer response = new StringBuffer();  
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);  
				}  
				in.close();
				ObjectMapper mapper = new ObjectMapper(); 
				dataMap = mapper.readValue(response.toString(), new TypeReference<HashMap<String,Object>>() {});
				responseCode = conn.getResponseCode();
			} else{
				responseCode = conn.getResponseCode();
				result = false;
			}
		} catch(Exception e) {
			log.error(e.getMessage());
			result = false;
		} finally {
			conn.disconnect();
			returnMap.put("responseCode", responseCode);
			returnMap.put("data", dataMap);
			returnMap.put("result", result);
		}
		return returnMap;
	}
	
	/**
	 * 포스트 방식 전송
	 * @param sendUrl
	 * @param jsonValue
	 * @return
	 * @throws Exception
	 */
	public static HashMap sendJsonPost(String sendUrl, String jsonValue) throws Exception {
		//	반환 맵
		HashMap returnMap = new HashMap();
		//	결과
		boolean result = true;
		//	데이터를 담을 맵
		HashMap dataMap = new HashMap();
		//	응답코드
		Integer responseCode = 0;
		URL url = new URL(sendUrl);
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setRequestProperty("x-api-key", "incheon-iot-qst");
			conn.setRequestProperty("Accept", "application/json");
			if(jsonValue != null) {
				conn.setDoOutput(true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				bw.write(jsonValue.toString());
				bw.flush();
				bw.close();
			}

			if(conn.getResponseCode() == 200){
				
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
				String inputLine;  
				StringBuffer response = new StringBuffer();  
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);  
				}  
				in.close();
				ObjectMapper mapper = new ObjectMapper(); 
				dataMap = mapper.readValue(response.toString(), new TypeReference<HashMap<String,Object>>() {});
				responseCode = conn.getResponseCode();
				//log.info("api result : " + dataMap.toString());
			} else{
				responseCode = conn.getResponseCode();
				result = false;
			}
			
		} catch(Exception e) {
			log.error(e.getMessage());
			result = false;
		} finally {
			conn.disconnect();
			returnMap.put("responseCode", responseCode);
			returnMap.put("data", dataMap);
			returnMap.put("result", result);
		}
		return returnMap;
	}


	
}
