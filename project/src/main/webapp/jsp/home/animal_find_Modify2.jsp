<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />
<script>

</script>
<style>
.find_write_table th{width: 200px; height: 30px}
</style>
<div align="center" style="margin: 0 auto; margin-top:100px;">
	<form action="animal_find_ModifyProc" method='post' id="f" enctype="multipart/form-data">
		<input type="hidden" name="no" value="${board.no }" />	
		<table class="find_write_table">
		
			<caption>
				<font size="5"><b>게시글 등록</b></font>
			</caption>
			<tr>
				<th width="100px">글 제목</th>
				<td><input style="width: 100%;" type="text" name="title" value="${board.title }"></td>
			</tr>
			<tr>
				<th>지역</th>
				<td width="300px" align="left" bgcolor="#ffffff">				                                    
					 <select name="area" style="font-size:10pt;">
							<option value="">지역선택</option>
							<option value="서울">서울시</option>
							<option value="인천">인천시</option>
							<option value="대전">대전시</option>
							<option value="광주">광주시</option>
							<option value="대구">대구시</option>
							<option value="울산">울산시</option>
							<option value="부산">부산시</option>
							<option value="경기">경기도</option>
							<option value="강원">강원도</option>
							<option value="세종">세종시</option>
							<option value="충남">충청남도</option>
							<option value="충북">충청북도</option>
							<option value="전남">전라남도</option>
							<option value="전북">전라북도</option>
							<option value="경남">경상남도</option>
							<option value="경북">경상북도</option>
							<option value="제주">제주도</option>
					 </select>
					 <input type="text" name="areatext" maxlength="25" size="17" style="background-color:rgb(248,248,248); border-width:1px; border-color:rgb(204,204,204); border-style:solid;" value="${board.areatext }">
				 </td>
			</tr>
			<tr>
				 <th>반려동물</th>
				 <td width="150px" align="left" bgcolor="#ffffff">				                                    
					 <select name="animal_group" style="font-size:10pt;">
						 <option value="강아지">강아지</option>
				  		 <option value="고양이">고양이</option>
				  		 <option value="기타 반려동물">기타 반려동물</option>
					 </select>
				 </td>
			</tr>
			<tr>
			<th>반려동물 이름</th>
			<td>
				<input style="width: 100%;" name="name" value="${board.name }">
			</td>
			</tr>
			<tr>
			<th>반려동물 성별</th>
			<td>
				<select name="sex" style="font-size:10pt;">
						 <option value="수컷">수컷</option>
				  		 <option value="암컷">암컷</option>
				</select>
			</td>
			</tr>
			<tr>
			<th>반려동물 나이</th>
				<td>
					<input style="width: 100%;" name="age" value="${board.age }">
				</td>
			</tr>
			<tr>
			<th>잃어버린 날짜</th>
				<td>
					<input style="width: 100%;" name="day" value="${board.day }">
					 <br><span>※기입 예) 2023-10-23</span>
				</td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td>
					<input style="width: 100%;" name="tel" value="${board.tel }">
					<br><span>※기입 예) 010-1234-1234</span>
				</td>
			</tr>
			<tr>
				<th>긴급 여부</th>
				<td width="150px" align="left" bgcolor="#ffffff">				                                    
					 <select name="cate" style="font-size:10pt;">
							<option value="일반">일반</option>
							<option value="긴급">긴급</option>
					 </select>
				 </td>
			</tr>
			<tr>
				<th>사례금</th>
				<td>
					<input style="width: 100%;" name="money" value="${board.money }">
					<br><span style="color: red"> 사례금은 10만원을 넘어선 안됩니다.</span>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea style="width: 100%;" rows="10" cols="30" name="content">${board.content }</textarea>
				</td>
			</tr>
			<tr>
				<th>파일첨부</th>
				<td><input type="file" name="upfile" >${board.image }</td>
			</tr>
			<tr>
			<th></th>
			<td><input type="file" name="upfile2" >${board.image2 }</td>
			</tr>
			<tr>
			<th></th>
			<td><input type="file" name="upfile3" >${board.image3 }</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="수정" onclick="find_write_Check()"> 
					<input type="button" value="목록"	 onclick="location.href='animal_find'">
					<button type="button" onclick="history.back()">이전</button>
				</td>
			</tr>
		</table>
	</form>
</div>
<c:import url="/footer" />  