<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
<IMS:codeStore fields="role_type,status,edit_mode"/>
<IMS:codeFormatter fields="role_type,status,edit_mode"/>
</head>
<script type="text/javascript">
//修改菜单
function editRole(){
   var row= $('#dataList').datagrid('getSelected');
		if(row!=null){
		 var editMode=row.editMode;
		 if(editMode=='1'){
		   var roleId=row.roleId;
		   showWindow('editDataWindow','${ctx}/system/role/edit?id='+roleId)
		 }else{
		    $.messager.alert('警告信息', '你选择的角色数据编辑模式为只读，只读的数据不允许删除和修改', 'warning');
		 }
		
		}else{
			$.messager.alert('警告信息', '请选择你要修改角色信息', 'warning');
		}
}


//删除角色
function removeRole(){
	 var row= $('#dataList').datagrid('getSelected');
 	 if(row!=null){
 	   if(row.editMode=='0'){
 	       $.messager.alert('警告信息', '你选择的角色数据编辑模式为只读，只读的数据不允许删除和修改,请重新选择你要删除角色', 'warning');
 	       return;
 	  }
 	  removeGridData('${ctx}/system/role/remove','dataList','roleId','你选择你要删除的角色信息数据','此数据为基础数据，删除后，与此相关的数据会受到影响，建议只进行修改和增加操作，您确定要删除这些角色信息吗');
 	 }else{
 		 
 		$.messager.alert('警告信息','请选择你要删除的角色', 'warning');
 	 }
}
</script>
<body style="margin: 0; padding: 0" >
<div class="easyui-layout"  data-options="fit:true">
<div  style="height:42px;background-color: white;"  data-options="region:'north',split:false">
        <form id="queryForm" method="post">
        <table class="searchContent">
					<tr>
						<td width="15%" style="text-align:right">
							角色名称：
						</td>
						<td width="40%" style="text-align:left">
							<input type="text" name="roleName" class="easyui-textbox" style="width: 200px;"/>
						</td>
						
					  
						<td width="50%" rowspan="4" algin="center">
							<div>
								&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-search" onclick="doQuery('dataList','queryForm')">查询</a> &nbsp;
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" 
							onclick="$('#queryForm').form('reset')">重置</a> 
							</div>
						</td>
					</tr>
					
				</table>
        </form>
      </div>
    <div id="main" data-options="region:'center',split:false, border:false">	
	<table id="dataList" class="easyui-datagrid" style="width: 100%; height: 100%; padding: 0px;"
		data-options="
				     border:false,
	                rownumbers:true,
	                singleSelect:true,
	                autoRowHeight:false, 
	                pagination:true,
	                toolbar:'#toolbar',
	                striped:true,
	                queryParams : $('#queryForm').serializeObject(),
	                fit:true,
	                url:'${ctx}/system/role/list',
	                pageSize:20">
	               
		<thead>
			<tr>
				<th field="roleId" hidden="true" align="center">角色编号</th>
				<th field="roleName" width="20%" align="center">角色名称</th>
				<th field="roleType"    formatter="role_typeFormatter" width="10%" align="center">角色类型</th>
				<th field="status"   formatter="statusFormatter" width="10%" align="center">当前状态</th>
				<th field="editMode"   formatter="edit_modeFormatter"   width="10%" align="center">编辑模式</th>
				<th field="roleRemark"    width="33%" align="center">备注</th>
				<th field="createTime"    width="15%" align="center">创建时间</th>
				
			</tr>
		</thead>
	</table>
	<div id="toolbar" style="padding: 2px;">
        <shiro:hasPermission name="system:role:add">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
			plain="true"
			onclick="showWindow('addDataWindow','${ctx}/system/role/add');">新增</a> 
		</shiro:hasPermission>
		<shiro:hasPermission name="system:role:edit">
		<a href="javascript:void(0);" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true"
			onclick="editRole();">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="system:role:roleMenu">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="role_grant"
			plain="true" onclick="showGridData('${ctx}/system/role/grantMenu','grantMenuWindow','dataList','roleId','请选择你要菜单授权的角色')">菜单授权</a>
	   </shiro:hasPermission>
	   <shiro:hasPermission name="system:role:roleUser">
	   <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="user_grant"
			plain="true" onclick="showGridData('${ctx}/system/role/grantUser','grantUserWindow','dataList','roleId','请选择你要用户授权的角色')">用户授权</a>
	   </shiro:hasPermission>
	   <shiro:hasPermission name="system:role:remove">
	   <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
			plain="true" onclick="removeRole()">删除</a>
	   </shiro:hasPermission>
	</div>

    </div>
    </div>
    <div id="addDataWindow" class="easyui-window" title="新增角色"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 500px; height:350px; background-color: #FFFFFF"></div>
    <div id="editDataWindow" class="easyui-window" title="编辑角色"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 500px; height:350px; background-color: #FFFFFF"></div>
    <div id="grantMenuWindow" class="easyui-window" title="菜单授权"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 400px; height:480px; background-color: #FFFFFF"></div>
    <div id="grantAdminMenuWindow" class="easyui-window" title="菜单授权"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 400px; height:480px; background-color: #FFFFFF"></div>
    <div id="grantUserWindow" class="easyui-window" title="用户授权"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 1120px; height:480px; background-color: #FFFFFF"></div>
   
   
    
</body>


