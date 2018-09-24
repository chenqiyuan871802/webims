
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}
/**公用函数**/
var IMSUtils={
	//判断参数是为空
	isEmpty:function (v){  
    switch (typeof v){  
        case 'undefined' : return true;  
        case 'string'    : if(trim(v).length == 0) return true; break;  
        case 'boolean'   : if(!v) return true; break;  
        case 'number'    : if(0 === v) return true; break;  
        case 'object'    :   
            if(null === v) return true;  
            if(undefined !== v.length && v.length==0) return true;  
            for(var k in v){return false;} return true;  
            break;  
    }  
    return false;  
   },
   //判断参数不为空
	isNotEmpty:function (v){  
    switch (typeof v){  
        case 'undefined' : return false;  
        case 'string'    : if(trim(v).length == 0) return false; break;  
        case 'boolean'   : if(!v) return false; break;  
        case 'number'    : if(0 === v) return false; break;  
        case 'object'    :   
            if(null === v) return false;  
            if(undefined !== v.length && v.length==0) return false;  
            for(var k in v){return true;} return false;  
            break;  
    }  
    return true;  
   }
  
 }
 /**去除字符串空格**/
 function trim(str){
 
     return str.replace(/(^\s*)|(\s*$)/g, ''); 
 }
 /**获取DataGrid选择的值，根据所提供的键获取**/
 function getRowValues(rows,rowKey){
	 var rowValues = "";
	 	for (var i = 0; i < rows.length; i++) {
	 	if(i==0){
	 		rowValues=rows[i][''+rowKey];
	 	}else{
	 		rowValues+= ","+rows[i][''+rowKey] ;
	 		}
	 	};
	 return rowValues;
	  
 }

/**信息弹出框******/
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}
/**
  * 增加列显示title
  * @param value
  * @returns {String}
  */
function formatCellTooltip(value){  
	 
	 if(IMSUtils.isNotEmpty(value)){
		 
		  return "<span title='" + value + "'>" + value + "</span>";  
	 }else{
		 return '';
	 }
}
/**
 * 打开一个窗口
 *windowId 窗口ID
 *url 地址
 *resize 窗口变化发生触发函数
 *title 窗口标题
 *
**/
function showWindow(windowId,url,resize,title) {
	$('#'+windowId).window('open');
	var relativeTop=$(document).scrollTop()+10;
    $('#'+windowId).panel('move',{
        top:relativeTop
    });
    if(IMSUtils.isNotEmpty(resize)){
	    $('#' + windowId).window('resize',resize);
    }
    if(IMSUtils.isNotEmpty(url)){
      
    	$('#'+windowId).window('refresh',url);
    }
    if(IMSUtils.isNotEmpty(title)){
    	$('#' + windowId).window('setTitle',title);
    }
}
/**关闭窗口**/
function closeWindow(windowId) {
	$('#'+windowId).window('close');
}

/**展示提示消息**/
function showMsg(title, msg,newConf) {
	var oldConf={
		title : title,
		msg : "<span style='width:100%;text-align:center;'>" + msg + "</span>",
		icon : 'info',
		timeout : 1000,
		showType : 'slide',
		style : {
			right : '',
			top : document.body.scrollTop + document.documentElement.scrollTop,
			bottom : ''
		}
	};
	var config=$.extend(oldConf,newConf);
	$.messager.show(config);
}
/**
*查看表格数据函数
*windowId Window窗口ID
*grid DataGrid表格ID
*rowKey 行主键ID
*url url地址
*warnMsg 警告信息
*/
function showGridData(windowId,gridId,rowKey,url,warnMsg){
  if(IMSUtils.isEmpty(windowId)){
	   $.messager.alert('错误信息', '缺少Window窗口ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(gridId)){
	   $.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(rowKey)){
	   $.messager.alert('错误信息', '缺少数据主键ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(url)){
	   $.messager.alert('错误信息', '缺少URL地址', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(warnMsg)){
	  warnMsg="请选择你要查看数据";
   }
   var row= $('#'+gridId).datagrid('getSelected');
    if(row!=null){
 		 var rowValue= row[''+rowKey];
 		 if(url.indexOf("?")!=-1){
 		   url+= "&id="+ rowValue;
 		 }else{
		   url+= "?id="+ rowValue;
 		 }
 		 showWindow(windowId,url);
 	 }else{
 			$.messager.alert('警告信息', warnMsg, 'warning');
   }
  
}
/**
*查看多项表格数据函数
*windowId Window窗口ID
*grid DataGrid表格ID
*rowKey 行主键ID
*url url地址
*warnMsg 警告信息
*/
function showMoreGridData(windowId,gridId,rowKey,url,warnMsg){
	if(IMSUtils.isEmpty(windowId)){
		$.messager.alert('错误信息', '缺少Window窗口ID', 'error');
		return ;
	}
	if(IMSUtils.isEmpty(gridId)){
		$.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
		return ;
	}
	if(IMSUtils.isEmpty(rowKey)){
		$.messager.alert('错误信息', '缺少数据主键ID', 'error');
		return ;
	}
	if(IMSUtils.isEmpty(url)){
		$.messager.alert('错误信息', '缺少URL地址', 'error');
		return ;
	}
	if(IMSUtils.isEmpty(warnMsg)){
		warnMsg="请选择你要处理数据";
	}
	var rows = $('#'+gridId).datagrid('getChecked');
 	var rowValues = "";
 	for (var i = 0; i < rows.length; i++) {
 	    
 			if(i==0){
 				rowValues=rows[i][''+rowKey];
 			}else{
 				rowValues+= ","+rows[i][''+rowKey] ;
 			}
 	};

	if(IMSUtils.isNotEmpty(rowValues)){
		
		if(url.indexOf("?")!=-1){
			url+= "&ids="+ rowValues;
		}else{
			url+= "?ids="+ rowValues;
		}
		showWindow(windowId,url);
	}else{
		$.messager.alert('警告信息', warnMsg, 'warning');
	}
	
}
/**
*修改表格数据函数
*windowId Window窗口ID
*grid DataGrid表格ID
*rowKey 行主键ID
*url url地址
*warnMsg 警告信息
*/
function editGridData(windowId,gridId,rowKey,url,warnMsg){
   if(IMSUtils.isEmpty(windowId)){
	   $.messager.alert('错误信息', '缺少Window窗口ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(gridId)){
	   $.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(rowKey)){
	   $.messager.alert('错误信息', '缺少数据主键ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(url)){
	   $.messager.alert('错误信息', '缺少URL地址', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(warnMsg)){
	  warnMsg="请选择你要修改的数据";
   }
   var row= $('#'+gridId).datagrid('getSelected');
    if(row!=null){
 		 var rowValue= row[''+rowKey];
 		 if(url.indexOf("?")!=-1){
 		   url+= "&id="+ rowValue;
 		 }else{
		   url+= "?id="+ rowValue;
 		 }
 		 showWindow(windowId,url);
 	 }else{
 			$.messager.alert('警告信息', warnMsg, 'warning');
   }

}
/**
*删除表格数据函数 单条数据
*grid DataGrid表格ID
*rowKey 行主键ID
*url url地址
*confirmMsg确认信息
*warnMsg 警告信息
*controlType 1代表树表格其他为数据表格
*/
function removeGridData(gridId,rowKey,url,warnMsg,confirmMsg,controlType,callback){
 
   if(IMSUtils.isEmpty(gridId)){
	   $.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(rowKey)){
	   $.messager.alert('错误信息', '缺少数据主键ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(url)){
	   $.messager.alert('错误信息', '缺少URL地址', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(confirmMsg)){
	  confirmMsg="你确认要删除选择的数据吗？";
   }
   if(IMSUtils.isEmpty(warnMsg)){
	  warnMsg="请选择你要删除的数据";
   }
 	 var row= $('#'+gridId).datagrid('getSelected');
 	 if(row!=null){
 		 var rowValue= row[''+rowKey];
 		 if(url.indexOf("?")!=-1){
 		   url+= "&id="+ rowValue;
 		 }else{
		   url+= "?id="+ rowValue;
 		 }
 		 	$.messager.confirm('确认', confirmMsg,
 				function(r) {
 			    if(r){
 					$.ajax({
 						type : 'post',
 						url  :url,
 						dataType : 'json',
 						success : function(data) {
 							if (data) {
 								if (data.appcode == "1") {
 									showMsg('提示', data.appmsg);
 									if(controlType=='1'){
						             $('#' + gridId).treegrid({});
						            }else{
						              $('#' + gridId).datagrid({});
						            }
	
 								} else if(data.appcode=="0"){
					                 $.messager.alert('警告信息', data.appmsg, 'warning');
					             }else {
 									$.messager.alert('错误信息',data.appmsg, 'error');
 								}
 							
 								if(typeof(callback) === "function"){
 							          callback(data);
 							    }
 							} else {
 								$.messager.alert('错误信息', '操作失败',
 										'error');
 							}
 						},
 						error : function() {
 							$.messager.alert('错误信息', '操作失败，网络连接超时',
 									'error');
 						}
 					})
 			    }
 				});
 	}else{
 		$.messager.alert('警告信息',warnMsg, 'warning');
 	}
 	
 	
 }
 /**
*批量删除表格数据函数
*grid DataGrid表格ID
*rowKey 行主键ID
*url url地址
*confirmMsg确认信息
*warnMsg 警告信息
*
*/
function batchRemoveGridData(gridId,rowKey,url,warnMsg,confirmMsg,callback){
 
   if(IMSUtils.isEmpty(gridId)){
	   $.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(rowKey)){
	   $.messager.alert('错误信息', '缺少数据主键ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(url)){
	   $.messager.alert('错误信息', '缺少URL地址', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(confirmMsg)){
	  confirmMsg="你确认要删除选择的数据吗？";
   }
   if(IMSUtils.isEmpty(warnMsg)){
	  warnMsg="请选择你要删除的数据";
   }
 	var rows = $('#'+gridId).datagrid('getChecked');
 	var rowValues = "";
 	for (var i = 0; i < rows.length; i++) {
 	    
 			if(i==0){
 				rowValues=rows[i][''+rowKey];
 			}else{
 				rowValues+= ","+rows[i][''+rowKey] ;
 			}
 	};
    var paramValue={};
    paramValue['ids']=rowValues 
 	if(rowValues!=''){
 		$.messager.confirm('确认', confirmMsg,
 				function(r) {
 			    if(r){
 					$.ajax({
 						type : 'post',
 						url  :url,
 						data :paramValue,
 						dataType : 'json',
 						success : function(data) {
 							if (data) {
 								if (data.appcode == "1") {
 									showMsg('提示', data.appmsg);
 									$('#'+gridId).datagrid({});
 								} else if(data.appcode=="0"){
					                 $.messager.alert('警告信息', data.appmsg, 'warning');
					             }else {
 									$.messager.alert('错误信息',data.appmsg, 'error');
 								}
 								if(typeof(callback) === "function"){
  							       
							          callback(data);
							    }
 							} else {
 								$.messager.alert('错误信息', '操作失败',
 										'error');
 							}
 						},
 						error : function() {
 							$.messager.alert('错误信息', '操作失败，网络连接超时',
 									'error');
 						}
 					})
 			    }
 				});
 	}else{
 		$.messager.alert('警告信息',warnMsg, 'warning');
 	}
 	
 	
 }
 /**
*操作表格数据函数 只作用单条
*grid DataGrid表格ID
*rowKey 行主键ID
*url url地址
*warnMsg 警告信息
*confirmMsg确认信息
*/
 function operateGridData(gridId,rowKey,url,warnMsg,confirmMsg,callback){
 if(IMSUtils.isEmpty(gridId)){
	   $.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(rowKey)){
	   $.messager.alert('错误信息', '缺少数据主键ID', 'error');
	   return ;
   }
   if(IMSUtils.isEmpty(url)){
	   $.messager.alert('错误信息', '缺少URL地址', 'error');
	   return ;
   }
   
   if(IMSUtils.isEmpty(warnMsg)){
	  warnMsg="请选择你要处理的数据";
   }
    var row= $('#'+gridId).datagrid('getSelected');
    if(row!=null){
 		 var rowValue= row[''+rowKey];
 		 if(url.indexOf("?")!=-1){
 		   url+= "&id="+ rowValue;
 		 }else{
		   url+= "?id="+ rowValue;
 		 }
    if(IMSUtils.isNotEmpty(confirmMsg)){
	 $.messager.confirm('确认', confirmMsg,
 				function(r) {
 			    if(r){
 					$.ajax({
 						type : 'post',
 						url  :url,
 						dataType : 'json',
 						success : function(data) {
 							if (data) {
 								if (data.appcode == "1") {
 									showMsg('提示', data.appmsg);
 									$('#'+gridId).datagrid({});
 								}else if(data.appcode=="0"){
					                    $.messager.alert('警告信息', data.appmsg, 'warning');
					              }else {
 									$.messager.alert('错误信息',data.appmsg, 'error');
 								}
 								if(typeof(callback) === "function"){
  							       
							          callback(data);
							    }
 							} else {
 								$.messager.alert('错误信息', '操作失败',
 										'error');
 							}
 						},
 						error : function() {
 							$.messager.alert('错误信息', '操作失败，网络连接超时',
 									'error');
 						}
 					})
 			    }
 				});
    }else{
      $.ajax({
 						type : 'post',
 						url  :url,
 						dataType : 'json',
 						success : function(data) {
 							if (data) {
 								if (data.appcode == "1") {
 									showMsg('提示', data.appmsg);
 									$('#'+gridId).datagrid({});
 								} else if(data.appcode=="0"){
					               $.messager.alert('警告信息', data.appmsg, 'warning');
					          }else {
 									$.messager.alert('错误信息',data.appmsg, 'error');
 								}
 								if( typeof(callback) === "function"){
  							       
							          callback(data);
							    }
 							} else {
 								$.messager.alert('错误信息', '操作失败',
 										'error');
 							}
 						},
 						error : function() {
 							$.messager.alert('错误信息', '操作失败，网络连接超时',
 									'error');
 						}
 					})
    }
 		 	
 	}else{
 		$.messager.alert('警告信息',warnMsg, 'warning');
 	}
 	
 
 }

 /**
 *DataGrid列表查询数据
 *gridId DataGrid表格ID
 *queryFromId 查询表单ID
 */
 function doQuery(gridId,queryFromId) {
		$('#'+gridId).datagrid('options').pageNumber=1;//设置页码初始值为1
	    $('#'+gridId).datagrid({
	        queryParams : $('#'+queryFromId).serializeObject()
	    });
 }
 /**
  * 提交表单数据并刷新表格 formId 表单ID gridId 如果controllerType=1 树网格ID windowId 窗口ID
  * controllerType 等于1 树网格,其他
  * 
  */
 function submitFormData(formId, gridId, windowId, controlType,callback) {
 	if (IMSUtils.isEmpty(formId)) {
 		$.messager.alert('错误信息', '缺少form表单ID', 'error');
 		return;
 	}
 
 	if (IMSUtils.isEmpty(windowId)) {
 		$.messager.alert('错误信息', '缺少Window窗口ID', 'error');
 		return;
 	}
 	$.messager.progress({
 		title : '信息操作',
 		text : '数据正在保存中，请耐心等待...'
 	});
 	$('#' + formId).form('submit', {
 		onSubmit : function(param) {
 			var result = $(this).form('enableValidation').form('validate');
 			if (!result) {
 				$.messager.progress('close');
 			}
 			return result;
 		},
 		success : function(data) {
 			$.messager.progress('close');
 			var data = eval('(' + data + ')');
 			if (data) {
 				if (data.appcode == "1") {
 					showMsg('提示', data.appmsg);
 					if (IMSUtils.isNotEmpty(gridId)) {
 						if (controlType == '1') {

 	 						$('#' + gridId).treegrid({});
 	 					} else {
 	 						$('#' + gridId).datagrid({});
 	 					}
 					}
 					
 					$('#' + windowId).window('close');

 				} else if (data.appcode == "0") {
 					$.messager.alert('警告信息', data.appmsg, 'warning');
 				} else {
 					$.messager.alert('错误信息', data.appmsg, 'error');
 				}
 				if( typeof(callback) === "function"){
				       
			          callback(data);
			    }
 			} else {
 				$.messager.alert('错误信息', '操作失败', 'error');
 			}
 		},
 		onLoadeError : function() {
 			$.messager.progress('close');
 			$.messager.alert('错误信息', '操作失败', 'error');
 		}
 	});
 	

 }
 
 /**
  * ajax接口
  * @param url
  * @param paramData
  * @param confirmMsg
  */
 function doAjax(url, paramData, confirmMsg,gridId,callback) {
 	if (IMSUtils.isEmpty(url)) {
 		$.messager.alert('错误信息', '缺少URL地址', 'error');
 		return;
 	}
 	if (IMSUtils.isNotEmpty(confirmMsg)) {
 		$.messager.confirm('确认', confirmMsg, function(r) {
 			if (r) {
 				$.messager.progress({
 					title : '信息操作',
 					text : '数据正在保存中，请耐心等待...'
 				});
 				$.ajax({
 					type : 'post',
 					url : url,
 					data : paramData,
 					dataType : 'json',
 					success : function(data) {
 						$.messager.progress('close');
 						if (data) {
 							if (data.appcode == "1") {
 								showMsg('提示', data.appmsg);
 								if (IMSUtils.isNotEmpty(gridId)) {

 									$('#' + gridId).datagrid({});
 								}

 							} else if (data.appcode == "0") {
 								$.messager
 										.alert('警告信息', data.appmsg, 'warning');
 							} else {
 								$.messager.alert('错误信息', data.appmsg, 'error');
 							}
 							if( typeof(callback) === "function"){
 						       
 						          callback(data);
 						    }
 						} else {
 							$.messager.alert('错误信息', '操作失败', 'error');
 						}
 					},
 					error : function() {
 						$.messager.progress('close');
 						$.messager.alert('错误信息', '操作失败，网络连接超时', 'error');
 					}
 				})
 			}
 		});
 	} else {
 		$.messager.progress({
 			title : '信息操作',
 			text : '数据正在保存中，请耐心等待...'
 		});
 		$.ajax({
 			type : 'post',
 			url : url,
 			data : paramData,
 			dataType : 'json',
 			success : function(data) {
 				$.messager.progress('close');
 				if (data) {
 					if (data.appcode == "1") {
 						showMsg('提示', data.appmsg);
 						if (IMSUtils.isNotEmpty(gridId)) {

 							$('#' + gridId).datagrid({});
 						}
 					} else if (data.appcode == "0") {
 						$.messager.alert('警告信息', data.appmsg, 'warning');
 					} else {
 						$.messager.alert('错误信息', data.appmsg, 'error');
 					}
 					if( typeof(callback) === "function"){
 				       
 				          callback(data);
 				    }
 				} else {
 					$.messager.alert('错误信息', '操作失败', 'error');
 				}
 			},
 			error : function() {
 				$.messager.progress('close');
 				$.messager.alert('错误信息', '操作失败，网络连接超时', 'error');
 			}
 		})
 	}

 }
