<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
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
                    <li class="active"><a href="${ctx}/management/comManagement">커뮤니티 관리</a></li>
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
                    <a href="${ctx}/management/myInfo" class="list-group-item hidden-xs">내정보관리</a>
                    <select class="form-control">
                        <option selected="selected" value="">커뮤니티 관리</option>
                        <option value="${ctx}/management/clubManagement">클럽관리</option>
                        <option value="${ctx}/management/userManagement">회원관리</option>
                        <option value="${ctx}/management/myInfo">내정보관리</option>
                    </select></div>
            </div>
        </div>

        <!-- ★★★ Contents -->
        <div class="col-sm-9 col-lg-9">
            <div class="page-header2">
                <h3>커뮤니티관리</h3>
            </div>

            <!-- ★★★ Tab Menu -->
            <ul class="nav nav-tabs" style="margin-bottom: 15px;">
                <li class="active"><a href="#all" data-toggle="tab">내가 관리하는 커뮤니티 목록</a></li>
            </ul>
            <!-- ★★★ Tab Panel -->
            <div id="communityList" class="tab-content">
                <!-- ★★★ 전체회원 -->
                <div class="tab-pane fade active in" id="all">
	                   <!-- ★★★ 회원목록 -->
	                   <div class="table-responsive">
	                       <table class="table table-striped table-bordered table-hover">
	                           <thead>
	                           <tr>
	                               <th class="text-center">커뮤니티 번호</th>
	                               <th class="text-center">커뮤니티명</th>
	                               <th class="text-center">커뮤니티 설명</th>
	                               <th class="text-center">커뮤니티 멤버수</th>
	                               <th class="text-center"></th>
	                           </tr>
	                           </thead>
	                           <tbody>
	                           <c:forEach var="community" items="${communities}">
	                           <tr>
	                               <td>${community.comNo}</td>
	                               <td>${community.name}</td>
	                               <td>${community.description}</td>
	                               <td>${community.members.size()}</td>
	                               <td class="text-center"><input type="button" onclick="location.href='${ctx}/management/belongComMem/${community.comNo}'" value="멤버목록"></td>
	                           </tr>
	                           </c:forEach>
	                           </tbody>
	                       </table>
	                   </div>
                	</div>
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/views/common/footer.jsp"%>
    </div>
</body>
</html>