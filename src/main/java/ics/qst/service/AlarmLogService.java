package ics.qst.service;

import ics.qst.mapper.AlarmLogMapper;
import ics.qst.vo.AlarmLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmLogService {

	@Autowired
	private AlarmLogMapper alarmLogMapper;

	public List<AlarmLog> getAlarmLogList(AlarmLog alarmLog) {

		alarmLog.setEndDate(alarmLog.getEndDate() + " 23:59:59.999");
		
		return alarmLogMapper.getAlarmLogList(alarmLog);
	}

	public List<AlarmLog> getCodeNmList() {
		return alarmLogMapper.getCodeNmList();
	}

	public void addAgentAlarm() {
		alarmLogMapper.addAgentAlarm();
	}

	public void updateAlarmFlag(String alarmIdx) {
		alarmLogMapper.updateAlarmFlag(alarmIdx);
	}
}