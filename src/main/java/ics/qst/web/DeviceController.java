package ics.qst.web;

import ics.qst.service.DeviceService;
import ics.qst.vo.DeviceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/qst/device/")
public class DeviceController {

	@Autowired
	public DeviceService deviceService;
	
	@RequestMapping("getDevice")
	@ResponseBody
	public List<DeviceList> getDevice(@RequestParam(value="grpCd" , required = false) String grpCd ,
									  @RequestParam(value="code" , required = false) int code ,
									  @RequestParam(value="flag" , required = false) String flag , Model model) {
		
		DeviceList deviceList = new DeviceList();
		deviceList.setGrpCd(grpCd);
		deviceList.setCode(code);
		deviceList.setFlag(flag);

		return deviceService.getDevice(deviceList);
	}

	@RequestMapping("deviceList")
	@ResponseBody
	public ModelAndView deviceList(ModelAndView mv) {

		List<DeviceList> grpNmList = deviceService.getGrpNmList(null);
		List<String> installationList = deviceService.getInstallationList();

		mv.addObject("installationList" , installationList);
		mv.addObject("grpNmList", grpNmList);
		mv.setViewName("qst/main/deviceList");

		return mv;
	}

	@RequestMapping("getDeviceListByGrpCdAndInstallation")
	@ResponseBody
	public Map<String, Object> getDeviceListByGrpCdAndInstallation(@RequestParam(value = "grpCd") String grpCd,
																	@RequestParam(value = "installation") String installation) {
		Map<String, Object> map = new HashMap<>();
		DeviceList deviceList = new DeviceList();

		deviceList.setGrpCd(grpCd);
		deviceList.setInstallation(installation);

		List<DeviceList> deviceLists = deviceService.getDeviceListByGrpCdAndInstallation(deviceList);

		map.put("data", deviceLists);

		return map;
	}

	@RequestMapping("getGrpNmListByInstallation")
	@ResponseBody
	public Map<String, Object> getGrpNmListByInstallation(@RequestParam(value = "installation", required = false) String installation) {
		Map<String, Object> map = new HashMap<>();

		if(installation.equals("")) {
			installation = null;
		}

		List<DeviceList> grpNmList = deviceService.getGrpNmList(installation);

		map.put("data", grpNmList);

		return map;
	}

	@RequestMapping("saveDeviceList")
	@ResponseBody
	public Map<String, Object> saveDeviceList(@RequestBody DeviceList deviceList) {
		Map<String, Object> map = new HashMap<>();

		try {
			deviceService.saveDeviceList(deviceList.getDeviceList());
			map.put("result" , "200");
		} catch (Exception e) {
			map.put("result" , "error");
			e.printStackTrace();
		}
		return map;
	}
}