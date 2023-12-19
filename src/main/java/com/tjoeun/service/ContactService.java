package com.tjoeun.service;

import org.springframework.ui.Model;

import com.tjoeun.vo.ContactVO;

public interface ContactService {

	public abstract void execute(ContactVO contactVO,Model model) ;
	public abstract void execute(Model model) ;

}
