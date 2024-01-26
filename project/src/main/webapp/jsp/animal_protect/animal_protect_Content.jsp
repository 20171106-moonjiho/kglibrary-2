<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />

<script>
	function deleteCheck(){
		result = confirm('진짜로 삭제하겠습니까?');
		if(result == true){
			location.href='ContentDeleteProc2?no=${board.no}'
		}
	}
</script>

	<script>
	  jQuery(document).ready(function(){

	  new Swiper('.swiper-container', {
		navigation : {
		nextEl : '.swiper-button-next', // 다음 버튼 클래스명
		prevEl : '.swiper-button-prev', // 이번 버튼 클래스명
		},
	  });	  
  });
  </script>
<style>
		.box1{font-size: 15px; border: 1px solid #444444;border-collapse: collapse; line-height: 20px;}
		.box1 th,td{ border: 1px solid #444444;}
		.box1 th{width: 150px; height: 40px; background: #626363; color: #fff;}
		.box1 td{width: 270px; height: 40px;}
		
	    .swiper-container {
		width:900px;
		height:650px;
		margin-bottom: 35px;
		}
		.swiper-slide {
		text-align:center;
		display:flex; /* 내용을 중앙정렬 하기위해 flex 사용 */
		align-items:center; /* 위아래 기준 중앙정렬 */
		justify-content:center; /* 좌우 기준 중앙정렬 */
		}
		.swiper-slide img{width: 900px; margin: 0 auto;}
</style>
<div align="center" style="margin: 0 auto; margin-top:100px;">
 
	<div class="swiper-container">
	<div class="swiper-wrapper">
	
	 <c:if test="${board.image!=null}">
	 	<div class="swiper-slide"><img src="img/${board.id}/${board.image}"></div>
	 </c:if>
	 
	  <c:if test="${board.image2!=null}">
	 	<div class="swiper-slide"><img src="img/${board.id}/${board.image2}"></div>
	 </c:if>
	 
	  <c:if test="${board.image3!=null}">
	 	<div class="swiper-slide"><img src="img/${board.id}/${board.image3}"></div>
	 </c:if>
	 
	  <c:if test="${board.image==null} && ${board.image2==null} && ${board.image3==null}">
	 	<div class="swiper-slide"><img src="../img/no_image.gif"></div>
	 </c:if>
	</div>
	<div class="swiper-button-next"></div>
	<div class="swiper-button-prev"></div>
	</div>
	<table class="box1" style="text-align: center;">
	<tr>
		<th>이 름
		</th>
		<td>${board.name }</td>	
		<th>나 이</th>
		<td>${board.age } </td>
	</tr>
	
	<tr>
		<th>성 별</th>
		<td>${board.sex }</td>
		<th>지 역</th>
		<td>${board.areatext }</td>
	</tr>
	
	<tr>
		<th>잃어버린 날짜</th>
		<td>${board.time }</td>
		<th>전화번호</th>
		<td>${board.tel } </td>
	</tr>
	
	<tr>
		<th>상세 설명</th>
	<td colspan="3" style="padding: 25px; text-align: left">${board.content }</td>
	</tr>
	<tr>
		<td colspan="4">
			<button type="button" onclick="location.href='animal_protect'">목록</button>
			<c:if test="${sessionScope.id eq board.id}">
			<button type="button" onclick="location.href='animal_protect_Modify?no=${board.no }'">수정</button>
			<button type="button" onclick="deleteCheck()">삭제</button>
			</c:if> 
		</td>
	</tr>
		
	</table>
</div>

<c:import url="/footer" />  
