<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
    <title>webims</title>
   <link rel="stylesheet"
	href="${ctx}/static/weblib/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${ctx}/static/webframe/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/static/webframe/css/index.css">
<link rel="stylesheet" href="${ctx}/static/webframe/css/skins/_all-skins.css">

</head>
<body class="hold-transition skin-blue sidebar-mini" style="overflow:hidden;">
    <div id="ajax-loader" style="cursor: progress; position: fixed; top: -50%; left: -50%; width: 200%; height: 200%; background: #fff; z-index: 10000; overflow: hidden;">
        <img src="${ctx}/static/webframe/images/ajax-loader.gif" style="position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: auto;" />
    </div>
    <div class="wrapper">
        <!--头部信息-->
        <header class="main-header">
            <a href="http://www.learun.cn/adms/index.html" target="_blank" class="logo">
                <span class="logo-mini">ERMS</span>
                <span class="logo-lg"><strong>权限管理系统</strong></span>
            </a>
            <nav class="navbar navbar-static-top">
                <a class="sidebar-toggle">
                    <span class="sr-only">Toggle navigation</span>
                </a>
                <div class="navbar-custom-menu">
                    <ul class="nav navbar-nav">
                        <li class="dropdown messages-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-envelope-o "></i>
                                <span class="label label-success">4</span>
                            </a>
                        </li>
                        <li class="dropdown notifications-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-bell-o"></i>
                                <span class="label label-warning">10</span>
                            </a>
                        </li>
                        <li class="dropdown tasks-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-flag-o"></i>
                                <span class="label label-danger">9</span>
                            </a>
                        </li>
                        <li class="dropdown user user-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <img src="${ctx}/static/webframe/images/user2-160x160.jpg" class="user-image" alt="User Image">
                                <span class="hidden-xs">${user.username }</span>
                            </a>
                            <ul class="dropdown-menu pull-right">
                                <li><a class="menuItem" data-id="userInfo" href="/SystemManage/User/Info"><i class="fa fa-user"></i>个人信息</a></li>
                                <li><a href="javascript:void();"><i class="fa fa-trash-o"></i>清空缓存</a></li>
                                <li><a href="javascript:void();"><i class="fa fa-paint-brush"></i>皮肤设置</a></li>
                                <li class="divider"></li>
                                <li><a href="#" onclick="logout()"><i class="ace-icon fa fa-power-off"></i>安全退出</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
        <!--左边导航-->
        <div class="main-sidebar">
            <div class="sidebar">
                <div class="user-panel">
                    <div class="pull-left image">
                        <img src="${ctx}/static/webframe/images/user2-160x160.jpg" class="img-circle" alt="User Image">
                    </div>
                    <div class="pull-left info">
                        <p>${user.username }</p>
                        <a><i class="fa fa-circle text-success"></i>在线</a>
                    </div>
                </div>
                <!--  
                <form action="#" method="get" class="sidebar-form">
                    <div class="input-group">
                        <input type="text" name="q" class="form-control" placeholder="Search...">
                        <span class="input-group-btn">
                            <a class="btn btn-flat"><i class="fa fa-search"></i></a>
                        </span>
                    </div>
                </form>
                -->
                <ul class="sidebar-menu" id="sidebar-menu">
                    <!--<li class="header">导航菜单</li>-->
                </ul>
            </div>
        </div>
        <!--中间内容-->
        <div id="content-wrapper" class="content-wrapper">
            <div class="content-tabs">
                <button class="roll-nav roll-left tabLeft">
                    <i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs menuTabs">
                    <div class="page-tabs-content" style="margin-left: 0px;">
                        <a href="javascript:;" class="menuTab active" data-id="mainIndex">欢迎首页</a>
                       
                       
                    </div>
                </nav>
                <button class="roll-nav roll-right tabRight">
                    <i class="fa fa-forward" style="margin-left: 3px;"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown tabClose" data-toggle="dropdown">
                        页签操作<i class="fa fa-caret-down" style="padding-left: 3px;"></i>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li><a class="tabReload" href="javascript:void();">刷新当前</a></li>
                        <li><a class="tabCloseCurrent" href="javascript:void();">关闭当前</a></li>
                        <li><a class="tabCloseAll" href="javascript:void();">全部关闭</a></li>
                        <li><a class="tabCloseOther" href="javascript:void();">除此之外全部关闭</a></li>
                    </ul>
                </div>
                <button class="roll-nav roll-right fullscreen"><i class="fa fa-arrows-alt"></i></button>
            </div>
            <div class="content-iframe" style="overflow: hidden;">
                <div class="mainContent" id="content-main" style="margin-top: 0px; margin-left: 1px; margin-right: 0px; margin-bottom: 0px; padding: 0;">
                    <iframe class="LRADMS_iframe" width="100%" height="100%" src="${ctx}/system/main/mainIndex?token=${token}" frameborder="0" data-id="mainIndex"></iframe>
                </div>
            </div>
        </div>
    </div>
   <script src="${ctx}/static/weblib/jquery/jQuery-2.2.0.min.js"></script>
	<script src="${ctx}/static/weblib/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctx}/static/webframe/js/index.js"></script>
	<script type="text/javascript" src="${ctx}/static/common/js/webplus.js"></script>
    <script type="text/javascript">
		$(function() {
			var ctx = '${ctx}'
			var data =${menuJson};
			$.learunindex.load();
			$.learunindex.loadMenu(ctx, data);
			$.learuntab.init();
		});
		//退出清空token
		function logout(){
			var token=webplus.getToken();
			webplus.removeToken() ;
			window.location.href='${ctx}/logout?token='+token;
		}
	</script>
</body>
</html>
