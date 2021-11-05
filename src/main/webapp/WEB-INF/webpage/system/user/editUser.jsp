<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/webpage/common/tagLibs.jsp"%>
<head>
<meta charset="UTF-8">
</head>
<body style="margin: 0; padding: 0">
<script type="text/javascript">
//初始化JS
$(function(){
	$('#editDataRange').combotree({
        url: '${ctx }/system/dept/listGrantTree?grantDeptId=${user.deptId}',
        required: true,
        cascadeCheck : false,
        multiple: true,
        method: 'GET',
        onLoadSuccess:function (node) {
        	var dataRange= '${user.dataRange}'
        	var rangeArray=dataRange.split(",");
        	var valueArr = new Array();
        	for(var i=0;i<rangeArray.length;i++){
        		
        		valueArr.push(rangeArray[i]);
        		
        	}
        	$('#editDataRange').combotree('setValue',valueArr)
        }
     
    });
	$('#editDeptId').combotree({
      url: '${ctx }/system/dept/loadDeptTree?whetherGrant=1',
      required: true,
      method: 'GET',
      onSelect:function (node) {
      	
      	$('#editDataRange').combotree("clear");
      	var grantDeptId=node.id;
          $('#editDataRange').combotree({
              url: '${ctx }/system/dept/listGrantTree?grantDeptId='+grantDeptId,
              required: true,
              cascadeCheck : false,
              multiple: true,
              method: 'GET',
              onSelect:function (node) {
                 
              },
              onLoadSuccess:function (node) {
              	$('#editDataRange').combotree('setValue',grantDeptId);
              }
           
          });
          
      }
	
  })

   
})
    

</script>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding: 5px;">
			<form id="editDataForm" action="${ctx }/system/user/update"
				method="post" >
				 <input type="hidden"  name="userId" value="${user.userId}" />
				<table cellpadding=5 cellspacing=0 width=100% align="center" class="kv-table">
				
					<tr>
						<td class="kv-label">用户账号：</td>
						<td class="kv-content"><input  type="text"  name="account" disabled="true" value="${user.account }" class="easyui-textbox" required="true" data-options="validType:'account'" style="width: 250px; height: 30px" ></td>
						<td class="kv-label">用户名：</td>
						<td class="kv-content"><input  type="text"  name="username" value="${user.username}"	class="easyui-textbox" required="true" data-options="validType:'length[1,50]'" style="width: 250px; height: 30px" ></td>
					</tr>
					
					<tr>
						<td class="kv-label">所属机构：</td>
						<td class="kv-content"><input  type="text" id="editDeptId"  name="deptId" value="${user.deptId}"	class="easyui-combotree"
							data-options="url:'${ctx }/system/dept/loadDeptTree',method:'get'" required="true"
							style="width: 250px; height: 30px" ></td>
						<td class="kv-label">性别：</td>
						<td class="kv-content"><input type="text"  name="sex"	 editable="false"  value="${user.sex}"
							class="easyui-combobox"   data-options="data:sexStore,textField:1,valueField:0"style="width: 250px; height: 30px" ></td>
					</tr>
					<tr>
						<td class="kv-label">手机：</td>
						<td class="kv-content"><input type="text"  name="mobile"	value="${user.mobile }" class="easyui-textbox"  data-options="validType:'mobile'" style="width: 250px; height: 30px" ></td>
						<td class="kv-label">邮箱：</td>
						<td class="kv-content"><input  type="text"   name="email" value="${user.email }"	class="easyui-textbox" validType="email" style="width: 250px; height: 30px" ></td>
					</tr>
					<%-- <tr>
						<td class="kv-label">QQ：</td>
						<td class="kv-content"><input type="text"  name="qq"	value="${user.qq}" class="easyui-textbox"  data-options="validType:'QQ'" style="width: 250px; height: 30px" ></td>
						<td class="kv-label">微信：</td>
						<td class="kv-content"><input  type="text"   name="wechat" value="${user.wechat}"	class="easyui-textbox" validType="wbchat" style="width: 250px; height: 30px" ></td>
					</tr> --%>
					<tr>
						<td class="kv-label">数据范围：</td>
						<td colspan="3" class="kv-content"><input type="text"  name="dataRange" id="editDataRange"	required="true" style="width: 650px; height: 30px" ></td>
						
					</tr>
					 <tr>
						<td class="kv-label">证件号：</td>
						<td class="kv-content"><input  type="text"   name="idno" value="${user.idno}"	class="easyui-textbox" validType="idcard" style="width: 250px; height: 30px" ></td>
					  <td class="kv-label">联系地址：</td>
					  <td class="kv-content"><input type="text"  name="address"	value="${user.address}" class="easyui-textbox"  data-options="validType:'length[0,200]'" style="width: 250px; height: 30px" ></td>
					 </tr>
					 <tr>
						<td class="kv-label">用户状态：</td>
						<td class="kv-content"><input  type="text"   name="status"	 value="${user.status}"  editable="false"  required="true"
							class="easyui-combobox"   data-options="data:user_statusStore,textField:1,valueField:0" style="width: 250px; height: 30px"></td>
					  <td class="kv-label"> 锁定次数：</td>
					  <td class="kv-content">
					  <input name="lockNum" type="text"
							class="easyui-numberspinner" value="${user.lockNum}" data-options="min:3,max:100,required:true"
							style="width: 250px; height: 30px" required="true">
					  </td>
					 </tr>
					
					<tr>
						<td class="kv-label">备注：</td>
						<td class="kv-content" colspan="3">
						<input type="text"  name="remark" value="${user.remark }"	class="easyui-textbox"  data-options="validType:'length[0,400]'" style="width: 660px; height: 70px" >
						</td>
						
					</tr>
				  
				

				</table>

			</form>
		</div>
		<div data-options="region:'south',border:false" height="40px"
			style="text-align: center; background: #F4F4F4; padding: 5px 0 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'ok'"href="javascript:void(0)"
			   onclick="submitFormData('editDataForm','dataList','editDataWindow')" style="width: 70px">保存</a> &nbsp;
		    <a class="easyui-linkbutton" data-options="iconCls:'close'" href="javascript:void(0)"
				onclick="closeWindow('editDataWindow')" style="width: 70px">关闭</a>
		</div>
	</div>

</body>
</html>