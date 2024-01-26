package com.care.boot.askBoard;
/*
create table ask_reply(
no number primary key,
page_no number not null,
id varchar2(20) not null,
time varchar2(15) not null,
content varchar(200) not null);
*/


public class AskReplyDTO {
	private int no;
	private int page_no;
	private String id;
	private String time;
	private String content;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getPage_no() {
		return page_no;
	}
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	

}
