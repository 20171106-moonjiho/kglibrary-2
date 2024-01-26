package com.care.boot.managementBoard;
/*
create table management(
no number primary key,
title varchar2(100) not null,
id varchar2(20) not null,
time varchar2(15) not null,
content varchar(200) not null,
image varchar2(255));
*/


public class ManagementBoardDTO {
	private int no;
	private String title;
	private String id;
	private String time;
	private String content;
	private String image;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
