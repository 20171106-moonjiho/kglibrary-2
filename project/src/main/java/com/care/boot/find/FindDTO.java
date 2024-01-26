package com.care.boot.find;

import java.util.List;

/**
 * 찾기 인증 게시판
 */
public class FindDTO {
	
	private int no;	
	private String image;		//메인 사진
	private String title;
	private String content;
	private String id;
	private String time;
	private String image2;
	private String species;		//동물분류 (CAT/DOG/OTHER)
	private String species_kr;	//동물분류 한글
	private boolean isSameImage;	//글 수정 시 이미지 변경 여부
	private boolean isSameImage2;	//글 수정 시 이미지2 변경 여부
	List<CommentDTO> comments;	//댓글 리스트
	
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getSpecies_kr() {
		return species_kr;
	}
	public void setSpecies_kr(String species_kr) {
		this.species_kr = species_kr;
	}
	public boolean isSameImage() {
		return isSameImage;
	}
	public void setSameImage(boolean isSameImage) {
		this.isSameImage = isSameImage;
	}
	public boolean isSameImage2() {
		return isSameImage2;
	}
	public void setSameImage2(boolean isSameImage2) {
		this.isSameImage2 = isSameImage2;
	}
	public List<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "FindDTO [no=" + no + ", image=" + image + ", title=" + title + ", content=" + content + ", id=" + id
				+ ", time=" + time + ", image2=" + image2 + ", species=" + species + ", species_kr=" + species_kr
				+ ", isSameImage=" + isSameImage + ", isSameImage2=" + isSameImage2 + ", comments=" + comments + "]";
	}
	

	
	

}
