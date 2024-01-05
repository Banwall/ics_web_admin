package ics.operator.cctv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ics.operator.cctv.dto.CctvListReqDTO;
import ics.operator.cctv.dto.CctvListResDTO;
import ics.operator.cctv.dto.StationListResDTO;

@Service
public class OperatorCctvService {
	
	@Autowired
	private OperatorCctvMapper operatorCctvMapper;

	/**
	 * 역 리스트 조회
	 * @return
	 * @throws Exception
	 */
	public List<StationListResDTO> getStationList() throws Exception {
		return operatorCctvMapper.getStationList();
	}

	/**
	 * cctv 리스트 조회
	 * @param cctvListReqDTO 
	 * @return
	 * @throws Exception
	 */
	public List<CctvListResDTO> getCctvList(CctvListReqDTO cctvListReqDTO) throws Exception {
		return operatorCctvMapper.getCctvList(cctvListReqDTO);
	}

}
