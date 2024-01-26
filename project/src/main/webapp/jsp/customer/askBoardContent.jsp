<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />



<script>
	function deleteCheck(){
		result = confirm('진짜로 삭제하겠습니까?');
		if(result == true){
			location.href="askBoardDeleteProc?no=${board.no}"
		}
	}

	function replydeleteCheck(no){
		result = confirm('진짜로 삭제하겠습니까?');
		page_no = document.getElementById('redel');
		if(result == true){
			var re = document.getElementById('re');
			re.action="askReplyBoardDeleteProc?no="+no+"&page_no="+${board.no};
			re.submit();
		}else
			alert('취소 되었습니다');
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
			<img src="/img/${board.id }/${board.image }">
			<br>
		</c:if>
		<c:choose>
		<c:when test="${not empty msg}">
		<script>
			alert("${msg}");
		</script>
		</c:when>	
	</c:choose>
		${sessionScope.viewGetContent }
	</div>
	<div class="button_bar">
		<button type="button" onclick="location.href='askForm'">목록</button>
			<button type="button" onclick="deleteCheck()">삭제</button> 	
	</div>
</div>
<!-- 
<div align="center" style="margin-top: 100px; margin-bottom: 20px;">
	<h1>${board.title }</h1>
	<c:choose>
		<c:when test="${not empty board.image }">
		<img src="/img/${board.id }/${board.image }">
		</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${not empty msg}">
		<script>
			alert("${msg}");
		</script>
		</c:when>	
	</c:choose>
	<table border='1' style="margin-top: 50px; ">
		<tr>
			<th>제목</th>
			<td width="200">${board.title }</td>
			<th width="100">작성자</th>
			<td width="150">${board.id }</td>
			<th width="70">작성일</th>
			<td whdth="120">${board.time }</td>
		</tr>	
		<tr>
			<th>문서내용</th>
			<td colspan="6">${board.content }</td>	
			</td>	
		</tr>
		<tr>
			<td colspan="6" align="center">
				<button type="button" onclick="location.href='askForm'">목록</button>
				<button type="button" onclick="deleteCheck()">삭제</button> 		
			</td>
		</tr>
	
	</table>
</div>
 -->
 
 <div class="ask_box">
	<div class="ask _re">
		<form method="post" id="re">
			<c:forEach var="askreply" items="${askreplys}">

				<div style="border: 1px solid #dedede; margin-bottom: 10px; font-size: 15px;">
					<div class="in_top" style="border-bottom: 1px solid #dedede; height: 45px; line-height: 45px">
						<div class="ask_name" style="width: 70%; float: left;box-sizing: border-box; padding-left: 15px; font-weight: bold;">
						${askreply.id }
						</div>
						<div class="ask_time" style="width: 30%; float: right; text-align: right; padding-right: 15px; box-sizing: border-box;">
						${askreply.time }
						<c:if test="${sessionScope.id eq askreply.id}">
							<tr>
								<td><input type="button" value="삭제"
									onclick="replydeleteCheck(${askreply.no })" id="redel">
								</td>

							</tr>
						</c:if>
						</div>
					</div>
					
				<div class="in_bottom" style="padding: 30px 15px 30px 15px;">
					<div class="ask_con">${askreply.content }</div>
				</div>
				
				</div>
			</c:forEach>
		</form>
	</div>
	<div class="ask_re">
		<form method="post" action="askReplyWrite">
			<div style="width: 100%">
				<textarea style="width: 100%" rows="5" cols="50" name="content"
					placeholder="로그인 하신 후 댓글을 입력하세요."></textarea>
			</div>
			<div style="width: 320px; float: right; margin-top: 10px">
				<p>
					<label>작성자 : </label> <input type="text" name="writer"
						value="${sessionScope.id}" readonly> <input type="hidden"
						name="page_no" value="${board.no}"> <input type="hidden"
						name="re_no" value="${replysize}">
					<button type="submit">댓글 작성</button>
				</p>
			</div>
		</form>
	</div>
	
	<!-- 
	<div align="center">
	
						<form method="post"  id="re">						
				<c:forEach var="askreply" items="${askreplys}">
				
	
					<table border='0' style="margin-bottom: 20px;">	
						
						<tr>
							<th width="50px">번호</th>
							<td width="50px"> ${askreply.no }</td>
							<th width="100px"> 작성자 </th>
							<td width="150px"> ${askreply.id }</td>
							<th width="100px">날짜</th>
							<td width="120px">${askreply.time }</td>	
						</tr>
						<tr>
							<td colspan="6">${askreply.content }</td> 
						</tr>
						<tr>
							<td><input type="button" value="삭제" onclick="replydeleteCheck(${askreply.no })"  id="redel"> </td>

						</tr>
					</table>
					
				</c:forEach>
						</form>
		
	</div>
	
	
	<div align="center">

		<form method="post" action="askReplyWrite">
	
			<p>
				<label>댓글 작성자</label> <input type="text" name="writer" value ="${sessionScope.id}" readonly>
			</p>
			<p>
				<textarea rows="5" cols="50" name="content"></textarea>
			</p>
			<p>
				<input type="hidden" name="page_no" value="${board.no}">
				<input type="hidden" name="re_no" value="${replysize}">	
				<button type="submit">댓글 작성</button>
			</p>
		</form>
	
		</div>
	
 -->
<c:import url="/footer" />






