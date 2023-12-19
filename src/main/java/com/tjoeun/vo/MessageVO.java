package com.tjoeun.vo;

import java.util.Date;

public class MessageVO {
	
	private int message_idx;
	private int chat_idx;
    private String write_id;
    private String write_content;
    private Date writedate;
    private Date readdate;
    
    public MessageVO() {
		// TODO Auto-generated constructor stub
	}

	public int getMessage_idx() {
		return message_idx;
	}

	public void setMessage_idx(int message_idx) {
		this.message_idx = message_idx;
	}

	public int getChat_idx() {
		return chat_idx;
	}

	public void setChat_idx(int chat_idx) {
		this.chat_idx = chat_idx;
	}

	public String getWrite_id() {
		return write_id;
	}

	public void setWrite_id(String write_id) {
		this.write_id = write_id;
	}

	public String getWrite_content() {
		return write_content;
	}

	public void setWrite_content(String write_content) {
		this.write_content = write_content;
	}

	public Date getWritedate() {
		return writedate;
	}

	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}

	public Date getReaddate() {
		return readdate;
	}

	public void setReaddate(Date readdate) {
		this.readdate = readdate;
	}

	@Override
	public String toString() {
		return "MessageVO [message_idx=" + message_idx + ", chat_idx=" + chat_idx + ", write_id=" + write_id
				+ ", write_content=" + write_content + ", writedate=" + writedate + ", readdate=" + readdate + "]";
	}

    
    
}
