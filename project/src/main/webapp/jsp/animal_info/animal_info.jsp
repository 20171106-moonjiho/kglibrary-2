<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<h2>반려동물 지식 공유 게시판</h2>

	<hr class="hr1" noshade>

	<form action="animal_info">

				<span> ▷ 총 ${count }개의 게시물이 있습니다. </span>
				<span class="right">
				
				<select class="selectBox" name="search_select">
						<c:choose>
							<c:when test="${search_select == '제목' }">
								<option value="">전체</option>
								<option value="제목" selected="selected" >제목</option>
								<option value="글쓴이">글쓴이</option>
							</c:when>
							<c:when test="${search_select == '글쓴이' }">
								<option value="">전체</option>
								<option value="제목">제목</option>
								<option value="글쓴이" selected="selected" >글쓴이</option>
							</c:when>
							<c:otherwise>
								<option value="" selected="selected">전체</option>
								<option value="제목">제목</option>
								<option value="글쓴이">글쓴이</option>
							</c:otherwise>
						</c:choose>
					
					</select>
					
				<c:choose>
				<c:when test="${empty search or search == 'null'}">
					<input type="text" name="search" >
				</c:when>
				<c:otherwise>
				<input type="text" name="search" >
				</c:otherwise>
				</c:choose>
				<input type="submit" class="submit_button" value="검색">
				</span>
	</form>

	<br>
			<c:choose>
			<c:when test="${empty boards }">
				<h1>등록된 데이터가 존재하지 않습니다.</h1>
			</c:when>
			<c:otherwise>
	<table>
		<tr>
			<th width="100">번호</th>
			<th width="500">제목</th>
			<th width="130">작성자</th>
			<th width="200">일시</th>
			<th width="70">조회수</th>
		</tr>
		<c:forEach var="board" items="${boards }">
			
			<tr>
				<td class="center"><a href="animal_info_Content?no=${board.no }">${board.no }</a></td>
				<td class="left"><a href="animal_info_Content?no=${board.no }">${board.title }</a></td>
				<td class="center">${board.id }</td>
				<td class="center">${board.writeDate }</td>
				<td class="center">${board.hits }</td>
			</tr>
			
		</c:forEach>
	</table>
	</br>

	<div class="center">
		${result}
	</div>

	<span class="right"><input type="button" value="글쓰기" onclick="location.href='animal_info_write'"
		class="submit_button">
	</span>
	</c:otherwise>
	</c:choose>
</div>

<c:import url="/footer" />