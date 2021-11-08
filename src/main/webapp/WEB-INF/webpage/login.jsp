<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录后台管理系统</title>
<link rel="shortcut icon"
			href="${ctx }/static/common/images/favicon.ico">
<link rel="stylesheet" type="text/css" href="${ctx }/static/common/css/login.css">
<script type="text/javascript" src="${ctx}/static/weblib/easyui/jquery.min.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/weblib/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/weblib/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/weblib/easyui/themes/color.css">
<script type="text/javascript" src="${ctx}/static/weblib/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/weblib/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/weblib/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/webplus.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/md5.js"></script>
<script language="javascript">
$(function () {
	webplus.setName('ctx', '${ctx}')
	$('.loginbox').css({ 'position': 'absolute', 'left': ($(window).width() - 692) / 2 });
	$(window).resize(function () {
		$('.loginbox').css({ 'position': 'absolute', 'left': ($(window).width() - 692) / 2 });

	})
	var accessToken = '${accessToken}';
	if (!webplus.isEmpty(accessToken)) {
		thirdLogin(accessToken);
	}
});

function thirdLogin(accessToken) {
	var params = {};
	params.accessToken = accessToken;
	doAjax('${ctx}/thirdLogin', params, '', '', '', function (data) {
		if (data.appCode == "1") {
			webplus.setToken(data.token);
			webplus.setName("account", data.userToken.account);
			$("#token").val(data.userToken.token);
			document.getElementById("toMainForm").submit();
		}
	});

}
function login() {
	var account = $("#account").val();
	var password = $("#password").val();
	if (account == "") {

		$.messager.alert('警告信息', '用户账号不能为空，请输入', 'warning');
		return;
	}
	if (password == "") {

		$.messager.alert('警告信息', '用户密码不能为空，请输入', 'warning');
		return;
	}

	var paramData = {};
	paramData.account = account;
	paramData.password = hex_md5(password);
	doAjax('${ctx}/doLogin', paramData, '', '', '', function (data) {
		if (data.appCode == "1") {
			webplus.setToken(data.token);
			webplus.setName("account", data.user.account);
			$("#token").val(data.token);
			document.getElementById("toMainForm").submit();
		}
	})

}
function enterSumbit() {
	if (event.keyCode == 13) {
		login();
	}

}

</script> 

</head>

<body style="background-color:#1c77ac; background-image:url(${ctx }/static/common/images/system/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">



    <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>  



    
    <div class="loginbody">
    
    <span class="systemlogo"></span> 
       
    <div class="loginbox">
    
    <ul>
    <li><input name="account" id="account" type="text" class="account" /></li>
    <li><input name="password"  id="password"   type="password" class="password"  onkeydown="javascript:enterSumbit();"/></li>
    <li>
    <input name="" type="button" class="loginbtn" value="登录"  onclick="login()"  />
    	
    </li>
    </ul>
    
    
    </div>
    
    </div>
    <form id="toMainForm" class="loginForm" action="${ctx}/system/main/initMain" method="post">
					<input id="token" name="token" type="hidden" />

				</form>
</body>

</html>
