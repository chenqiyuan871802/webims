<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="addParentMenuForm" action="${ctx }/system/menu/saveParentMenu"
				method="post" >
				<input type="hidden" name="menuType" value="1" />
				<table cellpadding=5 cellspacing=0 width=100% align="center" class="kv-table">
				    
					<tr>
						<td class="kv-label">上级菜单：</td>
						<td class="kv-content"><input  type="text"  name="parentId"	class="easyui-combotree"
							data-options="url:'${ctx }/system/menu/loadMenuTree?menuType=1&parentId=0',method:'get',value:0" required="true"
							style="width: 280px; height: 30px" ></td>
					</tr>
					 <tr>
						<td class="kv-label">菜单编码：</td>
						<td class="kv-content"><input name="menuCode" type="text"
							class="easyui-textbox" data-options="validType:'keyname'"
							style="width: 280px; height: 30px" required="true"></td>
					</tr>
				   <tr>
						<td class="kv-label">菜单名称：</td>
						<td class="kv-content"><input name="menuName" type="text"
							class="easyui-textbox" data-options="validType:'length[1,100]'"
							style="width: 280px; height: 30px" required="true"></td>
					</tr>
					
				
					<tr>
						<td class="kv-label">自动展开：</td>
						<td class="kv-content"><input name="isAutoExpand" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:is_auto_expandStore,textField:1,valueField:0"  
							style="width: 280px; height: 30px" ></td>
					</tr>
					<tr>
						<td class="kv-label">节点图标：</td>
						<td class="kv-content"><input name="iconName" type="text"
							class="easyui-textbox" data-options="validType:'length[1,100]'"
							style="width: 280px; height: 30px" ></td>
					</tr>
					<tr>
						<td class="kv-label">当前状态：</td>
						<td class="kv-content"><input name="status" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:statusStore,textField:1,valueField:0"  
							style="width: 280px; height: 30px" ></td>
					</tr>
					<tr>
						<td class="kv-label">编辑模式：</td>
						<td class="kv-content"><input name="editMode" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:edit_modeStore,textField:1,valueField:0"  
							style="width: 280px; height: 30px" ></td>
					</tr>
					<tr>
						<td class="kv-label">排序号：</td>
						<td class="kv-content"><input name="sortNo" type="text"
							class="easyui-numberspinner" value="1" data-options="min:1,max:1000000,required:true"
							style="width: 280px; height: 30px" required="true"></td>
					</tr>
				   <tr>
						<td class="kv-label">备注：</td>
						<td class="kv-content"><input name="remark" type="text"  class="easyui-textbox"
							data-options="multiline:true,validType:'length[0,500]'"
							style="width: 280px; height: 60px"></td>
					</tr>
				

				</table>

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'ok'"href="javascript:void(0)"
			   onclick="submitFormData('addParentMenuForm','','addParentMenuWindow','',submitCallBack)" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'close'" href="javascript:void(0)"
				onclick="closeWindow('addParentMenuWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>