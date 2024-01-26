<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />

<style>
.card{width: 800px; margin: 0 auto;}
.selectBox {
		  position: relative;
		  text-align:center;
		  width: 100px;
		  height: 35px;
		  border-radius: 10px;
		  border: 1px solid #999;
		}
.card-header1 h1{
	font-size: 30px;
	font-weight: 600;
}

.card-write{
	padding: 20px;
}
.btn-w{
	text-align:left;
	margin:0 auto;
	padding: 10px;
}
.btn-w input,button{
	border:none;
	background-color: lightcoral;
	padding: 10px;
	color: #fff;
	border-radius: 10px;
}
</style>


<div align="center" style="margin-top:75px">
<h1>회원 탈퇴</h1>
<table>
	<tr><td>${msg }</td></tr>
	<div class="card">
	
	<c:choose>
		<c:when test="${sessionScope.id eq 'admin'}">
				<tr><td><font color="red"> 관리자 비밀번호를 입력하세요. </font></td></tr>
		</c:when>
	</c:choose>
	<tr><td>
	<form action="deleteProc" method="post">
	<c:choose>
		<c:when test="${ sessionScope.id eq sessionScope.kakaoid}">
			<input type="text" name="id" value="${id}" readonly="readonly"> <br>
		
			<div class="btn-w" style="margin-left:4%">
			<input type="button" value="회원 탈퇴" onclick="location.href='kakaoDeleteProc'"> 
			<input type="button" value="취소" onclick="location.href='index'">
			</div>
		</c:when>
		<c:otherwise>
			<input type="text" name="id" value="${id}" readonly="readonly"> <br>
			<input type="password" placeholder="비밀번호" name="pw"><br>
			<input type="password" placeholder="비밀번호 확인" name="confirm"><br>
			<div class="btn-w" style="margin-left:4%">
			<input type="submit" value="회원 탈퇴"> 
			<input type="button" value="취소" onclick="location.href='index'">
			</div>
		</c:otherwise>
		</c:choose>

		</form>
		</td></tr>
	</table>
	</div>
</div>

<c:import url="/footer" />








