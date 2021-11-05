<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
<IMS:codeStore fields="edit_mode,status,is_auto_expand,menu_type,module_type" />
<IMS:codeFormatter fields="edit_mode,status,is_auto_expand,menu_type,module_type" />
<script type="text/javascript">
$(function(){
	   $('#addMenu').menu({    
	    onClick:function(item){    
	      
	      if(item.name=='addSubMenu'){
	    	 showWindow('addSubMenuWindow','${ctx}/system/menu/addSubMenu');
	      }else{
	         showWindow('addParentMenuWindow','${ctx}/system/menu/addParentMenu');
	      }
	     
	    }    
	});  
	});
	//树的单击查询
	function treeOnClickQuery(treeNode) {

		$('#queryForm').form('reset');//重置查询框的值
		$('#cascadeId').val(treeNode.cascadeId);
		doQuery('dataList', 'queryForm');
	}
	//成功提交回调
	 function submitCallBack(data){
		 $('#cascadeId').val('');
		 doQuery('dataList','queryForm')
		 $("#menuTree").tree('reload');//刷新树
	 }
	//修改菜单
	  function editMenu(){
	     var row= $('#dataList').datagrid('getSelected');
	 		if(row!=null){
	 		 var editMode=row.editMode;
	 		 if(editMode=='1'){
	 		   var menuId=row.menuId;
	 		   showWindow('editMenuWindow','${ctx}/system/menu/edit?id='+menuId)
	 		 }else{
	 		    $.messager.alert('警告信息', '你选择的菜单数据编辑模式为只读，只读的数据不允许删除和修改', 'warning');
	 		 }
	 		
	 		}else{
	 			$.messager.alert('警告信息', '请选择你要修改菜单信息', 'warning');
	 		}
	  }
	//删除菜单
	  function removeMenu(){

	 	 var row= $('#dataList').datagrid('getSelected');
	 	 if(row!=null){
	 	   if(row.editMode=='0'){
	 	          $.messager.alert('警告信息', '你选择的菜单数据编辑模式为只读，只读的数据不允许删除和修改,请重新选择你要删除菜单数据', 'warning');
	 	          return;
	 	     }
	 	   var menuId=row.menuId
	 	  doAjax('${ctx}/system/menu/remove?id='+menuId,'','你确认要删除选择的菜单数据吗？','','',submitCallBack);
	 	}else{
	 		$.messager.alert('警告信息','请选择你要删除的菜单数据', 'warning');
	 	}
	 	
	 	
	 }
</script>
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:false" title="功能菜单树"
			style="width: 210px;">
			<div class="easyui-layout" data-options="fit:true">

				<div data-options="region:'center'">
					<ul id="menuTree" class="easyui-tree"
						data-options="url:'${ctx }/system/menu/loadMenuTree',method:'get',animate:true,lines:true,onClick:treeOnClickQuery"></ul>
				</div>
			</div>

		</div>
		<div data-options="region:'center'">
			<div class="easyui-layout" data-options="fit:true">
				<div style="height: 40px; background-color: white;"
					data-options="region:'north',split:false">
					<form id="queryForm" method="post">
						<input type="hidden" name="cascadeId" id="cascadeId" />
						<table class="searchContent">
							<tr>
								<td width="15%" style="text-align: right">菜单名称：</td>
								<td width="40%" style="text-align: left"><input type="text"
									name="menuName" id="menuName" class="easyui-textbox"
									style="width: 200px;" /></td>

								<td width="40%" rowspan="4" algin="left">&nbsp;<a
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
	                singleSelect:true,
	                autoRowHeight:false, 
	                pagination:true,
	                striped:true,
	                toolbar:'#toolbar',
	                queryParams : $('#queryForm').serializeObject(),
	                 url:'${ctx}/system/menu/list',
	                fit:true,
	                pageSize:20">

						<thead>
							<tr>
								<th field="menuId" hidden=“true”>菜单编号</th>
								<th field="menuCode" formatter="formatCellTooltip" width="12%"
									align="center">菜单编码</th>
								<th field="menuName" formatter="formatCellTooltip" width="12%"
									align="center">菜单名称</th>
								<th field="url" formatter="formatCellTooltip" width="15%"
									align="center">菜单URL</th>
								<th field="menuType" formatter="menu_typeFormatter" width="7%"
									align="center">菜单类型</th>
								<th field="moduleType_dict" formatter="menu_typeFormatter" width="7%"
									align="center">模块类型</th>
								<th field="isAutoExpand" formatter="is_auto_expandFormatter"
									width="8%" align="center">自动展开</th>
								<th field="sortNo" width="7%" align="center">排序号</th>
								<th field="iconName" width="10%" align="center">节点图标</th>
								<th field="status" formatter="statusFormatter" width="7%"
									align="center">当前状态</th>
								<th field="editMode" formatter="edit_modeFormatter" width="7%"
									align="center">编辑模式</th>


							</tr>
						</thead>
					</table>
				</div>
				<!-- <div id="addMenu" style="width: 150px;">
					<div data-options="name:'addParentMenu',iconCls:'book'">新增父菜单</div>
					<div data-options="name:'addSubMenu',iconCls:'addCatalog'">新增子菜单</div>

				</div> -->
				<div id="toolbar" style="padding: 2px;">
 
					<!-- <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#addMenu',iconCls:'icon-add'">新增</a>  -->
                 <shiro:hasPermission name="system:menu:add">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showWindow('addMenuWindow','${ctx}/system/menu/add');">新增</a> 
				</shiro:hasPermission>
			<shiro:hasPermission name="system:menu:edit">
					<a href="javascript:void(0);" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true" onclick="editMenu();">编辑</a> 
				</shiro:hasPermission>
			   <shiro:hasPermission name="system:menu:remove">
			    <a
						href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
						plain="true" onclick="removeMenu();">删除</a>
				</shiro:hasPermission>
				 <shiro:hasPermission name="system:menu:refreshCache">
				<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="refresh" plain="true"
					onclick="doAjax('${ctx}/system/menu/refreshCache','','你确定要重新刷新缓存吗？')">刷新缓存</a>	
                 </shiro:hasPermission>
				</div>

			</div>
		</div>
	</div>
	
	<div id="addMenuWindow" class="easyui-window" title="新增菜单"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 850px; height: 450px; background-color: #FFFFFF"></div>
	<div id="editMenuWindow" class="easyui-window" title="编辑菜单"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 850px; height: 450px; background-color: #FFFFFF"></div>

</body>


