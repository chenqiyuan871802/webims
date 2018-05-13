<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/ims.tld" prefix="IMS"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set> 
<!-- EasyUI 需要引入的CSS文件和JS文件 -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/weblib/easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/weblib/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/weblib/easyui/themes/color.css">
<script type="text/javascript" src="${ctx}/static/weblib/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/weblib/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/weblib/easyui/easyui-lang-zh_CN.js"></script>
<!--系统定义的CSS和JS文件  -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/common/css/webims.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/common/css/icon.css">
<script type="text/javascript" src="${ctx}/static/common/js/webims.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/easyuiValidate.js"></script>

  