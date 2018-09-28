<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="editDictForm" action="${ctx }/system/dict/updateDict"
				method="post" >
			 <input type="hidden"  name="oldDictCode" value="${dict.dictCode}" />
			 <input type="hidden"  name="dictKey" value="${dictIndex.dictKey}" />
			 <input type="hidden"  name="dictId" value="${dict.dictId}" />
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
						<td class="kv-content"><input  type="text"  name="dictCode" value="${dict.dictCode}"  required="true" data-options="validType:'length[1,100]'"
							class="easyui-textbox" style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label" >字典对照值：</td>
						<td class="kv-content"><input  type="text"  name="dictValue" value="${dict.dictValue }" required="true" data-options="validType:'length[1,100]'"
							class="easyui-textbox" style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label" >显示颜色：</td>
						<td class="kv-content"><input  type="text"  name="showColor" value="${dict.showColor }" data-options="validType:'length[0,20]'"
							class="easyui-textbox" style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label" >当前状态：</td>
						<td class="kv-content" ><input name="status" type="text" value="${dict.status }"  editable="false" 
							class="easyui-combobox"   data-options="data:statusStore,textField:1,valueField:0"  
							style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label" >编辑模式：</td>
						<td class="kv-content" ><input name="editMode" value="${dict.editMode }" type="text"  editable="false" 
							class="easyui-combobox"   data-options="data:edit_modeStore,textField:1,valueField:0"  
							style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label">排序号：</td>
						<td class="kv-content" ><input name="sortNo" type="text" value="${dict.sortNo }"
							class="easyui-numberspinner"  data-options="min:1,max:1000000,required:true"
							style="width: 280px; " required="true"></td>
					</tr>
				
				 
				
				
					
				

				</table>

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'ok'"href="javascript:void(0)"
			   onclick="submitFormData('editDictForm','dictList','editDictWindow')" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'close'" href="javascript:void(0)"
				onclick="closeWindow('editDictWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>