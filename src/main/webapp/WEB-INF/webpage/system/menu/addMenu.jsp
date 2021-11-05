<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
<script type="text/javascript">
 function chooseModuleType(item){
	 var moduleType=item[0];
	 var moduleTypeQuery=''
	 if(moduleType=='1'){  //父菜单
		 moduleTypeQuery='0';
	 }else if(moduleType=='2'){ //子菜单
		 moduleTypeQuery='1'
	 }else{  //按钮
		 moduleTypeQuery='1,2'
	 }
	 
	 $('#parentId').combotree({
	        url: '${ctx }/system/menu/loadMenuTree?moduleTypeQuery='+moduleTypeQuery,
	        required: true,
	        method: 'GET',
	        onSelect:function (node) {
	        	var  treeBussType=node.treeBussType
	            if(moduleType=='3'){
	            	if(treeBussType!=='2'){
	            		$.messager.alert('错误信息', '模块类型为按钮，上级菜单只能选择子菜单', 'error');
	            		$('#parentId').combotree('setValue','')
	            	}
	            	
	            }else if(moduleType=='2'){
	            	
	            	if(treeBussType!=='1'){
	            		$.messager.alert('错误信息', '模块类型为子菜单，上级菜单只能选择父菜单', 'error');
	            		$('#parentId').combotree('setValue','')
	            	}
	            }
	        },
	        onLoadSuccess:function (node) {
	        	 if(moduleType=='1'){
	        		 $('#parentId').combotree('setValue','0')
	        	 }
	        	 if(moduleType=='2'){
	        		 $("#url").textbox({
	        			    required:true   
	        			});
	        	 }else{
	        		 $("#url").textbox({
	        			    required:false   
	        			});
	        	 }
	          }
		
	    })
 }
</script>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="addMenuForm" action="${ctx }/system/menu/save"
				method="post" >
				<table cellpadding=5 cellspacing=0 width=100% align="center" class="kv-table">
				    <tr>
						<td class="kv-label">菜单类型：</td>
						<td class="kv-content"><input name="menuType" type="text" value="0" required="true"  editable="false" 
							class="easyui-combobox"   data-options="data:menu_typeStore,textField:1,valueField:0"  
							style="width: 250px; height: 30px" ></td>
					
						<td class="kv-label">模块类型：</td>
						<td class="kv-content"><input name="moduleType" type="text" editable="false"  required="true"
							class="easyui-combobox"   data-options="data:module_typeStore,textField:1,valueField:0,onSelect:chooseModuleType"  
							style="width: 250px; height: 30px" ></td>
					</tr>
					
					<tr>
						<td class="kv-label">上级菜单：</td>
						<td class="kv-content"><input  type="text"  id="parentId" name="parentId"	class="easyui-combotree"
							required="true"
							style="width: 250px; height: 30px" ></td>
						<td class="kv-label">自动展开：</td>
						<td class="kv-content"><input name="isAutoExpand" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:is_auto_expandStore,textField:1,valueField:0"  
							style="width: 250px; height: 30px" ></td>
					</tr>
					 <tr>
						<td class="kv-label">菜单编码：</td>
						<td class="kv-content"><input name="menuCode" type="text"
							class="easyui-textbox" data-options="validType:'keyname'"
							style="width: 250px; height: 30px" required="true"></td>
							<td class="kv-label">菜单名称：</td>
						<td class="kv-content"><input name="menuName" type="text"
							class="easyui-textbox" data-options="validType:'length[1,100]'"
							style="width: 250px; height: 30px" required="true"></td>
					</tr>
				 
					<tr>
					<td class="kv-label">菜单URL：</td>
						<td colspan="3" class="kv-content"><input name="url"  id="url" type="text" 
							class="easyui-textbox" data-options="validType:'length[1,200]'"
							style="width: 650px; height: 30px" ></td>
						
						
					</tr>
					
						<td class="kv-label">节点图标：</td>
						<td class="kv-content"><input name="iconName" type="text"
							class="easyui-textbox" data-options="validType:'length[1,100]'"
							style="width: 250px; height: 30px" ></td>
					<td class="kv-label">排序号：</td>
						<td class="kv-content"><input name="sortNo" type="text"
							class="easyui-numberspinner" value="1" data-options="min:1,max:1000000,required:true"
							style="width: 250px; height: 30px" required="true"></td>
						
					</tr>
					<tr>
						<td class="kv-label">当前状态：</td>
						<td class="kv-content"><input name="status" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:statusStore,textField:1,valueField:0"  
							style="width: 250px; height: 30px" ></td>
					
						<td class="kv-label">编辑模式：</td>
						<td class="kv-content"><input name="editMode" type="text" value="1"  editable="false" 
							class="easyui-combobox"   data-options="data:edit_modeStore,textField:1,valueField:0"  
							style="width: 250px; height: 30px" ></td>
					</tr>
					
					<tr>
						
						<td class="kv-label">备注：</td>
						<td  class="kv-content" colspan="3">
						<input name="remark" type="text"  class="easyui-textbox"
							data-options="multiline:true,validType:'length[0,200]'"
							style="width: 650px; height: 80px">
							
						</td>
						
					</tr>
				 
				

				</table>

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'ok'"href="javascript:void(0)"
			   onclick="submitFormData('addMenuForm','','addMenuWindow','',submitCallBack)" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'close'" href="javascript:void(0)"
				onclick="closeWindow('addMenuWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>