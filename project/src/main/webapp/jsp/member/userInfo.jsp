<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
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



<div align="left" align="center" style= "margin-top:100px; margin-left:30%" >

	<div class="card">
	<div class="card-write">
	<tr><td>
		<font color="red">${msg}</font>
	</td></tr>
	<form action="update" method="post" id="f">
	<input type="hidden" id="id" name="id" value="${member.id}">
	<input type="hidden" id="name" name="name" value="${member.name}">
	<input type="hidden" id="email" name="email" value="${member.email}">
	<input type="hidden" id="tel" name="tel" value="${member.tel}">
	<h1>개인 정보</h1><br><br>
	<b>아이디 : ${member.id } <br> 
	이름 : ${member.name }<br>
	우편변호 : ${postcode}<br>
	주소 : ${member.address } <br>
	상세주소 : ${detailAddress } <br>
	E-mail : ${member.email } <br>
	전화번호 : ${member.tel }</b> <br><br>
	</div>
	<div class="btn-w" align="center">
	<input type="submit" value="회원 수정"></button>
	<button type="button" onclick="location.href='delete?id=${member.id }'">회원 탈퇴</button>
	</div>
	</form>
	</div>
</div>	

<c:import url="/footer"/>



















