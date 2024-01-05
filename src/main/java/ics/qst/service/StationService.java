package ics.qst.service;

import ics.qst.mapper.StationMapper;
import ics.qst.vo.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

	@Autowired
	private StationMapper stationMapper;

	public List<Station> getStationList() {

		return stationMapper.getStationList();
	}
}