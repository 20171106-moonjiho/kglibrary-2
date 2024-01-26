<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />

<script>
	function deleteCheck(){
		result = confirm('진짜로 삭제하겠습니까?');
		if(result == true){
			location.href="answerBoardDeleteProc?no=${board.no}"
		}
	}
</script>

<style>
.box1 {
	font-size: 15px;
	border: 1px solid #444444;
	border-collapse: collapse;
	line-height: 20px;
}

.box1 img {
	max-width: 900px;
	margin: 0 auto;
}

.box1 th, td {
	border: 1px solid #444444;
}

.box1 th {
	width: 150px;
	height: 40px;
	background: #626363;
	color: #fff;
}

.box1 td {
	width: 270px;
	height: 40px;
}

.main_box {
	width: 930px;
	margin: 0 auto;
	margin-top: 100px;
	border: 1px solid lightcoral;
	border-radius: 5px 5px 5px 5px;
}

.ask_box {
	width: 930px;
	margin: 0 auto;
	margin-top: 50px;
}

.title, .user, .time {
	border-bottom: 1px solid #dedede;
	height: 45px;
	padding-left: 27px;
	font-size: 15px;
	line-height: 45px;
}

.contents {
	padding: 27px;
	font-size: 15px;
}

.contents img {
	display: block;
	margin: 0 auto;
	max-width: 880px
}

.button_bar {
	margin: 0 auto;
	width: 300px;
	margin-bottom: 15px;
	text-align: center;
}

.button_bar button {
	color: #fff;
	width: 65px;
	height: 30px;
	border-radius: 4px;
	border: 1px solid lightcoral;
	background: lightcoral;
	padding-left: 10px;
}
</style>

<div class="main_box">
	<div class="title">
		<span style="color: lightcoral; font-weight: 500;">제목 :</span>
		${board.title }
	</div>
	<div class="user">
		<span style="color: lightcoral; font-weight: 500;">작성자 : </span>${board.id }
	</div>
	<div class="time">
		<span style="color: lightcoral; font-weight: 500;">작성일 : </span>${board.time }
	</div>
	<div class="contents">
		<c:if test="${board.image != null}">
			<img src="/img/admin/${board.image }">
			<br>
		</c:if>
		${sessionScope.viewGetContent }
	</div>
	<div class="button_bar">
		<button type="button" onclick="location.href='answerForm'">목록</button>
		<c:choose>
			<c:when test="${sessionScope.id eq 'admin'}">
				<button type="button" onclick="location.href='answerBoardModify?no=${board.no }'">수정</button>
				<button type="button" onclick="deleteCheck()">삭제</button> 
			</c:when>
		</c:choose>
	</div>
</div>

<!-- 
<div align="center" style="margin-top: 100px;">
	<h1>${board.title }</h1>
	<c:choose>
		<c:when test="${not empty board.image }">
		<img src="/img/admin/${board.image }">
		</c:when>
	</c:choose>
		
	<table border='1' style="margin-top: 50px;">
		<tr>
			<th>제목</th>
			<td width="200">${board.title }</td>
			<th width="100">작성자</th>
			<td width="100">${board.id }</td>
			<th width="70">작성일</th>
			<td whdth="100">${board.time }</td>
		</tr>
		<tr>
			<th>문서내용</th>
			<td colspan="6">${board.content }</td>	
			</td>	
		</tr>

		<tr>
			<td colspan="6" align="center">
				<button type="button" onclick="location.href='answerForm'">목록</button>
		
		<c:choose>
			<c:when test="${sessionScope.id eq 'admin'}">
				<button type="button" onclick="location.href='answerBoardModify?no=${board.no }'">수정</button>
				<button type="button" onclick="deleteCheck()">삭제</button> 
			</c:when>
		</c:choose>
		
			</td>
		</tr>
	</table>
</div>
 -->
<c:import url="/footer" />






