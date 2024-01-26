<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

<c:import url="/header" />
<div align="center">
	<div style="margin: 0 auto; margin-top: 100px;">
		<table width="761" border="0" cellpadding="0" cellspacing="0"
			align="center">

			<caption>
				<font size="5"><b>찾기 인증</b></font><br>	
			</caption>

		</table>

		<table width="729" border="0" cellpadding="0" cellspacing="0"
			align="center">

			<tr>
				<th width="100px">사용자</th>
				<td width="571"><font size="4">${find.id }</font></td>
			</tr>

		</table>

		<table width="729" border="0" cellpadding="0" cellspacing="0"
			align="center">
			<tr>
				<th width="100px">제목</th>
				<hr width="700">
				<br>
				<td width="571"><font size="4">${find.title }</font></td>

			</tr>
		</table>

		<table width="729" border="0" cellpadding="0" cellspacing="0"
			align="center">
			<tr>
				<th width="100px">동물종류</th>
				<hr width="700">
				<br>
				<td width="571"><font size="4">${find.species_kr }</font></td>
			</tr>
		</table>



		<table width="729" border="0" cellpadding="0" cellspacing="0"
			align="center">

			<tr>
				<th width="100px">내용</th>
				<br>
				<hr width="700">
				<td width="571"><br><font size="4">${find.content }</font><br>&nbsp;&nbsp;
				</td>
			</tr>
		</table>


		<c:if test="${!empty find.image }">
			<hr width="700">
			<img src="${find.image }" width="400px" height="auto" />
		</c:if>

		<c:if test="${!empty find.image2 }">
			<br />
			<br />
			<img src="${find.image2 }" width="400px" height="auto" />
		</c:if>


		<table width="720" border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr height="20">
				</tr>
				<tr>
					<td colspan="3"><hr /></td>
				</tr>
				<c:forEach var="comment" items="${find.comments }">
					<tr height="40">
						<td width="50">&nbsp;</td>
						<td width="652"><b> <font color="12436c">${comment.name }</font></b>&nbsp;(${comment.created_at })
							<button type="button"
								onclick='openPop(${comment.no},${comment.certification_no},"${comment.password}")'>❌</button>
							<br>${comment.content }</td>
						<td width="27">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3"><hr /></td>
					</tr>
				</c:forEach>

				
					<tr>
						<td></td>
						<table width="400" border="0" cellpadding="0" cellspacing="0">

							<td width="112" bgcolor="f4f4f4" align="center"><b>댓글달기</b>
							</td>
							<table width="540" border="0" height="58" cellpadding="3"cellspacing="0">

								<font>이름 <input type="text" name="cname"
									maxlength="5" size="15"
									style="padding-left: 3px; border: 1px solid rgb(204, 204, 204);"
									value="${sessionScope.id}" id="cname">
								</font>&nbsp;&nbsp;&nbsp;&nbsp;
								<font>비밀번호 <input type="password"
									name="cpass" maxlength="6" size="11"
									style="padding-left: 3px; border: 1px solid rgb(204, 204, 204);"
									id="cpass"></font>
							</table>
							
							<input type="text" name="comment" size="50" maxlength="100"
								style="padding-left: 3px; border: 1px solid rgb(204, 204, 204); width: 200px"
								value="" id="comment">
							<button type="button" onclick="insertComment()">댓글달기</button>
							<br></br>
					</table>
					</tr>
					</tbody>
					</table>
					</div>
					


		<button type="button" onclick="location.href='findForm'">목록</button>
		<c:if test="${sessionScope.id eq find.id}">
			<button type="button"
				onclick="location.href='editFind?no=${find.no }'">수정</button>
			<button type="button" onclick="deleteCheck(${find.no})">삭제</button>
		</c:if>

	</div>




	<!-- 댓글 삭제하기 모달 팝업 -->
	<div id="modalWrap" style="display: none;">
		<div id="modalContent">
			<div id="modalBody">
				<div>
					<span style="font-size: 25px;">댓글 삭제하기</span> <span id="closeBtn"
						onclick="$('#modalWrap').hide();">&times;</span>
				</div>
				<div class="divCenter">
					비밀번호 :&nbsp;&nbsp;<input type="password" id="deleteCommentPassword">
				</div>
				<div class="divCenter">
					<button type="button" onclick='deleteCommentCheck()'>삭제하기</button>
				</div>
			</div>
		</div>
	</div>
	<c:import url="/footer" />
	<script>
	//글 삭제
	function deleteCheck(no){
		console.log(no);
		if(window.confirm("삭제하시겠습니까?")) {
			//삭제
			location.href='deleteFind?no=' + no;  
		}
	}
	
	//삭제 댓글 정보를 담을 객체
	var commentObj = {};
	
	//댓글 삭제 > 모달팝업 오픈
	function openPop(no,certificationNo,password){
		console.log(no,certificationNo,password)
		commentObj = {
			no : no,
			certification_no : certificationNo,
			password : password
		};
		
		$("#deleteCommentPassword").val("");
		$("#modalWrap").attr("style", "display:block");
	}

	//댓글 삭제 > 비밀번호 확인
	function deleteCommentCheck(){
		let inputVal = $("#deleteCommentPassword").val();
		if(inputVal == commentObj.password){
			console.log('삭제할게요~')
			$("#modalWrap").attr("style", "display:none");
			
			$.ajax({ 
	            type:"POST", 
	            contentType : "application/json",
	            url:"deleteComment",
	            data: JSON.stringify ({
	            	no : commentObj.no,
	            	certification_no : commentObj.certification_no
	            }),
	            datatype: "application/json",
	            success:function(result){ 
	                console.log(result);
	                location.reload();
	            }
			
	        });
			//location.href='deleteComment?no=' + commentObj.no + "&certification_no=" + commentObj.certification_no;
		}else{
			alert('비밀번호가 틀렸습니다.');
		}
	} 
	
	//댓글 달기
	function insertComment(){
		let name = $("#cname").val();
		let password = $("#cpass").val();
		let content = $("#comment").val();
		
		
		$.ajax({ 
            type:"POST", 
            contentType : "application/json",
            url:"insertComment",
            data: JSON.stringify ({
            	certification_no : "${find.no}",
                name: name,
                password : password,
                content : content
            }),
            datatype: "application/json",
            success:function(result){ 
                console.log(result);
                location.reload();
            }
		
	
        });
		
	}
	
</script>
	<style>
#modalWrap {
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

#modalBody {
	width: 300px;
	height: 150px;
	padding: 30px;
	margin: 0 auto;
	border: 3px solid #df516b;
	background-color: #fff;
	display: flex;
	flex-direction: column;
}

#closeBtn {
	float: right;
	font-weight: bold;
	color: #777;
	font-size: 25px;
	cursor: pointer;
	margin-left: auto;
}

.divCenter {
	display: flex;
	align-items: center;
	justify-content: center;
	margin-top: 30px;
}
</style>