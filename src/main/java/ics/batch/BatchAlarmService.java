package ics.batch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ics.batch.dto.BatchAlarmLogListDTO;
import ics.batch.dto.BatchAlarmMemberListDTO;

@Service
public class BatchAlarmService {

	@Autowired
	private BatchAlarmMapper batchAlarmMapper;
	
	/**
	 * 알람 로그 조회
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<BatchAlarmLogListDTO> getAlarmLogList(Integer companyIdx) throws Exception {
		return batchAlarmMapper.getAlarmLogList(companyIdx);
	}

	/**
	 * 알람 설정한 사람 조회
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<BatchAlarmMemberListDTO> getAlarmMemberList(Integer companyIdx) throws Exception {
		return batchAlarmMapper.getAlarmMemberList(companyIdx);
	}

	/**
	 * 알람 전송 업데이트
	 * @param alarmIdx
	 * @throws Exception
	 */
	public void alarmStatudUpdate(Integer alarmIdx) throws Exception{
		batchAlarmMapper.alarmStatudUpdate(alarmIdx);
	}

}
