package com.care.boot.animal_find;
/*
 create table animal_find(
no number primary key,
title varchar2(100) not null,
id varchar2(20) not null,
time varchar2(15) not null,
content varchar(200) not null,
image varchar2(255),
animal_group varchar2(20) not null,
area varchar2(50) not null,
tel varchar2(15) not null,
cate varchar2(20) not null,
money varchar2(20));
*/
public class Animal_find_DTO {
	private int no;
	private String title;//제목
	private String id;//작성자
	private String time;//시간
	private String content;//내용
	private String image;//이미지
	private String image2;//이미지
	private String image3;//이미지
	private String animal_group;//동물 그룹
	private String area;//지역
	private String tel;//전화번호
	private String cate;//긴급여부
	private String money;//돈
	private String name;//동물 이름
	private String age;//동물 나이
	private String sex;//동물 성별
	private String day;//실종날짜
	private String areatext;//상세 주소
	
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
	public String getAnimal_group() {
		return animal_group;
	}
	public void setAnimal_group(String animal_group) {
		this.animal_group = animal_group;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getAreatext() {
		return areatext;
	}
	public void setAreatext(String areatext) {
		this.areatext = areatext;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	
	
}
