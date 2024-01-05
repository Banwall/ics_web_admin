package ics.qst.mapper;

import ics.qst.vo.Station;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StationMapper {

	public List<Station> getStationList();
}