function allCheck(){
	let id = document.getElementById('id');
	let pw = document.getElementById('pw');
	confirm = document.getElementById('confirm');
	userName = document.getElementById('name');
	address = document.getElementById('sample6_address');
	email = document.getElementById('email');
	tel = document.getElementById('tel');
	if(id.value == ""){
		alert('아이디를 입력해 주세요.');
	}else if(pw.value == ""){
		alert('비밀번호를 입력해 주세요.');
	}else if(confirm.value == ""){
		alert('비밀번호 확인은 필수 항목입니다.');
	}else if(userName.value == ""){
		alert('이름을 입력해 주세요.');
	}else if(address.value == ""){
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

function pwCheck(){
	let pw = document.getElementById('pw');
	confirm = document.getElementById('confirm');
	label = document.getElementById('label');
	 if(pw.value == confirm.value){
		 label.innerHTML = '일치'
	 }else{
		 label.innerHTML = '불일치'
	 }
	// window.alert('pwCheck 호출')
}

function loginCheck(){
	let id = document.getElementById('id');
	let pw = document.getElementById('pw');
	
	if(id.value == ""){
		alert('아이디는 필수 항목입니다.');
	}else if(pw.value == ""){
		alert('비밀번호는 필수 항목입니다.');
	}else{
		var f = document.getElementById('f');
		f.submit();
	}
}
 function agreeCheck()
    {	
		 let check1 = document.getElementById('check1');
         let check2 = document.getElementById('check2');
         let check3 = document.getElementById('check3');
  
        if (!check1.checked) {
            alert("1.회원가입약관의 내용에 동의하셔야 회원가입 하실 수 있습니다.");
            check1.focus();
            return false;
        }
        if (!check2.checked) {
            alert("2.개인정보처리방침안내의 내용에 동의하셔야 회원가입 하실 수 있습니다.");
            check2.focus();
            return false;
        }
        if (!check3.checked) {
            alert("3.14세 이상 가입 하실 수 있습니다");
            check3.focus();
            return false;
        }   
        var f = document.getElementById('f');
		f.submit();
    }
  
    
function find_write_Check(){
	var title = document.getElementsByName('title');
	var area = document.getElementsByName('area');
	var areatext = document.getElementsByName('areatext');
	var name = document.getElementsByName('name');
	var tel = document.getElementsByName('tel');
	var age = document.getElementsByName('age');
	var day = document.getElementsByName('day');
	var content = document.getElementsByName('content');
	if(title[0].value == ""){
		alert('제목을 입력해주세요.');
	}else if(area[0].value == ""){
		alert('실종지역을 선택해주세요.');
	}else if(areatext[0].value == ""){
		alert('실종지역을 입력해주세요.');
	}else if(name[0].value == ""){
		alert('반려동물의 이름을 입력해주세요.');
	}else if(age[0].value == ""){
		alert('반려동물의 나이를 입력해주세요.');
	}else if(day[0].value == ""){
		alert('반려동물의 실종일을 입력해주세요.');
	}else if(tel[0].value == ""){
		alert('연락받으실 보호자의 연락처를 입력해주세요.');
	}else if(content[0].value == ""){
		alert('상세 설명은 필수 사항입니다.');
	}else{
		var f = document.getElementById('f');
		f.submit();
	}
}


function find_info_Check(){
	var title = document.getElementsByName('title');
	var content = document.getElementsByName('content');
	if(title[0].value == ""){
		alert('제목을 입력해주세요.');
	}else if(content[0].value == ""){
		alert('내용은 필수 사항입니다.');
	}else{
		var f = document.getElementById('f');
		f.submit();
	}
}
