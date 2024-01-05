package ics.batch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ics.batch.dto.BatchAlarmLogListDTO;
import ics.batch.dto.BatchAlarmMemberListDTO;
import ics.fcm.FcmService;
import ics.fcm.dto.FcmResultDTO;
import lombok.extern.slf4j.Slf4j;



@Component
@Slf4j
public class BatchAlarmController {
	
	@Autowired
	private BatchAlarmService batchAlarmService;
	
	@Autowired
	private FcmService fcmService;
	
	@Scheduled(fixedDelay = 5000)
	public void cronRun() {
		 
		log.debug("===== 배치 시작 : " + LocalDateTime.now().toString() + " =====");
		//	배치 시작 컴퍼니 인덱스
		
		List pushList = new ArrayList();
		
		try{
			Integer companyIdx = 2;
			
			//	에러 알람 목록 조회
			List<BatchAlarmLogListDTO> alarmLogList = batchAlarmService.getAlarmLogList(companyIdx);
			
			//	알람 설정한 사람 조회
			List<BatchAlarmMemberListDTO> memberList = batchAlarmService.getAlarmMemberList(companyIdx);
			for(int i=0; i<alarmLogList.size(); i++) {
				HashMap logMap = new HashMap();
				
				BatchAlarmLogListDTO alarm = alarmLogList.get(i);
				
				Integer success = 0;
				Integer fail = 0;
				Integer alarmIdx = alarm.getAlarmIdx();
				
				//log.info("알람 전송 데이터 : " + (i+1) + " " + alarm.toString());
				
				for(int j=0; j<memberList.size(); j++) {
					BatchAlarmMemberListDTO member =  memberList.get(j);
					
					
					String	alarmMessage = alarm.getAlarmMessage();
					String	sensorTypeName = alarm.getSensorTypeName();
					String 	sensorLocationDescription = alarm.getSensorLocationDescription();
					String	stationName = alarm.getStationName();
					String	alarmDate = alarm.getAlarmCreateDate();
					
					String	memberId = member.getMemberId();
					String 	token = member.getMobileToken();
					//	외부습도이탈(인천대입구역-함체3 외부습도) 2022-03-07 (08:03)
					
					//String	title = sensorTypeName+" 이탈" + "(" + sensorLocationDescription + ")" + " " + alarmDate;
					String title = alarmDate + " " + sensorLocationDescription + " 이탈"; 
					FcmResultDTO res = fcmService.sendMessage(token, title, alarmMessage);
					success = success + res.getSuccess();
					fail = fail + res.getFail();
				}
				
				logMap.put("alarmIdx", alarmIdx);
				logMap.put("success", success);
				logMap.put("fail", fail);
				pushList.add(logMap);
				
				//	전송된 알람 업데이트
				batchAlarmService.alarmStatudUpdate(alarmIdx);
				
				
			}
						
		}catch(Exception e) {
			
			log.debug("===== 배치 에러 : " + LocalDateTime.now().toString() + " =====");
			log.debug(e.getMessage());
			
		}finally {
			log.debug("===== 배치 결과 : " + pushList.size() +"개" + " =====");
			log.debug("===== 배치 결과 : " + pushList.toString() + " =====");
			
			log.debug("===== 배치 종료 : " + LocalDateTime.now().toString() + " =====");
		}
		
	}
	
}
