package com.fh.controller.lottery.complain;

import java.io.Serializable;

public class ReplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public String userName;
	public String reply;
	public Integer time;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ReplyEntity [userName=" + userName + ", reply=" + reply + ", time=" + time + "]";
	}

	public ReplyEntity(String userName, String reply, Integer time) {
		super();
		this.userName = userName;
		this.reply = reply;
		this.time = time;
	}

	public ReplyEntity() {
	}

}
