<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html lang="ko">
<head>
<title>나무커뮤니티</title>
<%@ include file="/WEB-INF/views/common/common.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/mainNavigator.jsp"%>
	<!-- Header ========================================================================================== -->
	<header>
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="jumbotron">
						<h1>나무 커뮤니티와 함께!</h1>
						<p>나무 커뮤니티와 함께 특정 취미와 관심사, 특정 그룹 또는 조직에 관한 대화를 시작하세요.</p>
						<p>
							<a href="${ctx}/club/clubCreate"
								class="btn btn-warning btn-lg">클럽 개설하기</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</header>
	<!-- Container ======================================================================================= -->
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="page-header">
					<h2 id="container">${club.name}의 멤버목록</h2>
				</div>
				<table class="table table-hover" id="memberTable">
					<thead>
						<tr>
							<th>이름</th>
							<th>이메일</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
							<c:forEach var="member" items="${members}">
								<tr>
								<td>${member.name}</td>
								<td>${member.email}</td>
						
								<td>
								<form action="${ctx}/club/clubCommission/${club.clubNo}" method="post">
									<input type="hidden" name="email" value="${member.email}" />
									 <button type="submit" class="btn btn-default btn-sm">위임</button>
								</form></td>
								</tr>
							</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>
</body>
</html>