package ics.operator.cctv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ics.operator.cctv.dto.CctvListReqDTO;
import ics.operator.cctv.dto.CctvListResDTO;
import ics.operator.cctv.dto.StationListResDTO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/operator")
@Slf4j
public class OperatorCctvController {
	
	@Autowired
	private OperatorCctvService operatorCctvService;
	
	@RequestMapping("/cctv")
	public String main(Model model)throws Exception{
		List<StationListResDTO> stationList = operatorCctvService.getStationList();
		
		StationListResDTO dto = stationList.get(0);
		
		CctvListReqDTO cctvListReqDTO = CctvListReqDTO.builder()
				.stationIdx(dto.getStationIdx())
				.build();
		
		List<CctvListResDTO> cctvList = operatorCctvService.getCctvList(cctvListReqDTO);
		
		model.addAttribute("stationList", stationList);
		model.addAttribute("cctvList",cctvList);
		return "operator/cctv/operatorCctv";
	}

}
