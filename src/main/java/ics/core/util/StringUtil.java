package ics.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class StringUtil {
	
	private static final String salt = PropertyUtil.getProperty("aes.salt");
		
	/**
	 * 20210210
	 * @return
	 */
	public static String getToday() {
		return getFullYear()+getMonthMM()+getDayDD();
	}
	
	/**
	 * 2021-01-10
	 * @return
	 */
	public static String getTodayDash() {
		return getFullYear()+"-"+getMonthMM()+"-"+getDayDD();
	}
	
	/**
	 * 복호화
	 * @param str
	 * @return
	 */
	public static String decrypt(String str) {
		AES128 aes128 = new AES128(salt);
		return aes128.decrypt(str);
	}
	
	/**
	 * 암호화
	 * @param str
	 * @return
	 */
	public static String encrypt(String str) {
		AES128 aes128 = new AES128(salt);
		return aes128.encrypt(str);
	}
	
	
	/**
	 * - (하이픈) 있음
	 * RandomUUID 생성
	 * @return
	 */
	public static String getRandomUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * - 하이픈 제거
	 * RadndomUUID()
	 * @return
	 */
	public static String randomUUID() {
		String str = getRandomUUID();
		return str.replaceAll("-", "");
	}
	
	/**
	 * 현재년도를 구하는 함수	String
	 * ex) 2020
	 * @return
	 */
	public static String getFullYear() {
		return String.valueOf(getFullYearInt()); 
	}
	
	/**
	 * 현재년도를 구하는 함수	int
	 * @return
	 */
	public static int getFullYearInt() {
		Calendar cal = Calendar.getInstance();
		return cal.get(cal.YEAR);
	}
	
	/**
	 * 현재 달을 구하는 함수	
	 * ex) 2자리이며, 1~9월일경우 0을 붙임 09
	 * @return
	 */
	public static String getMonthMM() {
		int month = getMonthInt();
		String temp = "";
		if(month<10) {
			temp = "0"+month;
		}else {
			temp = ""+month;
		}
		return temp;
	}
	
	/**
	 * 현재 달을 구하는 함수
	 * ex) 1월~9월은 1자리 // 10~12월은 2자리	
	 * @return
	 */
	public static String getMonthString() {
		return String.valueOf(getMonthInt());
	}
	
	/**
	 * 현재 달을 구하는 함수
	 * ex) 1월~9월은 1자리 // 10~12월은 2자리
	 * @return
	 */
	public static int getMonthInt() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get (cal.MONTH) + 1 ;
		return month;
	}
	
	/**
	 * 현재 일을 구하는 함수
	 * 1~9는 1자리
	 * @return
	 */
	public static int getDayInt() {
		Calendar cal = Calendar.getInstance();
		return cal.get(cal.DATE);
	}
	
	/**
	 * 현재 일을 구하는 함수
	 * 1~9는 0을 붙여서 01~09 이며, 나머지는 2자리
	 * @return
	 */
	public static String getDayDD() {
		int date = getDayInt();
		String temp = "";
		if(date<10) {
			temp = "0"+date;
		}else {
			temp = ""+date;
		}
		return temp;
	}
	
	/**
	 * 지정한 날짜에 일수를 더함
	 * @param ymd		yyyy-MM-dd
	 * @param amount	
	 * @return
	 */
	public static String addDate(String ymd, int amount) {
		 Calendar cal = Calendar.getInstance();
	     DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	     try {
			cal.setTime(df.parse(ymd));
			cal.add(Calendar.DATE, amount);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return df.format(cal.getTime());
	}
	
	/**
	 * 	월요일 기준 - 일주일의 첫날(월요일)
	 * @param yyyyMMdd
	 * @return
	 */
	public static String getFirstDayOfWeek(String yyyyMMdd) {
		String startDt = yyyyMMdd;
		String[] dateArray = startDt.split("-");		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(dateArray[0]), (Integer.parseInt(dateArray[1]) - 1), Integer.parseInt(dateArray[2]));
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
		cal.add(Calendar.DAY_OF_MONTH, - dayOfWeek);
		
		return formatter.format(cal.getTime());
	}
	
	/**
	 * 	월요일 기준	- 일주일의 마지막날(일요일)
	 * @param yyyyMMdd
	 * @return
	 */
	public static String getLastDayOfWeek(String yyyyMMdd) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		String stDt = getFirstDayOfWeek(yyyyMMdd);
		Calendar cal = Calendar.getInstance();
		String[] dateArray = stDt.split("-");	
		cal.set(Integer.parseInt(dateArray[0]), (Integer.parseInt(dateArray[1]) - 1), Integer.parseInt(dateArray[2]));
		cal.add(Calendar.DAY_OF_MONTH, 6);
		String edDt = formatter.format(cal.getTime());
		return edDt;
	}
	
	/**
	 * 비밀번호 암호화
	 * @param password
	 * @return
	 */
	public static String getPasswordEncode(String password) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		return bc.encode(password);
	}
	
	/**
	 * 해당하는 월의 마지막 일자를 구하는 함수
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getLastDayOfMonth(Integer year, Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, 01);	//월은 -1해줘야 해당월로 인식
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 현재일자와 어제일자, 지금으로부터 1일전 데이터 가져오기
	 * @return
	 */
	public static HashMap getTodayAndYesterDay() {
		String today = null;
		Date date = new Date();
		
		// 포맷변경 ( 년월일 시분초)
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 
		// Java 시간 더하기
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		 
		today = sdformat.format(cal.getTime());
		
		cal.add(Calendar.DATE, -1);
		String yesterday  = sdformat.format(cal.getTime());
		
		HashMap dataMap = new HashMap();
		dataMap.put("endDay", today);
		dataMap.put("startDay", yesterday);
		return dataMap;
	}
	
	/**
	 * 한 시간 전 데이터 가져오기
	 * @return
	 */
	public static HashMap getBeforeOneHour() {
		String currentTime = null;
		String hourAgo = null;
		Date date = new Date();
		
		// 포맷변경 ( 년월일 시분초)
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 
		// Java 시간 더하기
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		 
		currentTime = sdformat.format(cal.getTime());
		
		cal.add(Calendar.HOUR, -1);
		hourAgo = sdformat.format(cal.getTime());
		
		HashMap dataMap = new HashMap();
		dataMap.put("endTime", currentTime);
		dataMap.put("startTime", hourAgo);
		
		return dataMap;
	} 
	
	/**
	 * 월요일 일자
	 * 일요일 일자 구하기
	 * @return
	 */
	public static HashMap getMonToSun() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //타입을 맞추기위한 객체
		Calendar ca = new GregorianCalendar();  //Calendar 객체생성

		ca.set(Calendar.YEAR, getFullYearInt());//선택 년도
		ca.set(Calendar.MONTH, getMonthInt()-1);	//월은 1을 뺌
		ca.set(Calendar.DATE, getDayInt());	//선택 일자
		
		String startDay = null;
		String endDay = null;
		String str = null;
		List<String> dayList = new ArrayList<String>();
		
		Integer c = Calendar.DAY_OF_WEEK;
		ca.set(Calendar.WEEK_OF_MONTH, ca.get(Calendar.WEEK_OF_MONTH));
		
		if(c==7) {
			
			ca.add(Calendar.DAY_OF_WEEK, -1);	// 일요일은 경우 -1을 더해서 토요일을 만듬
			
			
		}
		ca.set(Calendar.DAY_OF_WEEK, 2);  

		for(int i=0; i<7; i++) {
			
			if(i==0) {
				str = sdf.format(ca.getTime());
				startDay = str;	//	월
			}else if(i>0 && i<6 ) {
				ca.add(Calendar.DATE, 1);	// 화~토
				str = sdf.format(ca.getTime());
			}else {
				ca.add(Calendar.DATE, 1);	//	일
				str = sdf.format(ca.getTime());
				endDay = str;
			}
			
			dayList.add(str);
		}
		
		HashMap dataMap = new HashMap();
		dataMap.put("startDay", startDay);
		dataMap.put("endDay", endDay);
		dataMap.put("dayList", dayList);
		
		return dataMap;
	}
	
	/**
	 * 2022-01-11 10:05:34 ====> 2021-10-19 (20:21) 형식으로 변경
	 * @param str
	 * @return
	 */
	public static String dateFormat(String str) {
		String temp = null;
		//System.out.println(str);
		if("".equals(str) || "null".equals(str) || str == null) {
			temp = "";
			
		}else {
			String[] strArr = str.split(" ");
			String temp1 = strArr[0];
			String temp2 = strArr[1];
			
			temp = temp1 +" " +"("+temp2.substring(0, 5)+")"; 
		}
		
		
		return temp;
	}
	
	/**
	 * 포맷
	 * @param temp
	 * @return
	 * @throws Exception
	 */
    public static String strFmt(String temp) throws Exception {
    	return new String(temp.getBytes("KSC5601"),"8859_1");
    }
	
	/**
	 * 센서 승인 포맷
	 * @param str
	 * @return
	 */
	public static String sensorApprovalTextFormat(String str) {
		String text = null;
		if("Y".equals(str)) {
			text = "승인";
		}else if("N".equals(str)) {
			text = "미승인";
		}else if("R".equals(str)) {
			text = "승인 요청";
		}else if("W".equals(str)) {
			text = "등록";
		}
		return text;
	}

    public static String typeValue(String type, String value) {
    	String temp = "";
    	if(type.equals("") || type.equals("null")){
    		temp = value;
    	}else if(type.equals("INNER_TEMPERATURE") || type.equals("OUTER_TEMPERATURE")) {
    		temp = value + " ℃"; 
    	}else if(type.equals("MICRO_DUST")) {
    		temp = value + " ㎍/m"; 
    	}else if(type.equals("INNER_HUMIDITY") || type.equals("OUTER_HUMIDITY")) {
    		temp = value + " %"; 
    	}else if(type.equals("CO2")) {
    		temp = value + " ppm"; 
    	}else if(type.equals("NOISE")) {
    		temp = value + " dB"; 
    	}else if(type.equals("NOISE")) {
    		temp = value + " Lux"; 
    	}else if(type.equals("EARTHQUAKE_SI")) {
    		temp = value + " mm/sec"; 
    	}else if(type.equals("FIRE")) {
    		temp = value + " ℃"; 
    	}else if(type.equals("LEFT_DOOR") || type.equals("RIGHT_DOOR")) {
    		temp = "0".equals(value) ? "닫힘" : "열림";
    	}else {
    		temp = value;
    	}
    	
    	
    	return temp;
    }
	
}
