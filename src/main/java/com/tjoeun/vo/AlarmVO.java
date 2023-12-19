package com.tjoeun.vo;

import java.util.Date;

public class AlarmVO {
	
	private int alarm_idx;
    private String to_id;
    private String from_id;
    private int board_idx;
    private String board_title;
	private String category;
	private Date alarmdate;
	
	public AlarmVO() {
		// TODO Auto-generated constructor stub
	}

	public int getAlarm_idx() {
		return alarm_idx;
	}

	public void setAlarm_idx(int alarm_idx) {
		this.alarm_idx = alarm_idx;
	}

	public String getTo_id() {
		return to_id;
	}

	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}

	public String getFrom_id() {
		return from_id;
	}

	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}

	public int getBoard_idx() {
		return board_idx;
	}

	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getAlarmdate() {
		return alarmdate;
	}

	public void setAlarmdate(Date alarmdate) {
		this.alarmdate = alarmdate;
	}



	public String getBoard_title() {
		return board_title;
	}

	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}

	@Override
	public String toString() {
		return "AlarmVO [alarm_idx=" + alarm_idx + ", to_id=" + to_id + ", from_id=" + from_id + ", board_idx="
				+ board_idx + ", board_title=" + board_title + ", category=" + category + ", alarmdate=" + alarmdate
				+ "]";
	}
	
	
	

}
