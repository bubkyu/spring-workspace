package com.kh.spring.board.model.service;

import java.util.ArrayList;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {

	//1. 게시판 리스트 조회용 서비스
	public abstract int selectListCount();
	ArrayList<Board> selectList(PageInfo pi);
	
	//2. 게시판 작성용 서비스
	int insertBoard(Board b); 
		
	//3. 게시판 상세조회용 서비스
	int increaseCount(int bno); 
	Board selectBoard(int bno);
	
	//4. 게시판 수정용 서비스
	int updateBoard(Board b); 
	
	//5. 게시판 삭제용 서비스
	int deleteBoard(int bno); 
	
	//6. 댓글 리스트 조회용 서비스
	ArrayList<Reply> selectReplyList(int bno); 
	
	//7. 댓글 작성용 서비스
	int insertReply(Reply r); 
}