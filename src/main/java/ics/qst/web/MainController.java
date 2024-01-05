package ics.qst.web;

import ics.qst.service.DeviceService;
import ics.qst.vo.DeviceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/qst")
public class MainController {

	@Autowired
	public DeviceService deviceService;

	@RequestMapping("/main")
	public ModelAndView main(ModelAndView mv) {

		String value = "2021";

		List<String> installationList = deviceService.getInstallationList();
		List<DeviceList> grpNmList = deviceService.getGrpNmList(value);
		List<DeviceList> serverUsageList = deviceService.getServerUsage(value);
		List<DeviceList> deviceList = deviceService.getDeviceList(value);
		// 차트 함수 List<DeviceList> dataList = deviceService.getDataList(value);

		mv.addObject("installationValue", value);
		mv.addObject("installationList" , installationList);
		mv.addObject("grpNmList" , grpNmList);
		mv.addObject("serverUsageList" , serverUsageList);
		mv.addObject("deviceList" , deviceList);
		mv.setViewName("qst/main/main");
		// 차트 함수 mv.addObject("dataList" , dataList);

		return mv;
	}

	@RequestMapping("/getDeviceAndServer")
	public String getDeviceAndServer(@RequestParam(value="value") String value, Model model) {

		List<String> installationList = deviceService.getInstallationList();
		List<DeviceList> grpNmList = deviceService.getGrpNmList(value);
		List<DeviceList> serverUsageList = deviceService.getServerUsage(value);
		List<DeviceList> deviceList = deviceService.getDeviceList(value);
		// 차트 함수 List<DeviceList> dataList = deviceService.getDataList(value);

		model.addAttribute("installationValue", value);
		model.addAttribute("installationList" , installationList);
		model.addAttribute("grpNmList", grpNmList);
		model.addAttribute("serverUsageList" , serverUsageList);
		model.addAttribute("deviceList" , deviceList);
		// 차트 함수 model.addAttribute("dataList", dataList);

		return "qst/main/main :: #cardDiv";
	}

	@RequestMapping("/getDeviceGrpNmList")
	@ResponseBody
	public Map<String, Object> getDeviceGrpNmList() {

		Map<String, Object> map = new HashMap<>();

		List<DeviceList> grpNmList = deviceService.getAllGrpNmList();

		map.put("data", grpNmList);

		return map;
	}

	@RequestMapping("/addDevice")
	@ResponseBody
	public Map<String, Object> addDevice(@RequestBody DeviceList deviceList) {

		Map<String, Object> map = new HashMap<>();
		try {
			deviceService.addDevice(deviceList);
			map.put("result", "200");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "error");
		}
		return map;
	}

	@RequestMapping("/fiveMinReload")
	public String fiveMinReload(@RequestParam(value="value") String value, Model model) {

		List<String> installationList = deviceService.getInstallationList();
		List<DeviceList> grpNmList = deviceService.getGrpNmList(value);
		List<DeviceList> serverUsageList = deviceService.getServerUsage(value);
		List<DeviceList> deviceList = deviceService.getDeviceList(value);
		// 차트 함수 List<DeviceList> dataList = deviceService.getDataList(value);

		model.addAttribute("installationValue", value);
		model.addAttribute("installationList" , installationList);
		model.addAttribute("grpNmList", grpNmList);
		model.addAttribute("serverUsageList" , serverUsageList);
		model.addAttribute("deviceList" , deviceList);
		// 차트 함수 model.addAttribute("dataList", dataList);

		return "qst/main/main :: #cardDiv";
	}
}