package ics.qst.mapper;

import ics.qst.vo.Board;
import ics.qst.vo.DeviceList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

	public void addBoard(Board board);

	public List<Board> getBoardList();

	public Board getBoardDetail(int idx);

	public void modifyBoard(Board board);
}