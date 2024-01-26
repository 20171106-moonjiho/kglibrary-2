<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />
<script>

</script>
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
.card-write input, texarea{
	margin-left: 10px;
	padding: 10px;
	border: 1px solid #999;
	border-radius: 10px;
	box-shadow: 3px 3px 10px #e6e6e6;
}
.card-write .subject_title{
	font-size:13px;
	font-family: 600;
	margin-right: 10px;
}
.card-write .myinfo input[type="text"]{
	width: 25%;

}
.card-write .title-w input[type="text"]{
	margin-top: 20px;
	margin-bottom: 20px;
	width: 85.5%
}
.card-write .area{
	margin-top: 20px;
	margin-bottom: 20px;
}
.card-write .animalinfo{
	margin-top: 20px;
	margin-bottom: 20px;
}
.card-write .day{
	margin-top: 20px;
	margin-bottom: 20px;
}

.card-write .msg{
	margin-top: 20px;
}
.card-write .msg textarea{
	min-width: 90.5%;
	max-width: 90.5%;
	min-height: 200px;
	max-height: 200px;
	box-shadow: inset 3px 3px 10px #e6e6e6;
	
	vertical-align: top;
}
.card-write input[type="file"]{
	border: none;
	box-shadow: none;
	padding: 10px;
	
}
.btn-w{
	text-align:center;
	margin:0 auto;
	padding: 10px;
}
.btn-w input{
	border:none;
	background-color: lightcoral;
	padding: 10px;
	color: #fff;
	border-radius: 10px;
}
</style>
<div style="margin: 0 auto; margin-top:100px;">
	<form action="animal_info_writeProc" method='post' id="f" enctype="multipart/form-data">
	<div class="card">
			<div class="card-header1" style="text-align: center;">
				<h1>게시글 등록</h1>
			</div>


			<div class="card-write">
				
			<div class="title-w">
				<span class="subject_title">제목</span>
				<input type="text" name="title" placeholder="제목을 입력하세요.">
			</div>
								
			<div class="msg">
			<span class="subject_title">내용</span>
				<textarea name="content" placeholder="내용을 입력하세요."></textarea>
				<div style="border-bottom: 1px dotted #dedede; width: 90%; margin-top: 5px; margin-bottom: 5px;"></div>
				<p style="font-size:13px; ">
				<span style="color: lightcoral">※ 사진첨부시 주의사항</span><br>
				1. 사진용량이 너무 크거나, bmp 파일은 에러가 발생할 수 있습니다.<br>
				2. 사진의 파일명은 반드시 영문으로 등록해주세요.<br>
				3. 사진에 상세 입력사항을 넣거나 타사이트 주소를 게시할 경우 등록글은 삭제됩니다.<br>
				4. 업로드할 사진의 파일은 <span style="color: lightcoral">jpg, jpeg, png만</span> 가능합니다.
				</p>
				<input type="file" name="upfile"><br>
			</div>
		</div>
			<div class="btn-w">
				<input type="button" value="글쓰기" onclick="find_info_Check()"> 
				<input type="button" value="목록"	 onclick="location.href='animal_info'">
				</div>
			</div>
		</form>
</div>	
<c:import url="/footer" />  