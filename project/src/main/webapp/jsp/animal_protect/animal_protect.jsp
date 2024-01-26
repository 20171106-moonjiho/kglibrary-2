<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />
  
<style>
	.animal_find{width: 1200px; margin: 0 auto; margin-top:100px;}
	.table_box{width: 280px; border: 1px solid #dedede; padding: 5px;}
	.table_box>img{width: 282px; height: 317px;}
	.table_box_text{text-align:center;}
	.animal_serch{margin: 0 auto;}
	.animal_lost{margin: 0 auto; border-spacing: 0 10px;} 
	.animal_lost tr{display:inline-block;}
	.result_num a{color:#000000}
	.more_button{
		width:85px; 
		background: #fff;
		border: 3px solid #6e6e6e;
    	color: #6e6e6e;
	    padding: 5px 10px;
	    border-radius: 15px;
	    text-decoration: none;
	    font-weight: 600;
	    transition: 0.25s;}
	.more_button:hover {
	    transition: 0.2s ease-out;
	    opacity : 0.8;
	    cursor: pointer;
	    background: lightcoral;
	    border: 3px solid lightcoral;
	    color: #fff;
		}
	.more_button:not(:hover){transition: 0.2s ease-out;}
	.sub_top_in_text{position: absolute; z-index: 11; top: 50%; left: 38%; transform: translate(-38% -50%); font-size: 50px; font-weight: bold;}
	
		.selectBox {
		  position: relative;
		  text-align:center;
		  color : lightcoral;
		  width: 120px;
		  height: 35px;
		  border-radius: 4px;
		  border: 2px solid lightcoral;
		}
		.selectBox .select {
		  width: inherit;
		  height: inherit;
		  background: transparent;
		  border: 0 none;
		  outline: 0 none;
		  padding: 0 5px;
		  position: relative;
		  z-index: 3;
		}
		.selectBox .select option {
		  background: lightcoral;
		  color: #fff;
		  padding: 3px 0;
		  font-size: 16px;
		}
		.selectBox .icoArrow {
		  position: absolute; 
		  top: 0; 
		  right: 0; 
		  z-index: 1; 
		  width: 35px; 
		  height: inherit;
		  border-left: 2px solid lightcoral;
		  display: flex;
		  justify-content: center;
		  align-items: center;
		}
		.selectBox .icoArrow img {
		  width: 50%;
		  transition: .3s;
		}
		.selectBox .select:focus + .icoArrow img {
		  transform: rotate(180deg);
		}
		.search_box{
		  position: relative;
		  color : lightcoral;
		  width: 250px;
		  height: 35px;
		  border-radius: 4px;
		  border: 2px solid lightcoral;
		  background: #fff;
		  padding-left: 10px;
		}
		.submit_button{
		  position: relative;
		  text-align:center;
		  color : lightcoral;
		  width: 65px;
		  height: 35px;
		  border-radius: 4px;
		  border: 2px solid lightcoral;
		  background: #fff;
		  top:2px;
		}
</style>
<div class="sub_top_img" style="width: 100%; position: relative; top:55px;">
	<div style="text-align: center;"><img src="../img/protect_ani.jpg"></div>
		<div class="sub_top_in_text">
		</div>
	</div>
 <div class ="animal_find">
 <!-- 
	<div class="title" style="width: 1070px; height: 55px; line-height: 30px; font-size: 30px; text-align: center;margin: 0 auto; margin-bottom: 10px;">
		<p>실종 동물 찾기</p>
	</div>
-->
	<table class="animal_serch">
	<tr>
		<td><table width="574" border="0" cellspacing="0" cellpadding="0"  style=" height: 30px;">
<tbody>
<tr>
<form action="animal_protect">
<td width="150px" align="right" bgcolor="#ffffff">				                                    
<select class="selectBox" name="animal_group" style="font-size:10pt;">
	<c:choose>
		<c:when test="${animal_group == '강아지' }">
			 <option value="전체">전체</option>
			 <option value="강아지">강아지</option>
	  		 <option value="고양이">고양이</option>
	  		 <option value="기타 반려동물">기타 반려동물</option>
		</c:when>
		<c:when test="${animal_group == '고양이' }">
			<option value="전체">전체</option>
			 <option value="강아지">강아지</option>
	  		 <option value="고양이">고양이</option>
	  		 <option value="기타 반려동물">기타 반려동물</option>
		</c:when>
				<c:when test="${animal_group == '기타 반려동물' }">
			<option value="전체">전체</option>
			 <option value="강아지">강아지</option>
	  		 <option value="고양이">고양이</option>
	  		 <option value="기타 반려동물">기타 반려동물</option>
		</c:when>
		<c:otherwise>
			<option value="전체" selected="selected">전체</option>
			 <option value="강아지">강아지</option>
	  		 <option value="고양이">고양이</option>
	  		 <option value="기타 반려동물">기타 반려동물</option>
		</c:otherwise>
		</c:choose>
</select>

<select class="selectBox" name="area" style="font-size:10pt;">
	<option value="" selected="">지역선택</option>
	<option value="서울">서울시</option>
	<option value="인천">인천시</option>
	<option value="대전">대전시</option>
	<option value="광주">광주시</option>
	<option value="대구">대구시</option>
	<option value="울산">울산시</option>
	<option value="부산">부산시</option>
	<option value="경기">경기도</option>
	<option value="강원">강원도</option>
	<option value="세종">세종시</option>
	<option value="충남">충청남도</option>
	<option value="충북">충청북도</option>
	<option value="전남">전라남도</option>
	<option value="전북">전라북도</option>
	<option value="경남">경상남도</option>
	<option value="경북">경상북도</option>
	<option value="제주">제주도</option>
	</select>	

		<c:choose>
		<c:when test="${empty search or search == 'null'}">
			<input class="search_box" type="text" name="search" placeholder="search" />
		</c:when>
		<c:otherwise>
			<input class="search_box" type="text" name="search" value="${search }" />
		</c:otherwise>
		</c:choose>
		<input class="submit_button" type="submit" value="검색">
	</form>
	</td>
	</tr>													
	</tbody>
	</table></td>
	</tr>
	</table>
	<table class="animal_lost">
		<c:forEach var="board" items="${boards}">
		<tr>
			<th>
				<a href="animal_protect_Content2?no=${board.no }"><div class="table_box">
				<c:choose>
					<c:when test="${board.image==null}">
					<img src="../img/no_image.gif">
					</c:when>
					<c:otherwise>
					<img src="../img/${board.id}/${board.image}">
					</c:otherwise>
				</c:choose>
					<table class="table_box_in" cellspacing="0">
					<tr>
					<td style="padding-top:5px; width:280px; height:35px;
					line-height:18px; text-overflow:ellipsis; white-space: nowrap; overflow: hidden; display: block; line-height: 35px" align="left">
						<span style="color:#f39a68; font:14px '돋움';">${board.area }</span>
						<span style="color:#333333; font:14px '돋움';">${board.title }
						</span>
					</td>
					</tr>
						<tr style="padding-top:5px; width:280px;" align="left">
						<td style="font:13px '돋움'; color:#999999; letter-spacing:-1px;">${board.id }</td>
							<td style="font:13px '돋움'; color:#d3d3d3; padding:0 5px;">|</td>
							<td style="font:13px '돋움'; color:#999999; letter-spacing:-1px;">${board.animal_group }</td>
						</tr>
					</table>
				</div></a>
			</th>
		</tr>
		</c:forEach>
		</table>
		<div style="width:85px; margin: 0 auto;"><button class="more_button" type="button" onclick="location.href='animal_protect_write'">글쓰기</button></div>
		<table class="animal_lost">
		
		<tr>
						<td class="result_num" colspan="4">
							${result }
						</td>
					</tr>
		</table>
</div>
<c:import url="/footer" />    		