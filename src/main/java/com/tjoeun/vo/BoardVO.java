package com.tjoeun.vo;

import java.util.Date;

public class BoardVO {
	
	private int board_idx;
    private String id;
	private String category;
	private String board_title;
	private String board_content;
	private String file_name;
	private Date board_reg_date;
	private int view_num=0;
	private int likes_num=0;
	private int report_num =0;
	private char deleteOK = 'n';
	private char secretOK = 'n';
	

	public int getBoard_idx() {
		return board_idx;
	}
	
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getBoard_title() {
		return board_title;
	}
	
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	
	public String getBoard_content() {
		return board_content;
	}
	
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	
	public String getFile_name() {
		return file_name;
	}
	
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	
	public Date getBoard_reg_date() {
		return board_reg_date;
	}
	
	public void setBoard_reg_date(Date board_reg_date) {
		this.board_reg_date = board_reg_date;
	}
	
	public int getView_num() {
		return view_num;
	}
	
	public void setView_num(int view_num) {
		this.view_num = view_num;
	}
	
	public int getLikes_num() {
		return likes_num;
	}
	
	public void setLikes_num(int likes_num) {
		this.likes_num = likes_num;
	}
	
	public int getReport_num() {
		return report_num;
	}
	
	public void setReport_num(int report_num) {
		this.report_num = report_num;
	}
	
	public char getDeleteOK() {
		return deleteOK;
	}
	
	public void setDeleteOK(char deleteOK) {
		this.deleteOK = deleteOK;
	}
	
	public char getSecretOK() {
		return secretOK;
	}
	
	public void setSecretOK(char secretOK) {
		this.secretOK = secretOK;
	}

	@Override
	public String toString() {
		return "BoardVO [board_idx=" + board_idx + ", id=" + id + ", category=" + category + ", board_title="
				+ board_title + ", board_content=" + board_content + ", file_name=" + file_name + ", board_reg_date="
				+ board_reg_date + ", view_num=" + view_num + ", likes_num=" + likes_num + ", report_num=" + report_num
				+ ", deleteOK=" + deleteOK + ", secretOK=" + secretOK + "]";
	}


	

	
	

}
