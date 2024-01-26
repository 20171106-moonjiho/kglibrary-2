<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
table td{
	text-align:center;
}
</style>
<c:import url="/header" />
<div style="margin: 0 auto; margin-top: 100px;">

	
		<table width="761" border="0" cellpadding="0" cellspacing="0" align="center">
		
				<caption>
				<font size="5"><b>찾기 인증</b></font><br><br><br>
			</caption>
		</table>
	
	
	<table border="0" align="center">
	<tr>
		<th width="60">NO</th>
		<th width="60">사진</th>
		<th width="370">제목</th>
		<th width="60">작성인</th>
		<th width="100">작성일</th>
		
		
	</tr>




	<c:forEach var="find" items="${finds}">
		<tr>
			<td style="">${find.no }</td>
			<td>
				<c:if test="${!empty find.image }">
				<img src="${find.image }" width="45px" height="45px"/>
				</c:if>
				<c:if test="${empty find.image }"></c:if>
			</td>
			<td onclick="location.href='findContent?no=${find.no }'" style="cursor:pointer;">
				${find.title }</td>
			<td>${find.id }</td>
			<td>${find.time }</td>
		</tr>
	</c:forEach>
	
<tr>
	<td colspan="6">${result }</td>
</tr>
</table>


<c:choose>
	<c:when test="${empty finds }">
		<h1 align="center">등록된 게시판이 없습니다.</h1>
	</c:when>
</c:choose>


<div>
	<table width="1200" border="0" cellpadding="0" cellspacing="0"
		align="right">
		<td><button type="button" onclick="WriteLoginCheck()">글작성</button></td>		
	</table>
	</div>


</div>
<c:import url="/footer" />

<script>

function WriteLoginCheck() {
	console.log("");
if("${sessionScope.id}" == "") {
	location.href="login"; 	
}else{
	location.href="findWrite"; 	
}
}


</script>
