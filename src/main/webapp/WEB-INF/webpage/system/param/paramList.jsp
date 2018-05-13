<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
<IMS:codeStore fields="edit_mode,status" />
<IMS:codeFormatter fields="edit_mode,status" />
<script type="text/javascript">
	
</script>
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div style="height:42px; background-color: white;"
			data-options="region:'north',split:false">
			<form id="queryForm" method="post">
				<table class="searchContent">
					<tr>
						<td width="10%" style="text-align: right">参数名称：</td>
						<td width="30%" style="text-align: left"><input type="text"
							name="paramName"  class="easyui-textbox"
							style="width: 200px;" /></td>
						<td width="10%" style="text-align: right">参数键：</td>
						<td width="30%" style="text-align: left"><input type="text"
							name="paramKey" class="easyui-textbox"
							style="width: 200px;" /></td>

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
						<th field="paramValue" formatter="formatCellTooltip" width="20%"
							align="center">参数值</th>
						<th field="paramRemark" formatter="formatCellTooltip" width="20%"
							align="center">备注</th>
						<th field="catalogName" width="10%" align="center">参数分类</th>
						<th field="status" formatter="statusFormatter" width="7%"
							align="center">当前状态</th>
						<th field="editMode" formatter="edit_modeFormatter" width="7%"
							align="center">编辑模式</th>

					</tr>
				</thead>
			</table>
			<div id="toolbar" style="padding: 2px;">

				<a href="#" class="easyui-linkbutton" iconCls="icon-add"
					plain="true" onclick="addParam();">新增</a> <a
					href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true" onclick="modifyParam();">修改</a> <a
					href="#" class="easyui-linkbutton" iconCls="icon-remove"
					plain="true" onclick="deleteParam();">删除</a>

			</div>

		</div>
	</div>

</body>


