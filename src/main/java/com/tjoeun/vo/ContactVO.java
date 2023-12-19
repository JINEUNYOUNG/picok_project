package com.tjoeun.vo;

import java.util.ArrayList;
import java.util.Date;

public class ContactVO {

    private ArrayList<ContactVO> list;
	private int idx;
	private String category;
    private String id;
    private String email;
    private String title;
	private String content;
	private Date writeDate;
	private String answer;
	@Override
	public String toString() {
		return "ContactVO [idx=" + idx + ", category=" + category + ", id=" + id + ", email=" + email + ", title="
				+ title + ", content=" + content + ", writeDate=" + writeDate + ", answer=" + answer + ", answerDate="
				+ answerDate + "]";
	}

	private Date answerDate;

	public ContactVO() {
		// TODO Auto-generated constructor stub
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ArrayList<ContactVO> getList() {
		return list;
	}

	public void setList(ArrayList<ContactVO> arrayList) {
		this.list = arrayList;
	}

}
