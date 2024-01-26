<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/css/swiper.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/js/swiper.min.js"></script>
	<link href="main.css" rel="stylesheet">
	<script src="dbQuiz.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="jssor.slider-22.1.8.mini.js" type="text/javascript"></script>
	<script>
		  jQuery(document).ready(function(){
		  	//메뉴 부분
			$(".gnb>li").on("mouseover",function(){
		  $(this).find(".sub").slideDown(200);
	
		 });
	
		 $(".gnb>li").on("mouseleave",function(){
		  $(this).find(".sub").slideUp(200);
	
		 });
		  
		  var nav = $('.header_menu');
		  var navoffset = $('.header_menu').offset();
	
		  $(window).scroll(function(){
			if($(this).scrollTop() >= navoffset.top){
			nav.css('position','fixed').css('top',0);
		
		}else{
			nav.css('position','absolute').css('top',120);
		}

		  });
		 
		  var jssor_1_options = {
	              $AutoPlay: true,
	              $SlideDuration: 800,
	              $SlideEasing: $Jease$.$OutQuint,
	              $ArrowNavigatorOptions: {
	                $Class: $JssorArrowNavigator$
	              },
	              $BulletNavigatorOptions: {
	                $Class: $JssorBulletNavigator$
	              }
	            };

	            var jssor_1_slider = new $JssorSlider$("jssor_1", jssor_1_options);

	            /*responsive code begin*/
	            /*you can remove responsive code if you don't want the slider scales while window resizing*/
	            function ScaleSlider() {
	                var refSize = jssor_1_slider.$Elmt.parentNode.clientWidth;
	                if (refSize) {
	                    refSize = Math.min(refSize, 1920);
	                    jssor_1_slider.$ScaleWidth(refSize);
	                }
	                else {
	                    window.setTimeout(ScaleSlider, 30);
	                }
	            }
	            ScaleSlider();
	            $(window).bind("load", ScaleSlider);
	            $(window).bind("resize", ScaleSlider);
	            $(window).bind("orientationchange", ScaleSlider);
	            /*responsive code end*/
	            
	  });
		  
  </script>
<div class="header">
			<div class="header_login">
				<ul class="pc_login">
				<c:choose>
					<c:when test="${empty sessionScope.id }">
						<li><a href="login">로그인</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="logout">로그아웃</a></li>
					</c:otherwise>
				</c:choose>
					<li><a href="#">|</a></li>
					<c:if test="${empty sessionScope.id }">
					<li><a href="agree">회원가입</a></li>
					<li><a href="#">|</a></li>
					</c:if>
					
					<li><a href="userInfo">마이페이지</a></li> 
					<c:choose>
					<c:when test="${sessionScope.id eq 'admin'}">
						<li><a href="#">|</a></li>
						<li><a href="memberInfo">회원 관리</a></li>
					</c:when>
					
				</c:choose>
					</ul>
				</div>
			<div class="logo">
				<h1><a href="index"><img src="img/bemypet-logo.svg" alt="logo"></a></h1>
			</div>

			<div class="header_menu">
			<nav class="nav">
				<ul class="gnb">
					<li><a href="animal_find">실종동물</a></li>
					<li><a href="animal_protect">보호중</a></li>
					<li><a href="centerInfo">유기동물 보호센터</a></li>
					<!-- <li><a href="Val">자원봉사</a></li> -->
					<li><a href="#">커뮤니티</a>
						<ul class="sub">
							<li><a href="findForm">찾았어요!</a></li>
							<li><a href="animal_info">반려동물지식</a></li>
						</ul>
					</li>
					<li><a href="#">고객센터</a>
						<ul class="sub">
							<li><a href="managementForm">공지사항</a></li>
							<li><a href="askForm">1:1문의</a></li>
							<li><a href="answerForm">자주묻는질문</a></li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>
	</div>







