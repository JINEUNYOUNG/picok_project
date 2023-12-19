package com.tjoeun.vo;

import java.util.Date;

public class ChatVO {
	
	private int chat_idx;
    private String from_id; //접속자
    private String to_id; //대화상대 
    private Date chatdate;
    private String exit_id; //나간 유저의 아이디를 기억
    private MessageVO latest;

    
    public ChatVO() {
		// TODO Auto-generated constructor stub
	}

	public int getChat_idx() {
		return chat_idx;
	}

	public void setChat_idx(int chat_idx) {
		this.chat_idx = chat_idx;
	}

	public String getFrom_id() {
		return from_id;
	}

	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}

	public String getTo_id() {
		return to_id;
	}

	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}

	public Date getChatdate() {
		return chatdate;
	}

	public void setChatdate(Date chatdate) {
		this.chatdate = chatdate;
	}

	public String getExit_id() {
		return exit_id;
	}

	public void setExit_id(String exit_id) {
		this.exit_id = exit_id;
	}


	@Override
	public String toString() {
		return "ChatVO [chat_idx=" + chat_idx + ", from_id=" + from_id + ", to_id=" + to_id + ", chatdate=" + chatdate
				+ ", exit_id=" + exit_id + ", latest=" + latest + "]";
	}

	public MessageVO getLatest() {
		return latest;
	}

	public void setLatest(MessageVO latest) {
		this.latest = latest;
	}


}
