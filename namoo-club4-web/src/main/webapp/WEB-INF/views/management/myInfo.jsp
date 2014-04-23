<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
						<h2>커뮤니티 관리센터</h2>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 col-lg-12">
					<ol class="breadcrumb">
						<li><a href="#">관리자 홈</a></li>
						<li><a href="#">커뮤니티 관리</a></li>
						<li class="active">회원관리</li>
					</ol>
				</div>
			</div>
		</div>
	</header>

	<!-- Container ======================================================================================= -->
<div class="container">
    <div class="row">
        <!-- ★★★ Aside Menu -->
        <div style="z-index:1020" class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
            <div class="list-group panel panel-success">
                <div class="panel-heading list-group-item text-center hidden-xs">
                    <h4>커뮤니티 관리</h4>
                </div>
                <div id="cat-navi">
                    <a href="${ctx}/management/clubManagement" class="list-group-item hidden-xs">클럽관리</a>
                    <a href="${ctx}/management/userManagement" class="list-group-item hidden-xs">회원관리</a>
                    <select class="form-control">
                        <option selected="selected" value="">커뮤니티 관리</option>
                        <option value="${ctx}/management/clubManagement">클럽관리</option>
                        <option value="${ctx}/management/userManagement">회원관리</option>
                        <option value="${ctx}/management/myInfo">내정보관리</option>
                    </select></div>
            </div>
        </div>
			<div class="col-sm-9 col-lg-9">
				<div class="page-header2">
					<h3>회원관리</h3>
				</div>
				<div class="table-responsive">
					<table class="table table-bordered">

						<colgroup>
							<col width="120">
							<col width="*">
							<col width="120">
							<col width="*">
						</colgroup>
						<tbody>
							<tr>
								<th class="text-center">ID</th>
								<td class="text-left">${user.email}</td>
								<th class="text-center">회원명</th>
								<td class="text-left">${user.name}</td>
							</tr>
						</tbody>
					</table>
				</div>
				<h4>가입 클럽</h4>
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="text-center">가입커뮤니티</th>
								<th class="text-center">커뮤니티관리자</th>
								<th class="text-center">가입클럽</th>
								<th class="text-center">클럽대표관리자</th>
								<th class="text-center">클럽관리자</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="community" items="${communities}">
						<tr>
						<td>${community.name}</td>
						<c:choose>
							<c:when test="${community.isManager}"><td class="text-center">예</td></c:when>
							<c:otherwise><td class="text-center">아니오</td></c:otherwise>
						</c:choose>
						</tr>
						
						</c:forEach>
							<c:forEach var="club" items="${clubs}">
								<tr>
									<td>${club.name}</td>
									<c:choose>
										<c:when test="${club.isKingManager()}">
											<td class="text-center">예</td>
										</c:when>
										<c:otherwise>
											<td class="text-center">아니오</td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${club.isManager()}">
											<td class="text-center">예</td>
										</c:when>
										<c:otherwise>
											<td class="text-center">아니오</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>
</body>
</html>