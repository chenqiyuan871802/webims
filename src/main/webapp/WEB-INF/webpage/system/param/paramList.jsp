<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
<IMS:codeStore fields="edit_mode,status,param_type" />
<IMS:codeFormatter fields="edit_mode,status,param_type" />
<script type="text/javascript">
function editParam(){
	  var row= $('#dataList').datagrid('getSelected');
	   if(row!=null){
	 		 var rowValue= row['editMode'];
	 		 if(rowValue=='0'){
	 			$.messager.alert('警告信息', '你当前选择键值参数数据为只读，只读数据不允许删除和修改', 'warning'); 
	 		 }else{
	 			editGridData('editDataWindow','dataList','paramId','${ctx}/system/param/edit','请选择你要编辑键值参数数据'); 
	 		 }
	 		
	 	 }else{
	 		$.messager.alert('警告信息', '请选择你要编辑键值参数数据', 'warning');
	   }
	  
}
</script>
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div style="height: 42px; background-color: white;"
			data-options="region:'north',split:false">
			<form id="queryForm" method="post">
				<table class="searchContent">
					<tr>
						<td width="8%" style="text-align: right">参数名称：</td>
						<td width="15%" style="text-align: left"><input type="text"
							name="paramName" class="easyui-textbox" style="width: 150px;" /></td>
						<td width="8%" style="text-align: right">参数键：</td>
						<td width="15%" style="text-align: left"><input type="text"
							name="paramKey" class="easyui-textbox" style="width: 150px;" /></td>
						<td width="8%" style="text-align: right">参数键：</td>
						<td width="15%" style="text-align: left"><input type="text"
							name="paramType" class="easyui-combobox" editable="false"
							data-options="data:param_typeStore,textField:1,valueField:0,icons:[{
						iconCls:'icon-remove',
						 handler: function(e){
					                    $(e.data.target).combobox('clear')
				           }
					}]"
							class="easyui-textbox" style="width: 150px;" /></td>
						<td width="20%" rowspan="4" algin="center">&nbsp;<a
							href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-search" onclick="doQuery('dataList','queryForm')">查询</a>
							&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-reload" onclick="$('#queryForm').form('reset')">重置</a>
						</td>
					</tr>

				</table>
			</form>
		</div>
		<div id="main"
			data-options="region:'center',split:false, border:false">
			<table id="dataList" class="easyui-datagrid"
				style="width: 100%; height: 100%; padding: 0px;"
				data-options="
				     border:false,
	                rownumbers:true,
	                singleSelect:false,
	                autoRowHeight:false, 
	                pagination:true,
	                striped:true,
	                toolbar:'#toolbar',
	                queryParams : $('#queryForm').serializeObject(),
	                 url:'${ctx}/system/param/list',
	                fit:true,
	                pageSize:20">

				<thead>
					<tr>
						<th field="param_id" hidden=“true”>参数编号</th>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="paramName" formatter="formatCellTooltip" width="15%"
							align="center">参数名称</th>
						<th field="paramKey" formatter="formatCellTooltip" width="15%"
							align="center">参数键</th>
						<th field="paramValue" formatter="formatCellTooltip" width="22%"
							align="center">参数值</th>
						<th field="paramRemark" formatter="formatCellTooltip" width="20%"
							align="center">备注</th>
						<th field="paramType" width="10%" formatter="param_typeFormatter"
							align="center">参数分类</th>
						<th field="status" formatter="statusFormatter" width="7%"
							align="center">当前状态</th>
						<th field="editMode" formatter="edit_modeFormatter" width="7%"
							align="center">编辑模式</th>

					</tr>
				</thead>
			</table>
			<div id="toolbar" style="padding: 2px;">

				<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="showWindow('addDataWindow','${ctx}/system/param/add');">新增</a> 
					
				<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true" onclick="editParam();">编辑</a>
			   <a  href="javascript:void(0);" class="easyui-linkbutton"
				  iconCls="icon-remove" plain="true"
				   onclick="removeGridData('dataList','paramId','${ctx}/system/param/remove','请选择你要删除键值参数信息','你确认要删除选择的键值参数信息吗？');">删除</a>
				<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="refresh" plain="true"
					onclick="doAjax('${ctx}/system/param/refreshParam','','你确定要重新刷新键值参数缓存吗？')">刷新缓存</a>
				<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="close" plain="true"
					onclick="doAjax('${ctx}/system/param/flushParam','','你确定清空键值参数缓存吗？')">清空缓存</a>
			</div>

		</div>
	</div>
   </div>
	<div id="addDataWindow" class="easyui-window" title="新增键值参数"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 500px; height: 420px; background-color: #FFFFFF"></div>
	<div id="editDataWindow" class="easyui-window" title="编辑键值参数"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 500px; height: 420px; background-color: #FFFFFF"></div>
</body>


