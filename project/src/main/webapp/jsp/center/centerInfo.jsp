<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=29p4qllv9k">
    </script>
</head>
<body>
<div id="map" style="width:50%;height:400px; margin-left:25%; margin-top:100px;"></div>

<script type="text/javascript">

$(function() {
	initMap();
});


function initMap() { 
	
	var areaArr = new Array();  // 지역을 담는 배열 ( 지역명/위도경도 )
	
	areaArr.push(
			/* 이름*/			/*위도*/					/*경도*/			
 {location : '가평군동물보호센터' , lat : '37.8459543' , lng : '127.4991358'},
 {location : '가평군동물보호센터' , lat : '37.8459543' , lng : '127.4991358'},  
 {location : '고양시동물보호센터' , lat : '37.6496069' , lng : '126.870066'},  
 {location : '광주TNR동물병원' , lat : '37.41746097' , lng : '127.2752964'},  
 {location : '남양주동물보호센터' , lat : '37.6089046' , lng : '127.1918026'},  
 {location : '가나동물병원' , lat : '37.48350736' , lng : '126.7631747'},  
 {location : '가야동물병원' , lat : '37.49060016' , lng : '126.7838949	'}, 
 {location : '24시아이동물메디컬' , lat : '37.5256574' , lng : '126.8045482' },  
 {location : 'CJ동물병원' , lat : '37.50029663' , lng : '126.7751273'},  
 {location : '펫앤쉘터동물병원' , lat : '37.3670017' , lng : '127.1276345'},  
 {location : '수원시동물보호센터' , lat : '37.28501037' , lng : '127.0786968'},  
 {location : '시흥시동물누리보호센터' , lat : '37.37405365' , lng : '126.7427931'}, 
 {location : '(사)한국야생동물보호협회' , lat : '37.3401156' , lng : '126.8700487'},  
 {location : '스타동물병원' , lat : '37.3135805' , lng : '126.8367508'}, 
 {location : '이성준동물병원' , lat : '37.0065829' , lng : '127.274787'},  
 {location : '(사)한국동물구조관리협회' , lat : '37.8700531' , lng : '126.9831861'},  
 {location : '양평군품유기동물보호센터' , lat : '37.51079775' , lng : '127.5142953'},  
 {location : '위더스동물메디컬센터 부설동물보호센터' , lat : '37.297553'  , lng : '127.5756334'},  
 {location : '오산시 수의사회' , lat : '37.149051' , lng : '127.065149'},  
 {location : '용인시동물보호센터' , lat : '37.243299' , lng : '127.1591338'},  
 {location : '평택시 동물보호센터' , lat : '37.13063033'  , lng : '127.0554225'},  
 {location : '하남동물병원' , lat : '37.5371145' , lng : '127.204029'},  
 {location : '남양동물보호센터' , lat : '37.22494992' , lng : '126.8434243'},  
);	
	
	let markers = new Array(); // 마커 정보를 담는 배열
	let infoWindows = new Array(); // 정보창을 담는 배열
	
	var map = new naver.maps.Map('map', {
        center: new naver.maps.LatLng(37.552758094502494, 126.98732600494576), //지도 시작 지점
        zoom: 9
    });
	
	for (var i = 0; i < areaArr.length; i++) {
		// 지역을 담은 배열의 길이만큼 for문으로 마커와 정보창을 채워주자 !

	    var marker = new naver.maps.Marker({
	        map: map,
	        title: areaArr[i].location, // 이름 
	        position: new naver.maps.LatLng(areaArr[i].lat , areaArr[i].lng) // 위도 경도 넣기 
	    });
	    
	    /* 정보창 */
		 var infoWindow = new naver.maps.InfoWindow({
		     content: '<div style="width:200px;text-align:center;padding:10px;"><b>' + areaArr[i].location + '</b><br> - 네이버 지도 - </div>'
		 }); // 클릭했을 때 띄워줄 정보 HTML 작성
	    
		 markers.push(marker); // 생성한 마커를 배열에 담는다.
		 infoWindows.push(infoWindow); // 생성한 정보창을 배열에 담는다.
	}
    
	 
    function getClickHandler(seq) {
		
            return function(e) {  // 마커를 클릭하는 부분
                var marker = markers[seq], // 클릭한 마커의 시퀀스로 찾는다.
                    infoWindow = infoWindows[seq]; // 클릭한 마커의 시퀀스로 찾는다

                if (infoWindow.getMap()) {
                    infoWindow.close();
                } else {
                    infoWindow.open(map, marker); // 표출
                }
    		}
    	}
    
    for (var i=0, ii=markers.length; i<ii; i++) {
    	console.log(markers[i] , getClickHandler(i));
        naver.maps.Event.addListener(markers[i], 'click', getClickHandler(i)); // 클릭한 마커 핸들러
    }
}

</script>

<div align="center" style="margin-top: 100px;">


<font color="red" >${msg }</font>

		<table border=1>
			<thead>
			<tr><th colspan="4" style="background:#806363; alpha:3">경기도 유기 동물 보호시설 현황</th>
			</tr>
				<tr>	
					<th>지역</th>
					<th>센터 명</th>
					<th>전화번호</th>
					<th> 주소 </th>
				</tr>
			</thead>
				<tbody>
				<c:set var="i" value="0"/>
				
				<c:forEach var="center" items="${centers}">
				<c:set var="i" value="${i+1 }"/>
				
					<tr>
						<td align="center">
							${center.SIGUN_NM}
						</td>
						<td align="center">${center.ENTRPS_NM }</td>
						<c:choose>
							<c:when test="${center.ENTRPS_TELNO eq 'null'}">
							<td align="center">없음</td>
							</c:when>
							<c:otherwise>
							<td align="center">${center.ENTRPS_TELNO }</td>
							</c:otherwise>
						</c:choose>
						<td> ${center.REFINE_ROADNM_ADDR}</td>
					</tr>
				<form id="LATLOGT" action="centerFind()" method="post">
				<input type="hidden" name="LAT" id="LAT${i }" value="${center.REFINE_WGS84_LAT}">
				<input type="hidden" name="LOGT" id="LOGT${i }" value="${center.REFINE_WGS84_LOGT}">
				</form>
				</c:forEach>
			</tbody>
		</table>
		<div> ${result}	</div>
		<div id="i">${i }</div>
</div>







</body>
</html>
<c:import url="/footer" />
