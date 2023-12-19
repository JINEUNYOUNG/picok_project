package com.tjoeun.vo;

import java.util.Date;

public class BoardCommentVO {
	
	private int board_idx;
	private int comment_idx;
	private String id;
	private String nickname;
	private String comment_content;
	private Date comment_reg_Date;
	
	public BoardCommentVO() { }

	public int getBoard_idx() {
		return board_idx;
	}

	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}

	public int getComment_idx() {
		return comment_idx;
	}

	public void setComment_idx(int comment_idx) {
		this.comment_idx = comment_idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public Date getComment_ceg_Date() {
		return comment_reg_Date;
	}

	public void setComment_ceg_Date(Date comment_ceg_Date) {
		this.comment_reg_Date = comment_ceg_Date;
	}

	@Override
	public String toString() {
		return "BoardCommentVO [board_idx=" + board_idx + ", comment_idx=" + comment_idx + ", id=" + id + ", nickname="
				+ nickname + ", comment_content=" + comment_content + ", comment_ceg_Date=" + comment_reg_Date + "]";
	}


}
