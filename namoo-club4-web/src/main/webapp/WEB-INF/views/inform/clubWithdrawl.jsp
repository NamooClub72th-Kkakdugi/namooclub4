<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>나무커뮤니티</title>
<%@ include file="/WEB-INF/views/common/common.jsp"%>
<style type="text/css">
body {
	padding-top: 100px;
	padding-bottom: 40px;
	background-color: #ecf0f1;
}

.info-header {
	max-width: 500px;
	padding: 15px 29px 25px;
	margin: 0 auto;
	background-color: #18bc9c;
	color: white;
	text-align: left;
	-webkit-border-radius: 15px 15px 0px 0px;
	-moz-border-radius: 15px 15px 0px 0px;
	border-radius: 15px 15px 0px 0px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.info-footer {
	max-width: 500px;
	margin: 0 auto 20px;
	padding-left: 10px;
}

.info-body {
	max-width: 500px;
	padding: 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	-webkit-border-radius: 0px 0px 15px 15px;
	-moz-border-radius: 0px 0px 15px 15px;
	border-radius: 0px 0px 15px 15px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.info-heading {
	margin-bottom: 15px;
}

.info-btn {
	text-align: center;
	padding-top: 20px;
}
</style>
</head>
<body>
<%@ include file="/WEB-INF/views/common/mainNavigator.jsp"%>
	<div class="container">

		<!-- header -->
		<div class="info-header">
			<h2 class="info-heading">안내</h2>
		</div>

		<!-- body -->
		<div class="info-body">
			<form action="${ctx}/club/clubList.do" method="get">
				<h3>탈퇴 안내</h3>
				<input type="hidden" name="cmId" value="${cmId}" />
				<p>탈퇴가 완료되었습니다.</p>
				<input type="submit" value="확인" />
			</form>
		</div>

		<!-- footer -->
		<div class="info-footer">
			<p>© NamooSori 2014.</p>
		</div>
	</div>
</body>
</html>