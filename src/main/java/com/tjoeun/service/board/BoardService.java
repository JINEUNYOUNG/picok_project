package com.tjoeun.service.board;

import org.springframework.ui.Model;

import com.tjoeun.vo.BoardVO;

public interface BoardService {
	
	public abstract void execute(BoardVO boardVO) ;
	public abstract void execute(Model model) ;



}
