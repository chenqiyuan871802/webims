<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="addDictIndexForm" action="${ctx }/system/dict/saveDictIndex"
				method="post" >
			
				<table cellpadding=5 cellspacing=0 width=100% align="center" class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label">字典标识：</td>
						<td class="kv-content"><input  type="text"  name="dictKey" required="true"
							class="easyui-textbox" data-options="validType:'keyname'"
							style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label">字典名称：</td>
						<td class="kv-content"> <input  type="text"  name="dictName" required="true"
							class="easyui-textbox" data-options="validType:'length[1,100]'"
							style="width: 280px; " ></td>
					</tr>
					
				   <tr>
						<td class="kv-label">字典分类：</td>
						<td class="kv-content"><input name="dictType" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:dict_typeStore,textField:1,valueField:0"  
							style="width: 280px; " ></td>
					</tr>
					<tr>
						<td class="kv-label">备注：</td>
						<td class="kv-content"><input name="dictRemark" type="text"  class="easyui-textbox"
							data-options="multiline:true,validType:'length[0,1000]'"
							style="width: 280px; height: 80px"></td>
					</tr>
				
					</tbody>
				

				</table>

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'ok'"href="javascript:void(0)"
			   onclick="submitFormData('addDictIndexForm','dictIndexList','addDictIndexWindow','',clearDictCallback)" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'close'" href="javascript:void(0)"
				onclick="closeWindow('addDictIndexWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>