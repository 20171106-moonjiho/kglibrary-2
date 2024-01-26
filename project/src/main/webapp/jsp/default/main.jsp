<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div align="center" class="main_div">
<div id="jssor_1" style="position:relative;margin:0 auto;top:0px;left:0px;width:1300px;height:500px;overflow:hidden;visibility:hidden;">
        <!-- Loading Screen -->
        <div data-u="loading" style="position: absolute; top: 0px; left: 0px;">
            <div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
            <div style="position:absolute;display:block;background:url('img/loading.gif') no-repeat center center;top:0px;left:0px;width:100%;height:100%;"></div>
        </div>
        <div data-u="slides" style="cursor:default;position:relative;top:0px;left:0px;width:1300px;height:500px;overflow:hidden;">
            <div>
                <img data-u="image" src="img/slide_img1.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/slide_img2.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/slide_img3.jpg" />
            </div>
            <div>
                <img data-u="image" src="img/slide_img4.jpg" />
            </div>
        </div>
        <!-- Bullet Navigator -->
       <div data-u="navigator" class="jssorb057" style="position:absolute;bottom:16px;right:16px;" data-autocenter="1" data-scale="0.5" data-scale-bottom="0.75">
            <div data-u="prototype" class="i" style="width:14px;height:14px;">
                <svg viewbox="0 0 16000 16000" style="position:absolute;top:0;left:0;width:100%;height:100%;">
                    <circle class="b" cx="8000" cy="8000" r="5000"></circle>
                </svg>
            </div>
        </div>
        <!-- Arrow Navigator -->
        <span data-u="arrowleft" class="jssora22l" style="top:0px;left:8px;width:40px;height:58px;" data-autocenter="2"></span>
        <span data-u="arrowright" class="jssora22r" style="top:0px;right:8px;width:40px;height:58px;" data-autocenter="2"></span>
    </div>
	
	<!--컨텐츠 내용1 -->
	<div>
		<h4 style="text-align: center; margin-top: 120px;color: #595959;">
		<span style="font-size: 29px;">실종동물을 찾아요!</span></h4>
	</div>
		
		<div  class="content_box_1" style="width: 1200px; margin: 0 auto; margin-top: 35px;">
		
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody><tr>
			
			<c:forEach var="board" items="${boards}">
				<td valign="top" align="center"><table valign="top" cellspacing="0" style="width:250px;">
			<tbody><tr>
				<td style="width:250px;" valign="top">
					<table cellspacing="0">
			<tbody><tr>
				<td style="padding:3px; border:1px solid #efefef;" bgcolor="#FFFFFF"><a href="animal_find_Content?no=${board.no }"><img src="../img/${board.id}/${board.image}" align="absmiddle" border="0" width="250" height="200" style="border-radius: 10px 10px;"></a></td>
			</tr></tbody></table></td></tr>
				
				<tr>
				<td align="left" valign="top" class="font_st_05" style="padding-left:3px;">
		<table cellspacing="0">
			<tbody><tr>
			<td style="padding-top:5px; width:250px; line-height:18px;  text-overflow:ellipsis; white-space: nowrap; overflow: hidden; display: block;" align="left">
				<span style="color:#f39a68; font:11px '돋움';">${board.animal_group }</span>
				<a href="animal_find_Content?no=${board.no }"><span style="color:#333333; font:12px '돋움';">${board.title }</span></a>
			</td>
			</tr>
			<tr>
			<td style="padding-top:3px; width:250px;" align="left">
				<table cellspacing="0">
				<tbody><tr>
					<td style="font:11px '돋움'; color:#999999; letter-spacing:-1px;">${board.id }</td>
					<td style="font:11px '돋움'; color:#d3d3d3; padding:0 5px;">|</td>
					<td style="font:11px '돋움'; color:#999999;">${board.time }</td>
			</tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></td>
			</c:forEach>
				
				</tr></tbody></table>
		  </div>
	
		<a href="animal_find"><div class="more">더 보기 + </div></a>
            
	
	
	<!-- 컨텐츠 내용 2 -->
	
		<div>
		<h4 style="text-align: center; margin-top: 120px;color: #595959;">
		<span style="font-size: 29px;">보호중인 동물이 있어요!</span></h4>
	</div>
		
		<div  class="content_box_1" style="width: 1200px; margin: 0 auto; margin-top: 35px;">
		
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody><tr>
			
			<c:forEach var="board" items="${boards2}">
				<td valign="top" align="center"><table valign="top" cellspacing="0" style="width:250px;">
			<tbody><tr>
				<td style="width:250px;" valign="top">
					<table cellspacing="0">
			<tbody><tr>
				<td style="padding:3px; border:1px solid #efefef;" bgcolor="#FFFFFF"><a href="animal_protect_Content2?no=${board.no }"><img src="../img/${board.id}/${board.image}" align="absmiddle" border="0" width="250" height="200" style="border-radius: 10px 10px;"></a></td>
			</tr></tbody></table></td></tr>
				
				<tr>
				<td align="left" valign="top" class="font_st_05" style="padding-left:3px;">
		<table cellspacing="0">
			<tbody><tr>
			<td style="padding-top:5px; width:250px; line-height:18px;  text-overflow:ellipsis; white-space: nowrap; overflow: hidden; display: block;" align="left">
				<span style="color:#f39a68; font:11px '돋움';">${board.animal_group }</span>
				<a href="animal_protect_Content2?no=${board.no }"><span style="color:#333333; font:12px '돋움';">${board.title }</span></a>
			</td>
			</tr>
			<tr>
			<td style="padding-top:3px; width:250px;" align="left">
				<table cellspacing="0">
				<tbody><tr>
					<td style="font:11px '돋움'; color:#999999; letter-spacing:-1px;">${board.id }</td>
					<td style="font:11px '돋움'; color:#d3d3d3; padding:0 5px;">|</td>
					<td style="font:11px '돋움'; color:#999999;">${board.time }</td>
			</tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></td>
			</c:forEach>
				
				</tr></tbody></table>
		  </div>
	
		<a href="animal_protect"><div class="more">더 보기 + </div></a>
	
	
	
	<div class="banner" style="width: 1200px; margin: 0 auto; margin-top: 60px; ">
		<a href="#"><img src="../img/banner1.jpg" alt="banner_1"></a>
	</div>
	
		<!--동물 사전-->
		<div class="tableArea clearfix">
			
		<div>
		<h4 style="text-align: center; margin-top: 50px;color: #595959;">
		<span style="font-size: 29px;">동물 백과 사전</span></h4>
		</div>
			
		<div class="animal_info">
		<ul class="">
			<li>
				<div class="photo"><a href="animal_info"><img class="img_left" src="../img/팁1.jpg" title=""></a></div>
			</li>
			<li>
				<div class="photo"><a href="animal_info"><img class="img_left" src="../img/팁2.jpg" title=""></a></div>
			</li>
			<li>
				<div class="photo"><a href="animal_info"><img class="img_left" src="../img/팁3.jpg" title=""></a></div>
			</li>
			<li>
				<div class="photo"><a href="animal_info"><img class="img_left" src="../img/팁4.jpg" title=""></a></div>
			</li>
		</ul>
		</div>
			<a href="animal_info"><div class="more">더 보기 + </div></a>
	</div>
		
</div>

