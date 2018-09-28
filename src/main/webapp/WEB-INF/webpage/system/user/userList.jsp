<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
<IMS:codeStore fields="user_status,sex"/>
<IMS:codeFormatter fields="user_status,sex"/>
<script type="text/javascript">
   //树的单击查询
  function treeOnClickQuery(treeNode){
     
     $('#queryForm').form('reset');//重置查询框的值
	 $('#cascadeId').val(treeNode.cascadeId);
	 doQuery('dataList','queryForm');
  }
 
 //新增用户
 function addUser(){
  var treeNode= $("#deptTree").tree('getSelected')
  var deptId="0";
   if(treeNode){
	   deptId=treeNode.id
   }
   showWindow('addDataWindow','${ctx}/system/user/add?deptId='+deptId);
 }
</script>
</head>
<body style="margin: 0; padding: 0">
	<div class="easyui-layout"  data-options="fit:true">
		<div data-options="region:'west',split:false" title="组织机构树" style="width: 210px;">
		 <div class="easyui-layout" data-options="fit:true">
				
				<div data-options="region:'center'">
					  <ul id="deptTree" class="easyui-tree" data-options="url:'${ctx }/system/dept/loadTree',method:'get',animate:true,lines:true,onClick:treeOnClickQuery"></ul>
				</div>
			</div>
		
		</div>
		<div data-options="region:'center'" >
			<div class="easyui-layout" data-options="fit:true">
				<div style="height: 75px; background-color: white;"
					data-options="region:'north',split:false">
					<form id="queryForm" method="post">
					<input type="hidden"  name="cascadeId" id="cascadeId" />
						<table class="searchContent">
							<tr>
								<td width="7%" style="text-align: right">账号：</td>
								<td width="13%" style="text-align: left">
								<input type="text" name="account"   class="easyui-textbox" style="width: 110px;  " />
								</td>
								
								<td width="7%" style="text-align: right">姓名：</td>
								<td width="13%" style="text-align: left">
								<input type="text" name="username"   class="easyui-textbox" style="width: 110px; " />
								</td>
								<td width="10%" style="text-align: right">用户状态：</td>
								<td width="13%" style="text-align: left">
								<input type="text" name="status"    class="easyui-combobox" editable="false"  data-options="data:user_statusStore,textField:1,valueField:0" style="width: 90px;  " />
								</td>
								<td width="7%" style="text-align: right">性别：</td>
								<td width="13%" style="text-align: left">
								<input type="text" name="sex" class="easyui-combobox" editable="false"  data-options="data:sexStore,textField:1,valueField:0"   class="easyui-textbox" style="width:90px;  " />
								</td>
								
								
								<td width="20%" rowspan="4" algin="left">
										&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-search" onclick="doQuery('dataList','queryForm')">查询</a> &nbsp;
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload"  onclick="$('#queryForm').form('reset')">重置</a> 
								</td>
							</tr>
							<tr>
								<td width="7%" style="text-align: right">手机：</td>
								<td width="13%" style="text-align: left">
								<input type="text" name="mobile"   class="easyui-textbox" style="width: 110px;  " />
								</td>
								
								<td width="7%" style="text-align: right">邮箱：</td>
								<td width="13%" style="text-align: left">
								<input type="text" name="email"   class="easyui-textbox" style="width: 110px; " />
								</td>
								
								<td width="10%" style="text-align: right">注册日期：</td>
								<td width="13%" style="text-align: left" colspan="3">
								<input type="text"  name="careteTimeBegin" class="easyui-datebox" editable="false" style="width:110px" />
							至：<input type="text" name="careteTimeEnd"  class="easyui-datebox" editable="false" style="width:110px" />
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
	                 url:'${ctx}/system/user/list',
	                fit:true,
	                pageSize:20">

						<thead>
							<tr>
								<th field="userId" hidden=“true”>用户编号</th>
								<th data-options="field:'ck',checkbox:true"></th>
								<th field="account" formatter="formatCellTooltip" width="11%" align="center">账号</th>
								<th field="username"  formatter="formatCellTooltip" width="10%" align="center">姓名</th>
								<th field="deptName"  formatter="formatCellTooltip" width="15%" align="center">所属机构</th>
								<th field="sex"  formatter="sexFormatter"  width="5%" align="center">性别</th>
								<th field="mobile"  formatter="formatCellTooltip" width="12%" align="center">手机</th>
								<th field="email"  formatter="formatCellTooltip" width="15%" align="center">邮箱</th>
								
								<th field="status" formatter="user_statusFormatter"  width="9%" align="center">用户状态</th>
								<th field="createTime"   width="16%" align="center">注册时间</th>
								

							</tr>
						</thead>
					</table>
				</div>
				<div id="toolbar" style="padding: 2px;">

					<a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true"
						onclick="addUser()">新增</a>

					<a href="javascript:void(0);" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true"
						onclick="editGridData('editDataWindow','dataList','userId','${ctx}/system/user/edit','请选择你要编辑的用户信息');">编辑</a> 
				    <a href="#" class="easyui-linkbutton" iconCls="password_reset"
		 	               plain="true" onclick="editGridData('resetPasswordWindow','dataList','userId','${ctx}/system/user/resetPassword','请选择你要重置密码的用户');">密码重置</a>
				   <a href="#" class="easyui-linkbutton" iconCls="user_move"
			               plain="true" onclick="showMoreGridData('moveUserWindow','dataList','userId','${ctx}/system/user/moveUser','请选择你要移动的用户');">移动用户</a>
				    <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="batchRemoveGridData('dataList','userId','${ctx}/system/user/batchRemove','请选择你删除的用户信息','你确认要删除选择的用户信息吗');">删除</a> 
				</div>

			</div>
		</div>
	</div>
	<div id="addDataWindow" class="easyui-window" title="新增用户"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 850px; height: 450px; background-color: #FFFFFF"></div>
	<div id="editDataWindow" class="easyui-window" title="编辑用户"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 850px; height: 410px; background-color: #FFFFFF"></div>
	<div id="resetPasswordWindow" class="easyui-window" title="密码重置"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 520px; height:180px; background-color: #FFFFFF"></div>
	<div id="moveUserWindow" class="easyui-window" title="移动用户"
		data-options="collapsible:false,shadow:false,minimizable:false,maximizable:false,modal:true,closed:true"
		style="width: 400px; height:480px; background-color: #FFFFFF"></div>
</body>


