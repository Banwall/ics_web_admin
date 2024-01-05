package ics.qst.web;

import ics.qst.service.AlarmLogService;
import ics.qst.vo.AlarmLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/alarm/")
public class AlarmLogController {

	@Autowired
	public AlarmLogService alarmLogService;
	
	@RequestMapping("list")
	public ModelAndView alarmList(ModelAndView mv) {
		
		List<AlarmLog> codeNmList = alarmLogService.getCodeNmList();
		
		mv.addObject("codeNmList" , codeNmList);
		mv.setViewName("qst/alarm/list");
		return mv;
	}
	
	@RequestMapping("alarmLogList")
	@ResponseBody
	public List<AlarmLog> alarmLogList(@RequestBody AlarmLog alarmLog , ModelAndView mv) {
		
		List<AlarmLog> alarmLogList = alarmLogService.getAlarmLogList(alarmLog);
		
		return alarmLogList;
	}
	
	@RequestMapping("addAgentAlarm")
	@ResponseBody
	public String addAgentAlarm() {
		
		alarmLogService.addAgentAlarm();
		
		return "";
	}
	
	@RequestMapping("updateAlarmFlag")
	@ResponseBody
	public String updateAlarmFlag(@RequestBody String alarmIdx) {
		
		alarmLogService.updateAlarmFlag(alarmIdx);
		
		return "알람을 확인했습니다.";
	}
}