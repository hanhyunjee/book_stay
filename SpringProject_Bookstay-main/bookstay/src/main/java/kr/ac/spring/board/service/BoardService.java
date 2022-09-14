package kr.ac.spring.board.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.spring.board.dao.BoardDAO;
import kr.ac.spring.board.vo.BoardVO;
import kr.ac.spring.board.vo.Pagination;

@Service("boardService")
public class BoardService {
	@Autowired
	BoardDAO boardDAO;
	
	public List<BoardVO> listBoards(Pagination pagination) throws Exception{
		List<BoardVO> boardsList =  boardDAO.selectAllBoardList(pagination);
        return boardsList;
	}

	public int addBoard(BoardVO boardVO) throws Exception{
		return boardDAO.addBoard(boardVO);
	}

	public int updateBoard(BoardVO boardVO) throws Exception {
		return boardDAO.updateBoard(boardVO);
	}

	public BoardVO viewBoard(int boardId) throws Exception{
		return boardDAO.viewBoard(boardId);
	}

	public int deleteBoard(Map<String, Object> parameters) throws Exception{
		return boardDAO.deleteBoard(parameters);
	}
	
	public int getBoardListCnt() throws Exception{
		return boardDAO.getBoardListCnt();
	}
	
	public void increaseViewCnt(int boardId, HttpSession session) throws Exception{
		long update_time = 0;
		// ���ǿ� ����� ��ȸ�ð� �˻�
		//���ʷ� ��ȸ�� ��� ���ǿ� ����� ���� ���� ������ if���� ���� ���� �ʴ´�.
		if(session.getAttribute("update_time_" + boardId) != null)
			update_time = (long)session.getAttribute("update_time_" + boardId);
		//�ý����� ���� �ð��� current_time�� ���� 
		long current_time = System.currentTimeMillis();
		//���� �ð��� ��� �� ��ȸ �� ���� ó�� 24*60*60*1000 (24�ð�)
		//�ý��� ����ð� - ���� �ð� > ���� �ð� (��ȸ�� ������ �����ϵ��� ������ �ð�)
		if(current_time - update_time > 5 * 1000) {
			boardDAO.increaseViewCnt(boardId);
			//���ǿ� �ð��� ����
			session.setAttribute("update_time_"+boardId, current_time);
		}
	}

	public int replyBoard(BoardVO boardVO) {
		return boardDAO.replyBoard(boardVO);
	}

	public int getBoardListCntById(String id) {
		// TODO Auto-generated method stub
		return boardDAO.getBoardListCntById(id);
	}

	public List<BoardVO> listBoardById(Pagination pagination, String id) {
		// TODO Auto-generated method stub
		List<BoardVO> boardsList =  boardDAO.selectAllBoardListById(pagination, id);
        return boardsList;
	}
}
