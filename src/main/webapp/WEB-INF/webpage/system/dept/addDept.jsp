<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="addDataForm" action="${ctx }/system/dept/save"
				method="post" >
				<table cellpadding=5 cellspacing=0 width=100% align="center" class="kv-table">
				
					<tr>
						<td class="kv-label">机构名称：</td>
						<td class="kv-content"><input name="deptName" type="text"
							class="easyui-textbox"   data-options="validType:'length[1,100]'"
							style="width: 250px; height: 30px" required="true"></td>
						<td class="kv-label">上级机构：</td>
						<td class="kv-content"><input  type="text"  name="parentId"  value="${parentId}"	class="easyui-combotree"
							data-options="url:'${ctx }/system/dept/loadDeptTree',method:'get'" required="true"
							style="width: 250px; height: 30px" ></td>
					</tr>
				  
					<tr>
						<td class="kv-label">机构代码：</td>
						<td class="kv-content"><input name="deptCode" type="text"
							class="easyui-textbox" data-options="validType:'length[0,50]'"
							style="width: 250px; height: 30px" ></td>
						<td class="kv-label">主要负责人：</td>
						<td class="kv-content"><input name="manager" type="text"
							class="easyui-textbox" data-options="validType:'length[0,50]'"
							style="width: 250px; height: 30px" ></td>
					</tr>
				  
					<tr>
						<td class="kv-label">电话：</td>
						<td class="kv-content"><input name="phone" type="text"
							class="easyui-textbox" data-options="validType:'phoneAndMobile'"
							style="width: 250px; height: 30px" ></td>
						<td class="kv-label">传真：</td>
						<td class="kv-content"><input name="fax" type="text"
							class="easyui-textbox" data-options="validType:'fax'"
							style="width: 250px; height: 30px" ></td>
					</tr>
				  
					
					<tr>
						<td class="kv-label">地址：</td>
						<td class="kv-content"><input name="address" type="text"
							class="easyui-textbox" data-options="validType:'length[0,200]'"
							style="width: 250px; height: 30px" ></td>
						<td class="kv-label">节点图标：</td>
						<td class="kv-content"><input name="icon_name" type="text"
							class="easyui-textbox" data-options="validType:'length[0,100]'"
							style="width: 250px; height: 30px" ></td>
					</tr>
					<tr>
					<td class="kv-label">自动展开：</td>
						<td class="kv-content"><input name="isAutoExpand" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:is_auto_expandStore,textField:1,valueField:0"  
							style="width: 250px; height: 30px" ></td>
						
							<td class="kv-label">排序号：</td>
						<td class="kv-content"><input name="sortNo" type="text"
							class="easyui-numberspinner" value="1" data-options="min:1,max:1000000,required:true"
							style="width: 250px; height: 30px" required="true"></td>
					</tr>
					
					
				   <tr>
						<td class="kv-label">备注：</td>
						<td class="kv-content" colspan="3"><input name="remark" type="text"  class="easyui-textbox"
							data-options="multiline:true,validType:'length[0,500]'"
							style="width: 670px; height: 80px"></td>
					</tr>
					
				

				</table>

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'ok'"href="javascript:void(0)"
			   onclick="submitFormData('addDataForm','','addDataWindow','',submitCallBack)" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'close'" href="javascript:void(0)"
				onclick="closeWindow('addDataWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>