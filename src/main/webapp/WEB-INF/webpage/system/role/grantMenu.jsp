<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
<script type="text/javascript">
 $(function(){
	
	$("#menuTree").tree({
			url:'${ctx }/system/role/listRoleMenu?roleId=${roleId}',
		    method:'get',
			checkbox : true,
			cascadeCheck : false,
			animate:true,
			lines:true,
			onCheck : function(node, checked) {
				if (checked) {
					var parentNode = $("#menuTree").tree('getParent', node.target);
					if (parentNode != null) {
						$("#menuTree").tree('check', parentNode.target);
					}
				} else {
					var childNode = $("#menuTree").tree('getChildren', node.target);
					if (childNode.length > 0) {
						for (var i = 0; i < childNode.length; i++) {
							$("#menuTree").tree('uncheck', childNode[i].target);
						}
					}
				}
			}
		});

	})

	function submitGrantMenuData() {
		var innodes = $('#menuTree').tree('getChecked', 'indeterminate');
		var nodes = $('#menuTree').tree('getChecked');
		var menuIds = '';
		for (var i = 0; i < innodes.length; i++) {
			if (menuIds != '') {
				menuIds += ','
			}
			;
			menuIds += innodes[i].id;
		}
		for (var i = 0; i < nodes.length; i++) {
			if (menuIds != '') {
				menuIds += ','
			}
			;
			menuIds += nodes[i].id;
		}
		if (menuIds == '') {
			$.messager.alert('警告信息', '请选择你要授权的菜单', 'warning');
			return;
		}
		$("#menuIds").val(menuIds);
		submitFormData('grantMenuForm', 'dataList', 'grantMenuWindow');
	}
</script>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="grantMenuForm" action="${ctx }/system/role/saveRoleMenu"
				method="post" >
				<input type="hidden" name="roleId" value="${roleId}"  />
				<input type="hidden" name="menuIds" id="menuIds"  />
			<ul id="menuTree" class="easyui-tree" ></ul>
				

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'ok'"href="javascript:void(0)"
			   onclick="submitGrantMenuData()" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'close'" href="javascript:void(0)"
				onclick="closeWindow('grantMenuWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>