<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>

	function kakaoCheck(){
	
		address = document.getElementById('sample6_address');
		email = document.getElementById('email');
		tel = document.getElementById('tel');

		if(address.value == ""){
			alert('주소를 입력해 주세요.');
		}else if(email.value == ""){
			alert('email을 입력해 주세요.');
		}else if(tel.value == ""){
			alert('전화번호를 입력해 주세요.');
		}else{
			var f = document.getElementById('f');
			f.submit();
		}
	}
	
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
</script>
<div align="center" style="margin-top: 100px;">
	<h1>추가 정보 기입</h1>
	<table >
	<tr><td align="center">
		<font color="red">${msg}</font><br>
		<font color="blue">*전부 입력해 주세요.*</font>
	</td></tr>
	<tr><td>
	<form action="kakaoregistProc" method="post" id="f">
		<input type="text" name="id" placeholder="아이디" id="id" value=${ sessionScope.kakaoid} readonly> <br>
		<label id="label" ></label><br>
		<input type="text" name="name" id="name" placeholder="이름" value=${ sessionScope.kakaonickname} readonly><br>
		<input type="text" name = "postcode" id="sample6_postcode" placeholder="우편번호">
		<input type="button"  onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
		<input type="text" name = "address" id="sample6_address" placeholder="주소"><br>
		<input type="text" name = "detailAddress" id="sample6_detailAddress" placeholder="상세주소"><br>
		<input type="text" name="email" id="email" placeholder="E-Mail"><br>
		<input type="text" name="tel" id="tel" placeholder="전화번호" ><br>
		<input type="button" value="회원가입" onclick="kakaoCheck()">
		<input type="button" value="취소" onclick="location.href='logout'"><br>
	</form>
	</td></tr></table>
</div>

<c:import url="/footer" />



