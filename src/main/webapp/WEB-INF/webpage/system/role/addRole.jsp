<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="addDataForm" action="${ctx }/system/role/save"
				method="post" >
			
				<table cellpadding=5 cellspacing=0 width=100% align="center" class="kv-table">
				
					<tr>
						<td class="kv-label">角色名称：</td>
						<td class="kv-content"><input  type="text"  name="roleName" required="true"
							class="easyui-textbox" data-options="validType:'length[1,100]'"
							style="width: 280px; height: 30px" ></td>
					</tr>
					
					
				  <tr>
						<td class="kv-label">角色类型：</td>
						<td class="kv-content"><input name="roleType" type="text" value="1"   editable="false" 
							class="easyui-combobox"   data-options="data:role_typeStore,textField:1,valueField:0"  
							style="width: 280px; height: 30px" ></td>
					</tr>
					
				  <tr>
						<td class="kv-label">当前状态：</td>
						<td class="kv-content"><input name="status" type="text" value="1"   editable="false" 
							class="easyui-combobox"   data-options="data:statusStore,textField:1,valueField:0"  
							style="width: 280px; height: 30px" ></td>
					</tr>
				  <tr>
						<td class="kv-label">编辑模式：</td>
						<td class="kv-content"><input name="editMode" type="text" value="1"   editable="false" 
							class="easyui-combobox"   data-options="data:edit_modeStore,textField:1,valueField:0"  
							style="width: 280px; height: 30px" ></td>
					</tr>
					
					<tr>
						<td class="kv-label">角色描述：</td>
						<td class="kv-content"><input name="roleRemark" type="text"  class="easyui-textbox"
							data-options="multiline:true,validType:'length[0,400]'"
							style="width: 280px; height: 90px"></td>
					</tr>
				
					
				

				</table>

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'ok'"href="javascript:void(0)"
			   onclick="submitFormData('addDataForm','dataList','addDataWindow')" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'close'" href="javascript:void(0)"
				onclick="closeWindow('addDataWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>