<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="addDictForm" action="${ctx }/system/dict/saveDict"
				method="post" >
			 <input type="hidden"  name="dictIndexId" value="${dictIndex.dictIndexId}" />
			 <input type="hidden"  name="dictKey" value="${dictIndex.dictKey}" />
				<table cellpadding=5 cellspacing=0 width=100% align="center" class="kv-table">
					<tr>
						<td class="kv-label">字典标识：</td>
						<td class="kv-content"><input  type="text"  name="dictKey" value="${dictIndex.dictKey }"
							class="easyui-textbox" disabled="true"
							style="width: 280px;" ></td>
					</tr>
					<tr>
						<td class="kv-label">字典名称：</td>
		 				<td class="kv-content"><input  type="text"  name="dictName" value="${dictIndex.dictName }" disabled="true"
							class="easyui-textbox" 
							style="width: 280px;" ></td>
					</tr>
					<tr>
						<td class="kv-label">字典对照码：</td>
						<td class="kv-content"><input  type="text"  name="dictCode"  required="true" data-options="validType:'length[1,100]'"
							class="easyui-textbox" style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label">字典对照值：</td>
						<td class="kv-content"><input  type="text"  name="dictValue"  required="true" data-options="validType:'length[1,100]'"
							class="easyui-textbox" style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label">显示颜色：</td>
						<td class="kv-content"><input  type="text"  name="showColor" data-options="validType:'length[0,20]'"
							class="easyui-textbox" style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label">当前状态：</td>
						<td class="kv-content"><input name="status" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:statusStore,textField:1,valueField:0"  
							style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label">编辑模式：</td>
						<td class="kv-content"><input name="editMode" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:edit_modeStore,textField:1,valueField:0"  
							style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label">排序号：</td>
						<td class="kv-content"><input name="sortNo" type="text"
							class="easyui-numberspinner" value="1" data-options="min:1,max:1000000,required:true"
							style="width: 280px; " required="true"></td>
					</tr>
				
				 
				
				
					
				

				</table>

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-save'"href="javascript:void(0)"
			   onclick="submitFormData('addDictForm','dictList','addDictWindow')" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
				onclick="closeWindow('addDictWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>