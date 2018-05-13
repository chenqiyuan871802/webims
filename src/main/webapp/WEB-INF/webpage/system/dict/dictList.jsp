<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
<IMS:codeStore fields="edit_mode,status,dict_type" />
<IMS:codeFormatter fields="edit_mode,status,dict_type" />
<script type="text/javascript">
	//单击列查询
	function clickQueryDict(rowIndex, rowData) {
		$("#dictIndexId").val(rowData.dictIndexId);
		queryDict();

	}
	//查询字典
	function queryDict() {
		$('#dictList').datagrid({
			url : '${ctx}/system/dict/listDict',
			queryParams : $('#queryDictForm').serializeObject()
		});
	}
	
	
  //清空字典回调函数
  function clearDictCallback(data){
		if(data){
			if(data.appcode){
				$('#dictList').datagrid('loadData', { total: 0, rows: [] });//清空数据字典明细表
			}
		}
		
	}
  function editDict(){
	  var row= $('#dictList').datagrid('getSelected');
	   if(row!=null){
	 		 var rowValue= row['editMode'];
	 		 if(rowValue=='0'){
	 			$.messager.alert('警告信息', '你当前选择字典对照数据为只读，只读数据不允许删除和修改', 'warning'); 
	 		 }else{
	 			editGridData('editDictWindow','dictList','dictId','${ctx}/system/dict/editDict','请选择你要编辑字典对照数据'); 
	 		 }
	 		
	 	 }else{
	 		$.messager.alert('警告信息', '请选择你要编辑字典对照数据', 'warning');
	   }
	  
  }
</script>
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:false" style="width: 530px;">
			<div class="easyui-layout" data-options="fit:true">
				<div style="height: 45px; background-color: white;"
					data-options="region:'north',split:false">
					<form id="queryForm" method="post">
						<table class="searchContent">
							<tr>
								<td width="23%" style="text-align: right">字典标识/名称：</td>
								<td width="20%" style="text-align: left"><input type="text"
									name="queryParam" class="easyui-textbox" style="width: 100px;" /></td>

								<td width="15%" style="text-align: right">字典分类：</td>
								<td width="22%" style="text-align: left"><input type="text"
									name="dictType" class="easyui-combobox" editable="false"
									data-options="data:dict_typeStore,textField:1,valueField:0,icons:[{
						iconCls:'icon-remove',
						 handler: function(e){
					                    $(e.data.target).combobox('clear')
				           }
					}]"
									class="easyui-textbox" style="width: 120px;" /></td>
								<td width="15%" rowspan="4" algin="center">&nbsp;<a
									href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-search"
									onclick="doQuery('dictIndexList','queryForm')">查询</a>

								</td>
							</tr>

						</table>
					</form>
				</div>
				<div data-options="region:'center',split:false, border:false">
					<table id="dictIndexList" class="easyui-datagrid"
						style="width: 100%; height: 100%; padding: 0px;"
						data-options="
				     border:false,
	                rownumbers:true,
	                 pagination:true,
	                singleSelect:true,
	                striped:true,
	                toolbar:'#toolbar',
	                 url:'${ctx}/system/dict/listDictIndex',
	                 onClickRow:clickQueryDict,
	                fit:true,
	                pageSize:20">

						<thead>
							<tr>
								<th field="dictIndexId" hidden="true">字典索引编号</th>
								<th field="dictKey" width="40%" align="center">字典标识</th>
								<th field="dictName" width="40%" align="center">字典名称</th>
								<th field="dictType" formatter="dict_typeFormatter" width="20%" align="center">字典分类</th>
							</tr>
						</thead>
					</table>

				</div>
			</div>
		</div>
		<div data-options="region:'center'">
			<div class="easyui-layout" data-options="fit:true">
				<div style="height: 45px; background-color: white;"
					data-options="region:'north',split:false">
					<form id="queryDictForm" method="post">
						<table class="searchContent">
							<input type="hidden" name="dictIndexId" id="dictIndexId" />
							<tr>

								<td width="23%" style="text-align: right">字典对照码：</td>
								<td width="20%" style="text-align: left"><input type="text"
									name="dictCode" class="easyui-textbox" style="width: 100px;" /></td>

								<td width="15%" style="text-align: right">字典对照值：</td>
								<td width="22%" style="text-align: left"><input type="text"
									name="dictValue" class="easyui-textbox" style="width: 100px;" /></td>
								</td>
								<td width="15%" rowspan="4" algin="center">&nbsp;<a
									href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-search" onclick="queryDict()">查询</a>

								</td>
							</tr>

						</table>
					</form>
				</div>
				<div data-options="region:'center',split:false, border:false">
					<table id="dictList" class="easyui-datagrid"
						style="width: 100%; height: 100%; padding: 0px;"
						data-options="
				     border:false,
	                rownumbers:true,
	                pagination:true,
	                singleSelect:false,
	                autoRowHeight:false, 
	                striped:true,
	                toolbar:'#tb',
	                url:'',
	                fit:true,
	                pageSize:20">

						<thead>
							<tr>
								<th field="dictId" hidden=“true”>字典明细编号</th>
								<th data-options="field:'ck',checkbox:true"></th>
								<th field="dictCode" width="20%" align="center">字典对照码</th>
								<th field="dictValue" width="20%" align="center">字典对照值</th>
								<th field="showColor" width="15%" align="center">显示颜色</th>
								<th field="sortNo" width="11%" align="center">排序号</th>
								<th field="status" formatter="statusFormatter" width="14%"
									align="center">当前状态</th>
								<th field="editMode" formatter="edit_modeFormatter" width="14%"
									align="center">编辑模式</th>

							</tr>
						</thead>
					</table>

				</div>
			</div>
		</div>

	</div>
	<div id="toolbar" style="padding: 2px;">

		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="showWindow('addDictIndexWindow','${ctx}/system/dict/addDictIndex');">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true"
			onclick="editGridData('editDictIndexWindow','dictIndexList','dictIndexId','${ctx}/system/dict/editDictIndex','请选择你要编辑的字典数据');">编辑</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
			plain="true" onclick="removeGridData('dictIndexList','dictIndexId','${ctx}/system/dict/removeDictIndex','请选择你要删除字典信息','你确认要删除选择的字典信息吗？','',clearDictCallback);">删除</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="refresh"
			plain="true" onclick="doAjax('${ctx}/system/dict/refreshDict','','你确定要重新刷新字典缓存吗？')">刷新缓存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="close"
			plain="true" onclick="doAjax('${ctx}/system/dict/flushDict','','你确定清空字典缓存吗？')">清空缓存</a>

	</div>
	<div id="tb" style="padding: 2px;">

		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="editGridData('addDictWindow','dictIndexList','dictIndexId','${ctx}/system/dict/addDict','请先在数据字典列表上选择一条字典数据');">新增</a>

		<a href="javascript:void(0);" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editDict()">编辑</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
			plain="true" onclick="batchRemoveGridData('dictList','dictId','${ctx}/system/dict/batchRemoveDict','请选择你要删除字典对照信息','你确认要删除选择的字典对照信息吗？');">删除</a>

	</div>
	<div id="addDictIndexWindow" class="easyui-window" title="新增字典"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 500px; height: 310px; background-color: #FFFFFF">
	</div>
	<div id="editDictIndexWindow" class="easyui-window" title="编辑字典"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 500px; height: 310px; background-color: #FFFFFF">
	</div>
	<div id="addDictWindow" class="easyui-window" title="新增字典对照"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 500px; height: 400px; background-color: #FFFFFF">
	</div>
	<div id="editDictWindow" class="easyui-window" title="编辑字典对照"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 500px; height: 400px; background-color: #FFFFFF">
	</div>

</body>


