package com.tjoeun.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.vo.MemberVO;

public interface MemberService {
	
	public abstract String execute(MemberVO memberVO,HttpServletRequest request) ;
	public abstract void execute(Model model) ;
	void execute(MemberVO memberVO);

}
