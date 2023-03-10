<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
<script type="text/javascript">
 function submitMoveUserData(){
	var treeNode= $("#deptTreeByMove").tree('getSelected')  
	if(treeNode){
		var deptId=treeNode.id;
		$("#modeDeptId").val(deptId);
		submitFormData('moveUserForm','dataList','moveUserWindow')
	}else{
		$.messager.alert('警告信息', '请选择用户所属的机构', 'warning');
	}
 
 }
</script>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="moveUserForm" action="${ctx }/system/user/saveMoveUser"
				method="post" >
				<input type="hidden" name="ids" value="${ids}" />
				<input type="hidden" name="deptId" id="modeDeptId" />
			 <ul id="deptTreeByMove" class="easyui-tree" data-options="url:'${ctx }/system/dept/loadDeptTree',method:'get',animate:true,lines:true"></ul>
				

			</form>
		</div>
		<div data-options="region:'south',border:false" height="35px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'ok'"href="javascript:void(0)"
			   onclick="submitMoveUserData()" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'close'" href="javascript:void(0)"
				onclick="closeWindow('moveUserWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>