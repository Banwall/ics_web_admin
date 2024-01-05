package ics.batch;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ics.batch.dto.BatchAlarmLogListDTO;
import ics.batch.dto.BatchAlarmMemberListDTO;

@Mapper
public interface BatchAlarmMapper {

	public List<BatchAlarmLogListDTO> getAlarmLogList(Integer companyIdx) throws Exception;

	public List<BatchAlarmMemberListDTO> getAlarmMemberList(Integer companyIdx) throws Exception;

	public void alarmStatudUpdate(Integer alarmIdx) throws Exception;

}
