<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
							<a href="${ctx}/club/clubCreateInput.xhtml"
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
					<h2 id="container">${clubName}의멤버목록</h2>
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
						
								<td> <button type="button" class="btn btn-default btn-sm" onclick="location.href='${ctx}/commission/clubCommission.do?clubNo=${clubNo}&email=${member.email}&comNo=${comNo}'">위임</button></td>
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