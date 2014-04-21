<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html">
<html>
<head>
<title>해당 클럽의 멤버 리스트</title>
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
						<form action="${ctx}/club/clubCreateInput.do" method="get">
							<h1>${communityName}</h1>
							<p>${description}</p>
							<p>
								<input type="hidden" name="name" value="${name}" /> <input
									type="hidden" name="comNo" value="${comNo}" /> <input
									type="submit" class="btn btn-warning btn-lg" value="클럽 개설하기">
							</p>
						</form>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-12 col-lg-12">
					<ol class="breadcrumb">
						<li><a href="${ctx}/community/comList.do?name=${name}">Home</a></li>
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
				<!-- ★★★ Tab Menu -->
				<ul class="nav nav-tabs" style="margin-bottom: 15px;">
					<li class="active"><a href="#joined" data-toggle="tab">가입
							클럽</a></li>
					<li class=""><a href="#unjoinded" data-toggle="tab">미가입 클럽</a></li>
				</ul>

				<!-- ★★★ Tab Panel -->
				<div id="communityList" class="tab-content">
					<!-- ★★★ 가입 클럽 -->
					<div class="tab-pane fade active in" id="joined">
						<div class="page-header">
							<h2 id="container">가입 클럽</h2>
						</div>

						<form action="${ctx}/inform/comWithdrawlCheck.do?name=${name}"
							method="post">
							<ul class="list-group">
								<c:forEach var="club" items="${joinClubs}">
									<ul class="list-group">
										<li class="list-group-item"><span class="badge"></span>
											<h4>
												<c:if test="${club.manager.name == name}">
													<span class="label label-warning">관리자</span>
												</c:if>
												<span class="label label-primary">${club.category}</span>&nbsp;
												<a href="../team/index.html">${club.name}&nbsp;(회원수 :
													${club.members.size()})</a>
											</h4>
											<p>${club.description}</p> <c:choose>
												<c:when test="${club.manager.name == name}">
													<button type="button" class="btn btn-default btn-sm"
														onclick="location.href='${ctx}/inform/clubRemoveCheck.do?clubNo=${club.clubNo}&name=${name}&comNo=${comNo}'">클럽
														삭제하기</button>
													<button type="button" class="btn btn-default btn-sm"
														disabled="disabled"
														onclick="location.href='${ctx}/inform/clubWithdrawlCheck.do?clubNo=${club.clubNo}&name=${name}&comNo=${comNo}'">멤버탈퇴
														신청하기</button>
												</c:when>
												<c:otherwise>
													<button type="button" class="btn btn-default btn-sm"
														disabled="disabled"
														onclick="location.href='${ctx}/inform/clubRemoveCheck.do?clId=${club.id}&name=${name}&cmId=${cmId}'">클럽
														삭제하기</button>
													<button type="button" class="btn btn-default btn-sm"
														onclick="location.href='${ctx}/inform/clubWithdrawlCheck.do?clId=${club.id}&name=${name}&cmId=${cmId}'">멤버탈퇴
														신청하기</button>
												</c:otherwise>
											</c:choose>
										</li>
									</ul>
								</c:forEach>
							</ul>
						</form>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>
</body>
</html>