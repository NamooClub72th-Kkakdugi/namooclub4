<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
						<h1>우리동네 축구클럽</h1>
						<p>우리동네 축구클럽은 해뜨자마자 바로 시작해요.</p>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-12 col-lg-12">
					<ol class="breadcrumb">
						<li><a href="#">Home</a></li>
						<li><a href="#">건강커뮤니티</a></li>
						<li class="active">우리동네 축구클럽</li>
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
					<h2 id="container">클럽멤버 가입하기</h2>
				</div>

				<div class="well">
					<p>아래 질문 내용들을 정성껏 작성해 주세요.</p>
						<fieldset>
							<div class="form-group">
								<label class="col-lg-2 control-label">클럽 가입 목적은 무엇입니까?</label>

								<div class="col-lg-10">
									<input type="text" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label for="textArea" class="col-lg-2 control-label">클럽 운영자에게 하고
									싶은 말씀은 무엇인가요?</label>
								<div class="col-lg-10">
									<textarea class="form-control" rows="3" id="textArea"></textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-10 col-lg-offset-2">
									<button class="btn btn-primary" onclick="location.href='${ctx}/club/clubJoin.do?comNo=${comNo}&clubNo=${clubNo}'">확인</button>
									<button class="btn btn-default">취소</button>
								</div>
							</div>
						</fieldset>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/views/common/footer.jsp"%>

	</div>
</body>
</html>