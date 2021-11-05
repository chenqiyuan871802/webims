<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="editParentMenuForm" action="${ctx }/system/menu/updateParentMenu"
				method="post" >
				<input type="hidden" name="menuId" value="${menu.menuId }" />
				<table cellpadding=5 cellspacing=0 width=100% align="center" class="kv-table">
				    
					<tr>
						<td class="kv-label">上级菜单：</td>
						<td class="kv-content"><input  type="text" disabled="true"  value="${menu.parentId}" name="parentId"	class="easyui-combotree"
							data-options="url:'${ctx }/system/menu/loadMenuTree?menuType=1&parentId=0',method:'get'" required="true"
							style="width: 280px; height: 30px" ></td>
					</tr>
					 <tr>
						<td class="kv-label">菜单编码：</td>
						<td class="kv-content"><input name="menuCode"  value="${menu.menuCode }" type="text"
							class="easyui-textbox" data-options="validType:'keyname'"
							style="width: 280px; height: 30px" required="true"></td>
					</tr>
				   <tr>
						<td class="kv-label">菜单名称：</td>
						<td class="kv-content"><input name="menuName" value="${menu.menuName}" type="text"
							class="easyui-textbox" data-options="validType:'length[1,100]'"
							style="width: 280px; height: 30px" required="true"></td>
					</tr>
					
				
					<tr>
						<td class="kv-label">自动展开：</td>
						<td class="kv-content"><input name="isAutoExpand" type="text" value="${menu.isAutoExpand }"  editable="false" 
							class="easyui-combobox"   data-options="data:is_auto_expandStore,textField:1,valueField:0"  
							style="width: 280px; height: 30px" ></td>
					</tr>
					<tr>
						<td class="kv-label">节点图标：</td>
						<td class="kv-content"><input name="iconName" type="text" value="${menu.iconName }"
							class="easyui-textbox" data-options="validType:'length[1,100]'"
							style="width: 280px; height: 30px" ></td>
					</tr>
					<tr>
						<td class="kv-label">当前状态：</td>
						<td class="kv-content"><input name="status" type="text" value="${menu.status }"  editable="false" 
							class="easyui-combobox"   data-options="data:statusStore,textField:1,valueField:0"  
							style="width: 280px; height: 30px" ></td>
					</tr>
					<tr>
						<td class="kv-label">编辑模式：</td>
						<td class="kv-content"><input name="editMode" type="text" value="${menu.editMode}"  editable="false" 
							class="easyui-combobox"   data-options="data:edit_modeStore,textField:1,valueField:0"  
							style="width: 280px; height: 30px" ></td>
					</tr>
					<tr>
						<td class="kv-label">排序号：</td>
						<td class="kv-content"><input name="sortNo" type="text"
							class="easyui-numberspinner" value="${menu.sortNo }" data-options="min:1,max:1000000,required:true"
							style="width: 280px; height: 30px" required="true"></td>
					</tr>
				   <tr>
						<td class="kv-label">备注：</td>
						<td class="kv-content"><input name="remark" value="${menu.remark}"  type="text"  class="easyui-textbox"
							data-options="multiline:true,validType:'length[0,500]'"
							style="width: 280px; height: 60px"></td>
					</tr>
				

				</table>

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'ok'"href="javascript:void(0)"
			   onclick="submitFormData('editParentMenuForm','','editParentMenuWindow','',submitCallBack)" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'close'" href="javascript:void(0)"
				onclick="closeWindow('editParentMenuWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>