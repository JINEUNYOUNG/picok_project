package com.tjoeun.vo;

import java.util.Date;

public class MemberVO {
	
	private int mem_idx;
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String email;
	private String addr;
	private String phone_num;
	private Date joindate;
	private char dropOK = 'n';
	private int mem_lv = 0;
	
	public MemberVO(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	

	public MemberVO(String id, String password, String nickname, String email, String addr,
			String phone_num) {
		super();
		
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.addr = addr;
		this.phone_num = phone_num;
	}

	public MemberVO(String id, String password, String name, String nickname, String email, String addr,
			String phone_num, Date joinDate) {
		super();
	
		
		this.id = id;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.addr = addr;
		this.phone_num = phone_num;
		this.joindate = joinDate;
	}


	
	public MemberVO() {}


	public int getMem_idx() {
		return mem_idx;
	}
	public void setMem_idx(int mem_idx) {
		this.mem_idx = mem_idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public Date getJoindate() {
		return joindate;
	}
	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}
	public char getDropOK() {
		return dropOK;
	}
	public void setDropOK(char dropOK) {
		this.dropOK = dropOK;
	}
	public int getMem_lv() {
		return mem_lv;
	}
	public void setMem_lv(int mem_lv) {
		this.mem_lv = mem_lv;
	}
	@Override
	public String toString() {
		return "MemberVO [mem_idx=" + mem_idx + ", id=" + id + ", password=" + password + ", name=" + name
				+ ", nickname=" + nickname + ", email=" + email + ", addr=" + addr + ", phone_num=" + phone_num
				+ ", joindate=" + joindate + ", dropOK=" + dropOK + ", mem_lv=" + mem_lv + "]";
	}

}
