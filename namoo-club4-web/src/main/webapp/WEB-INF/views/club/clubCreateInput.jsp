<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<title>나무커뮤니티 - 클럽개설</title>
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
					<h1>${community.name}</h1>
					<p>${community.description}</p>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12 col-lg-12">
				<ol class="breadcrumb">
					<li><a href="#">Home</a></li>
					<li class="active">건강커뮤니티</li>
				</ol>
			</div>
		</div>
	</div>
	</header>

	<!-- Container ======================================================================================= -->
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<!-- ★★★ Contents -->
				<div class="page-header">
					<h2 id="container">클럽 개설하기</h2>
				</div>

				<div class="well">
					<p>나와 같은 관심사를 가진 멤버를 모집하고 열심히 운영하여 클럽을 성장시켜 보세요.</p>
					<form class="form-horizontal" action="${ctx}/club/clubCreateCheck" method="post">
					<input type="hidden" name="communityNo" value="${community.comNo}" />
						<fieldset>
							<div class="form-group">
								<label class="col-lg-2 control-label">클럽 카테고리</label>

								<div class="col-lg-10">
									<select class="form-control" id="select" name="categoryNo">
									<c:forEach var="category" items="${community.categories}">
										<option value="${category.categoryNo}">${category.categoryName}</option>
									</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label" >클럽명</label>

								<div class="col-lg-10">
									<input type="text" name="clubName" class="form-control" placeholder="클럽명" required>
								</div>
							</div>
							<div class="form-group">
								<label for="textArea" class="col-lg-2 control-label">클럽 대표문구</label>

								<div class="col-lg-10">
									<textarea class="form-control" rows="3" id="textArea" name="clubDescription" required></textarea>
									<span class="help-block">클럽을 소개하는 대표문구를 입력해 주세요. 클럽 홈화면에
										입력하신 문구가 출력됩니다.</span>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-10 col-lg-offset-2">
									<button type="submit" class="btn btn-primary">확인</button>
									<button class="btn btn-default" onclick="location.href='${ctx}/club/clubList.do?comNo=${community.comNo}'; return false;">취소</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>