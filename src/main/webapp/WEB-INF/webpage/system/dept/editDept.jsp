<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
<script type="text/javascript">
//变换组织机构只能变换当前同级组织机构或者负机构
function selectParentDept(treeNode){
	var id=treeNode.id;
	var deptId=$("#deptId").val();
	var parentIdOld=$("#parentIdOld").val();
	//获取comboxtree 中tree对象
	var  parentTree= $('#parentTree').combotree('tree');
	var parentNode=parentTree.tree('find',deptId);
	var  nodes=parentTree.tree('getChildren',parentNode.target);
	if(deptId==id){
		$('#parentTree').combotree('setValue', parentIdOld);//设置选中该节点
		$.messager.alert('警告信息', '上级机构不能选择当前机构或当前机构下面的机构，请重新选择上级机构', 'warning');
		return ;
	}
	for(var i=0; i<nodes.length; i++){
		var nodeId=nodes[i].id;
		if(id==nodeId){
			$('#parentTree').combotree('setValue',parentIdOld);//设置选中该节点
			$.messager.alert('警告信息', '上级机构不能选择当前机构或当前机构下面的机构，请重新选择上级机构', 'warning');
		
			return ;
		}
	}
	
  
  
}
</script>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="editDataForm" action="${ctx }/system/dept/update"
				method="post" >
				 <input type="hidden"  name="deptId" id="deptId" value="${dept.deptId}" />
				 <input type="hidden"  id="parentIdOld" name="parentIdOld" value="${dept.parentId}" />
				 <input type="hidden"  name="cascadeId" value="${dept.cascadeId}" />
				<table cellpadding=5 cellspacing=0 width=100% align="center" class="kv-table">
					<tr>
						<td class="kv-label">机构名称：</td>
						<td class="kv-content"><input name="deptName" value="${dept.deptName }"  type="text"
							class="easyui-textbox" data-options="validType:'length[1,100]'"
							style="width: 250px; height: 30px" required="true"></td>
						<td class="kv-label">上级机构：</td>
						<td class="kv-content"><input  type="text" id="parentTree" name="parentId"  <c:if test="${dept.deptId=='0'}">disabled="true" value="0" </c:if>   <c:if test="${dept.deptId!='0'}"> value="${dept.parentId }"  </c:if>	
						class="easyui-combotree"
							data-options="url:'${ctx }/system/dept/loadDeptTree',method:'get',onSelect:selectParentDept" required="true"
							style="width: 250px; height: 30px" ></td>
					</tr>
				  
					<tr>
						<td class="kv-label">机构代码：</td>
						<td class="kv-content"><input name="deptCode" type="text" value="${dept.deptCode }"
							class="easyui-textbox" data-options="validType:'length[0,50]'"
							style="width: 250px; height: 30px" ></td>
						<td class="kv-label">主要负责人：</td>
						<td class="kv-content"><input name="manager" type="text" value="${dept.manager }"
							class="easyui-textbox" data-options="validType:'length[0,50]'"
							style="width: 250px; height: 30px" ></td>
					</tr>
				  
					<tr>
						<td class="kv-label">电话：</td>
						<td class="kv-content" ><input name="phone" type="text" value="${dept.phone}"
							class="easyui-textbox" data-options="validType:'phoneAndMobile'"
							style="width: 250px; height: 30px" ></td>
						<td class="kv-label">传真：</td>
						<td class="kv-content"><input name="fax" type="text" value="${dept.fax}"
							class="easyui-textbox" data-options="validType:'fax'"
							style="width: 250px; height: 30px" ></td>
					</tr>
				  
					
					<tr>
						<td class="kv-label">地址：</td>
						<td class="kv-content"><input name="address" type="text" value="${dept.address}"
							class="easyui-textbox" data-options="validType:'length[0,200]'"
							style="width: 250px; height: 30px" ></td>
						<td class="kv-label">节点图标：</td>
						<td class="kv-content"><input name="icon_name" type="text" value="${dept.iconName}"
							class="easyui-textbox" data-options="validType:'length[0,100]'"
							style="width: 250px; height: 30px" ></td>
					</tr>
					<tr>
					<td class="kv-label">自动展开：</td>
						<td class="kv-content"><input name="isAutoExpand" type="text" value="${dept.isAutoExpand }"  editable="false" 
							class="easyui-combobox"   data-options="data:is_auto_expandStore,textField:1,valueField:0"  
							style="width: 250px; height: 30px" ></td>
						
							<td class="kv-label">排序号：</td>
						<td class="kv-content"><input name="sort_no" type="text"
							class="easyui-numberspinner" value="${dept.sortNo }" data-options="min:1,max:1000000,required:true"
							style="width: 250px; height: 30px" required="true"></td>
					</tr>
					
					
				   <tr>
						<td class="kv-label">备注：</td>
						<td class="kv-content" colspan="3"><input name="remark" type="text" value="${dept.remark }"  class="easyui-textbox"
							data-options="multiline:true,validType:'length[0,500]'"
							style="width: 670px; height: 80px"></td>
					</tr>
					
				

				</table>

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'ok'"href="javascript:void(0)"
			   onclick="submitFormData('editDataForm','','editDataWindow','',submitCallBack)" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'close'" href="javascript:void(0)"
				onclick="closeWindow('editDataWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>