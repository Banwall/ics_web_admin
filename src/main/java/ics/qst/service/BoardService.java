package ics.qst.service;

import ics.qst.mapper.BoardMapper;
import ics.qst.mapper.DeviceMapper;
import ics.qst.vo.Board;
import ics.qst.vo.DeviceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public void saveBoard(Board board) {
        String flag = board.getFlag();

        if(flag.equals("save")) {
            // 게시판 신규 등록
            boardMapper.addBoard(board);
        } else {
            // 게시판 수정
            boardMapper.modifyBoard(board);
        }

    }

    public List<Board> getBoardList() {

        return boardMapper.getBoardList();
    }

    public Board getBoardDetail(int idx) {

        return boardMapper.getBoardDetail(idx);
    }
}