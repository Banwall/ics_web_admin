package ics.operator.cctv;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ics.operator.cctv.dto.CctvListReqDTO;
import ics.operator.cctv.dto.CctvListResDTO;
import ics.operator.cctv.dto.StationListResDTO;

@Mapper
public interface OperatorCctvMapper {

	/**
	 * 역 리스트 조회
	 * @return
	 * @throws Exception
	 */
	public List<StationListResDTO> getStationList() throws Exception;

	/**
	 * cctv 리스트 조회
	 * @param cctvListReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<CctvListResDTO> getCctvList(CctvListReqDTO cctvListReqDTO) throws Exception;

}
