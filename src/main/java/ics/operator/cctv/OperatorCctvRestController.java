package ics.operator.cctv;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ics.operator.cctv.dto.CctvListReqDTO;
import ics.operator.cctv.dto.CctvListResDTO;

@RestController
@RequestMapping("/operator")
public class OperatorCctvRestController {
	
	@Autowired
	private OperatorCctvService operatorCctvService;

	@RequestMapping("/cctv/list")
	public HashMap cctvList(@ModelAttribute CctvListReqDTO cctvListReqDTO )throws Exception{
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		List<CctvListResDTO> cctvList = operatorCctvService.getCctvList(cctvListReqDTO);
		dataMap.put("cctvList", cctvList);

		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
}
