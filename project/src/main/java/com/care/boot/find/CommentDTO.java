package com.care.boot.find;

/**
 * 찾기 인증 게시판 > 댓글
 */
public class CommentDTO {
	private int no;	//댓글 번호
	private Integer certification_no; //인증게시판 글번호
	private String name;		//이름
	private String password;	//비밀번호
	private String content;		//댓글 내용
	private String created_at;	//작성일
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public Integer getCertification_no() {
		return certification_no;
	}
	public void setCertification_no(Integer certification_no) {
		this.certification_no = certification_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	@Override
	public String toString() {
		return "CommentDTO [no=" + no + ", certification_no=" + certification_no + ", name=" + name + ", password="
				+ password + ", content=" + content + ", created_at=" + created_at + "]";
	}
	
	
}
