<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />
<style>
h2 {
	font-weight: border;
}

.hr1 {
	border: 0;
	height: 2px;
	background: #d3d3d3;
}

.grey {
	color: #727272;
}

#strong {
	font-weight: 900;
}

table {
	width: 100%;
	border-top: 1px solid #d3d3d3;
	border-collapse: collapse;
}

th {
	background-color: #d3d3d3;
	border-top: 3px solid #727272;
}

th, td {
	border-bottom: 1px solid #d3d3d3;
	padding: 10px;
}

.greylist {
	width: 50px;
	height: 30px;
	font-weight: 900;
	color: white;
	text-align: center;
	background: grey;
	border: solid 2px white;
	border-radius: 5px;
}

.submit_button {
	width: 80px;
	height: 30px;
	font-weight: 900;
	color: white;
	text-align: center;
	background: linear-gradient(to bottom, grey, black);
	border: solid 2px white;
	border-radius: 5px;
}

.left {
	text-align: left;
}

.right {
	float: right;
}

.center {
	text-align: center;
}

a {
	color: black;
	text-decoration-line: none;
}
</style>

	<div style="width: 1200px; margin: 0 auto; margin-top: 100px;">
	<h2>자주 묻는 질문 계시판</h2>

	<hr class="hr1" noshade>
	
	<form action="answerForm">
	<span> ▷ 총 ${count }개의 게시물이 있습니다. </span> 
					<span class="right">
					<select class="selectBox" name="select">
								<option value="" selected="selected">전체</option>
					</select>
					<c:choose>
						<c:when test="${empty search or search == 'null'}">
							<input type="text" name="search" />
						</c:when>
						<c:otherwise>
							<input type="text" name="search" value="${search }"/>
						</c:otherwise>
					</c:choose>
					<input type="submit" class="submit_button" value="검색" />
					</span>
				</form>
				
		<c:choose>
			<c:when test="${empty boards }">
				<h1> 등록된 데이터가 존재하지 않습니다. </h1>
			</c:when>
			<c:otherwise>
				<table style="margin-top: 30px;">
					<tr>
						<th width="100">번호</th>
						<th width="500">제목</th>
						<th width="130">작성자</th>
						<th width="200">작성일</th>
					</tr>
					
					<c:forEach var="board" items="${ boards}">
						<tr>
							<td class="center">${board.no }</td>
							<td class="left" onclick="location.href='answerBoardContent?no=${board.no }'">
								${board.title }
							</td>
							<td class="center">${board.id }</td>
							<td class="center">${board.time }</td>
						</tr>
					</c:forEach>
				</table>
				<div class="center" style="margin-top: 18px;">${result}</div>
		</c:otherwise>
	</c:choose>
	
						<c:choose>
							<c:when test="${sessionScope.id eq 'admin'}">
							<span class="right">
								<button type="button" onclick="location.href='answerBoardWrite'"class="submit_button" >글쓰기</button>
							</span>
							</c:when>
						</c:choose>
	
</div>
<c:import url="/footer" />































