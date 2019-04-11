package entity;

import java.util.Date;

public class ChattingRecord {
	private String time;
	private String username;
	private String content;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "time=" + time + ", username=" + username + ", content=" + content;
	}
}
