<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="addDataForm" action="${ctx }/system/param/save"
				method="post" >
			
				<table cellpadding=5 cellspacing=0 width=100% align="center" class="kv-table">
				
					<tr>
						<td class="kv-label">参数名称：</td>
						<td class="kv-content"> <input  type="text"  name="paramName" required="true"
							class="easyui-textbox" data-options="validType:'length[1,100]'"
							style="width: 280px;" ></td>
					</tr>
					<tr>
						<td class="kv-label">参数键：</td>
						<td class="kv-content"><input  type="text"  name="paramKey" required="true"
							class="easyui-textbox" data-options="validType:'keyname'"
							style="width: 280px;" ></td>
					</tr>
					
				   <tr>
						<td class="kv-label">参数值：</td>
						<td class="kv-content"> <input name="paramValue" type="text"
							class="easyui-textbox" data-options="validType:'length[1,500]'"
							style="width: 280px;" required="true"></td>
					</tr>
				   <tr>
						<td class="kv-label">参数分类：</td>
						<td class="kv-content"><input name="paramType" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:param_typeStore,textField:1,valueField:0"  
							style="width: 280px;" ></td>
					</tr>
					<tr>
						<td class="kv-label">当前状态：</td>
						<td class="kv-content"><input name="status" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:statusStore,textField:1,valueField:0"  
							style="width: 280px;" ></td>
					</tr>
					<tr>
						<td class="kv-label">编辑模式：</td>
						<td class="kv-content"><input name="editMode" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:edit_modeStore,textField:1,valueField:0"  
							style="width: 280px;" ></td>
					</tr>
				
					
					<tr>
						<td class="kv-label">备注：</td>
						<td class="kv-content"><input name="paramRemark" type="text"  class="easyui-textbox"
							data-options="multiline:true,validType:'length[0,200]'"
							style="width: 280px; height: 80px"></td>
					</tr>
				
					
				

				</table>

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-save'"href="javascript:void(0)"
			   onclick="submitFormData('addDataForm','dataList','addDataWindow')" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
				onclick="closeWindow('addDataWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>