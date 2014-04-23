<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

        <!-- ★★★ Contents -->
        <div class="col-sm-9 col-lg-9">
            <div class="page-header2">
                <h3>회원관리</h3>
            </div>

            <!-- ★★★ Tab Menu -->
            <ul class="nav nav-tabs" style="margin-bottom: 15px;">
                <li class="active"><a href="#all" data-toggle="tab">전체회원목록</a></li>
            </ul>
            <!-- ★★★ Tab Panel -->
            <div id="communityList" class="tab-content">
                <!-- ★★★ 전체회원 -->
                <div class="tab-pane fade active in" id="all">

                    <!-- ★★★ 검색조건 -->
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <form class="form-search">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="회원명 또는 ID">
                                    <span class="input-group-btn">
                                        <button type="submit" class="btn btn-primary">검색</button>
                                    </span>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- ★★★ 회원목록 -->
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th class="text-center">번호</th>
                                <th class="text-center">ID</th>
                                <th class="text-center">회원명</th>
                                <th class="text-center"><input type="button" value="강퇴"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <!--<tr><td colspan="5" class="text-center">가입한 회원이 없습니다.</td></tr>-->
                            <tr>
                                <td class="text-center">1</td>
                                <td>teo</td>
                                <td><a href="./commViewMember.html">이재욱</a></td>
                                <td>클럽(2), 팀(2)</td>
                                <td>43</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                </div>
                <!-- ★★★ 탈퇴신청 회원 -->
                <div class="tab-pane fade" id="withdraw">
                    <!-- ★★★ 회원목록 -->
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th class="text-center">번호</th>
                                <th class="text-center">ID</th>
                                <th class="text-center">회원명</th>
                                <th class="text-center">탈퇴승인</th>
                            </tr>
                            </thead>
                            <tbody>
                            <!--<tr><td colspan="4" class="text-center">탈퇴를 신청한 회원이 없습니다.</td></tr>-->
                            <tr>
                                <td class="text-center">1</td>
                                <td>teo</td>
                                <td><a href="./commViewMember.html">이재욱</a></td>
                                <td class="text-center">
                                    <button type="button" class="btn btn-success btn-xs">탈퇴승인</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/views/common/footer.jsp"%>
    </div>
</body>
</html>