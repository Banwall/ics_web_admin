package ics.qst.mapper;

import ics.qst.vo.AlarmLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlarmLogMapper {
	
	public List<AlarmLog> getAlarmLogList(AlarmLog alarmLog);

	public List<AlarmLog> getCodeNmList();

	public void addAgentAlarm();

	public void updateAlarmFlag(String alarmIdx);
}
