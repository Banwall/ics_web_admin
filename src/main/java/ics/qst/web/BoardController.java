package ics.qst.web;

import ics.qst.service.BoardService;
import ics.qst.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/qst/board")
public class BoardController {

	@Autowired
	public BoardService boardService;

	@RequestMapping("/reportBoardList")
	public ModelAndView reportBoardList(ModelAndView mv) {

		List<Board> boardList = boardService.getBoardList();

		mv.addObject("boardList", boardList);
		mv.setViewName("qst/main/reportBoard");

		return mv;
	}

	@GetMapping("/writeBoard")
	public ModelAndView writeBoard(@RequestParam(value="idx", required = false, defaultValue = "0") int idx, ModelAndView mv) {

		Board board = new Board();

		if(idx == 0) {
			//글 작성
			board.setIdx(0);
		} else {
			//글 상세보기
			board = boardService.getBoardDetail(idx);
		}
		mv.addObject("board", board);
		mv.setViewName("qst/main/writeBoard");

		return mv;
	}

	@PostMapping("/writeBoard")
	@ResponseBody
	public Map<String, Object> writeBoard(@RequestBody Board board) {

		Map<String, Object> map = new HashMap<>();

		try {
			boardService.saveBoard(board);

			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "error");
			e.printStackTrace();
		}

		return map;
	}

}