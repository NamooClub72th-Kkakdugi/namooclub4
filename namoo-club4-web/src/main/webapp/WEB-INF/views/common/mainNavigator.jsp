<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!-- Main Navigation ========================================================================================== -->
<div class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-responsive-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand">${loginUser.name}님이 로그인 중입니다.</a>
		</div>
		<ul class="nav navbar-nav navbar-right">
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown">관리 <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="${ctx}/management/comManagement">커뮤니티 관리센터</a></li>
					<li><a href="${ctx}/management/clubManagement">클럽 관리센터</a></li>
					<li><a href="${ctx}/management/myInfo">내정보 관리센터</a></li>
					<li class="divider"></li>
				</ul></li>
		</ul>
		<div class="navbar-collapse collapse navbar-responsive-collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${ctx}/withdrawl">회원탈퇴</a></li>
				<li><a href="${ctx}/logout">로그아웃</a></li>
			</ul>
		</div>

	</div>
</div>
</html>