package com.care.boot.member;

/*create table member(
no number not null,
name varchar2(30) not null,
id varchar2(20) not null primary key,
pw varchar2(12) not null,
tel varchar2(20) not null,
email varchar2(50) not null);
*/

public class MemberDTO {

	private int no;
	private String name;
	private String id;
	private String pw;
	private String confirm;
	private String tel;
	private String email;
	private String address;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	
	
}
