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
						<h1>나무 커뮤니티와 함께!</h1>
						<p>나무 커뮤니티와 함께 특정 취미와 관심사, 특정 그룹 또는 조직에 관한 대화를 시작하세요.</p>
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
					<h2 id="container">커뮤니티 개설하기</h2>
				</div>

				<div class="well">
					<p>나와 같은 관심사를 가진 멤버를 모집하고 열심히 운영하여 커뮤니티를 성장시켜 보세요.</p>
					<form class="form-horizontal" action="${ctx}/community/comCreateCheck.do" method="post">
						<fieldset>
							<div class="form-group">
								<label class="col-lg-2 control-label">커뮤니티명</label>

								<div class="col-lg-10">
									<input type="text" name="communityName" class="form-control" placeholder="커뮤니티명" required>
								</div>
							</div>
							<div class="form-group">
								<label for="textArea"class="col-lg-2 control-label">커뮤니티 대표문구</label>

								<div class="col-lg-10">
									<textarea name="description" class="form-control" rows="3" id="textArea" required></textarea>
									<span class="help-block">커뮤니티를 소개하는 대표문구를 입력해 주세요. 커뮤니티 홈화면에
										입력하신 문구가 출력됩니다.</span>
								</div>
							</div>
								
							<div class="form-group">
                           		<label class="col-lg-2 control-label">클럽 카테고리</label>

                            	<div class="col-lg-10">
                              	 	 <input type="text" class="form-control" placeholder="카테고리 1" name="ctgr1">
	                             	<input type="text" class="form-control" placeholder="카테고리 2" name="ctgr2">
	                                <input type="text" class="form-control" placeholder="카테고리 3" name="ctgr3">
	                                <input type="text" class="form-control" placeholder="카테고리 4" name="ctgr4">
	                                <input type="text" class="form-control" placeholder="카테고리 5" name="ctgr5">
	                                <input type="text" class="form-control" placeholder="카테고리 6" name="ctgr6">
	                            </div>
	                        </div>
							<div class="form-group">
								<div class="col-lg-10 col-lg-offset-2">
									<button class="btn btn-primary" type="submit">확인</button>
									<button class="btn btn-default" onclick="location.href='${ctx}/community/comList.do'; return false;">취소</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>

		</div>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>
</body>
</html>