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
var webplus={
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
   },
   /**
    * 处理空置
    */
   removeEmpty:function(obj){
   	
   	if (typeof(obj) == "undefined" || obj == "" || obj == null || obj == "null" || obj.length == 0) {
           return "";
       } 
   	return obj;
   },
   /**
    * 去掉空格字符串
    */
   trim: function (str) {

       return str.replace(/(^\s*)|(\s*$)/g, '');
   },/*
    * 根据名称获取
    */
   getName: function (name) {
       return localStorage.getItem(name);
   },
   /*
    * 设置本地缓存
    */
   setName: function (key, value) {
       localStorage.setItem(key, value);
       return true;
   },
   /**
    *获取token值,用于请求接口
    */
   setToken: function (token) {
	   return localStorage.setItem("token",token);
   },
   /**
    *获取token值,用于请求接口
    */
   getToken: function () {
       return localStorage.getItem("token");
   },
   /**
    *删除token值
    */
   removeToken: function () {
       localStorage.setItem("token", '');
   },
   getUrlToken:function(url){
	   var token=webplus.getToken();
	   if(url.indexOf("?")>-1){
		   url+="&token="+token;
	   }else{
		   url+="?token="+token;
	   }
	   return url;
   }
  
  
 }
$.ajaxSetup({
	timeout:900000,
	data:{
	"token":webplus.getToken()
	},
	error:function() {
				$.messager.alert('错误信息', '操作失败，网络连接超时', 'error');
		}
});
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
	 
	 if(webplus.isNotEmpty(value)){
		 
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
    
    if(webplus.isNotEmpty(resize)){
	    $('#' + windowId).window('resize',resize);
    }
    if(webplus.isNotEmpty(url)){
      
    	$('#'+windowId).window('refresh',url);
    }
    if(webplus.isNotEmpty(title)){
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
function showGridData(url,windowId,gridId,rowKey,warnMsg){
  if(webplus.isEmpty(windowId)){
	   $.messager.alert('错误信息', '缺少Window窗口ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(gridId)){
	   $.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(rowKey)){
	   $.messager.alert('错误信息', '缺少数据主键ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(url)){
	   $.messager.alert('错误信息', '缺少URL地址', 'error');
	   return ;
   }
   if(webplus.isEmpty(warnMsg)){
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
	if(webplus.isEmpty(windowId)){
		$.messager.alert('错误信息', '缺少Window窗口ID', 'error');
		return ;
	}
	if(webplus.isEmpty(gridId)){
		$.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
		return ;
	}
	if(webplus.isEmpty(rowKey)){
		$.messager.alert('错误信息', '缺少数据主键ID', 'error');
		return ;
	}
	if(webplus.isEmpty(url)){
		$.messager.alert('错误信息', '缺少URL地址', 'error');
		return ;
	}
	if(webplus.isEmpty(warnMsg)){
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

	if(webplus.isNotEmpty(rowValues)){
		
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
function editGridData(url,windowId,gridId,rowKey,warnMsg){
   if(webplus.isEmpty(windowId)){
	   $.messager.alert('错误信息', '缺少Window窗口ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(gridId)){
	   $.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(rowKey)){
	   $.messager.alert('错误信息', '缺少数据主键ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(url)){
	   $.messager.alert('错误信息', '缺少URL地址', 'error');
	   return ;
   }
   if(webplus.isEmpty(warnMsg)){
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
function removeGridData(url,gridId,rowKey,warnMsg,confirmMsg,controlType,callback){
 
   if(webplus.isEmpty(gridId)){
	   $.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(rowKey)){
	   $.messager.alert('错误信息', '缺少数据主键ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(url)){
	   $.messager.alert('错误信息', '缺少URL地址', 'error');
	   return ;
   }
   if(webplus.isEmpty(confirmMsg)){
	  confirmMsg="你确认要删除选择的数据吗？";
   }
   if(webplus.isEmpty(warnMsg)){
	  warnMsg="请选择你要删除的数据";
   }
 	 var row= $('#'+gridId).datagrid('getSelected');
 	 if(row!=null){
 		 var rowValue= row[''+rowKey];
 		 var params={}
 		 params.id=rowValue;
 		doAjax(url, params,confirmMsg,controlType,gridId,callback)
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
function batchRemoveGridData(url,gridId,rowKey,warnMsg,confirmMsg,callback){
 
   if(webplus.isEmpty(gridId)){
	   $.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(rowKey)){
	   $.messager.alert('错误信息', '缺少数据主键ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(url)){
	   $.messager.alert('错误信息', '缺少URL地址', 'error');
	   return ;
   }
   if(webplus.isEmpty(confirmMsg)){
	  confirmMsg="你确认要删除选择的数据吗？";
   }
   if(webplus.isEmpty(warnMsg)){
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
    var params={};
    params['ids']=rowValues 
 	if(rowValues!=''){
 		doAjax(url, params,confirmMsg,'',gridId,callback)
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
 if(webplus.isEmpty(gridId)){
	   $.messager.alert('错误信息', '缺少DataGrid表格ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(rowKey)){
	   $.messager.alert('错误信息', '缺少数据主键ID', 'error');
	   return ;
   }
   if(webplus.isEmpty(url)){
	   $.messager.alert('错误信息', '缺少URL地址', 'error');
	   return ;
   }
   
   if(webplus.isEmpty(warnMsg)){
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
    if(webplus.isNotEmpty(confirmMsg)){
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
 	if (webplus.isEmpty(formId)) {
 		$.messager.alert('错误信息', '缺少form表单ID', 'error');
 		return;
 	}
 
 	if (webplus.isEmpty(windowId)) {
 		$.messager.alert('错误信息', '缺少Window窗口ID', 'error');
 		return;
 	}
 	$.messager.progress({
 		title : '信息操作',
 		text : '数据正在保存中，请耐心等待...'
 	});
 	$('#' + formId).form('submit', {
 		onSubmit : function(param) {
 			param.token=webplus.getToken();
 			var result = $(this).form('enableValidation').form('validate');
 			if (!result) {
 				$.messager.progress('close');
 			}
 			return result;
 		},
 		success : function(data) {
 			$.messager.progress('close');
 			var data = eval('(' + data + ')');
 			doAjaxResult(data, controlType, gridId, windowId,callback)
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
  * @param param
  * @param confirmMsg
  */
 function doAjax(url, params, confirmMsg,controlType,gridId,callback) {
 	if (webplus.isEmpty(url)) {
 		$.messager.alert('错误信息', '缺少URL地址', 'error');
 		return;
 	}
 	if (webplus.isNotEmpty(confirmMsg)) {
 		$.messager.confirm('确认', confirmMsg, function(r) {
 			if (r) {
 				
 				ajaxBase(url,params,gridId,'','',function(data){
 					doAjaxResult(data,controlType,gridId,'',callback)
 				})
 			}
 		});
 	} else {
 		ajaxBase(url,params,gridId,'','',function(data){
				doAjaxResult(data,controlType,gridId,'',callback)
			})
 	}

 }
 /**
  *  AJAX基础工具类
  * @param url
  * @param params
  * @param type
  * @param dataType
  * @param callback
  * @returns
  */
 function ajaxBase(url, params,gridId, type, dataType, callback) {
      if (webplus.isEmpty(url)) {
          webplus.alertWarn("请求地址url不能为空");
          return false;
      }
      if (webplus.isEmpty(params)) {
          params = {};
      }
      if (webplus.isEmpty(type)) {
          type = 'post';
      }
      if (webplus.isEmpty(dataType)) {
          dataType = 'json';
      }
      params.token=webplus.getToken();
      $.ajax({
          type: type,
          url: url,
          data: params,
          timeout: 30000,
          beforeSend: function(){
         	 $.messager.progress({
 					title : '信息操作',
 					text : '数据正在处理中，请耐心等待...'
 				});
          },
          dataType: dataType,
          success: function (data) {
              if (typeof (callback) === "function") {
                  callback(data);
              }
          },
          error: function (jqXHR, textStatus, errorThrown) {
         	 $.messager.alert('错误信息', '操作失败，网络连接超时'+textStatus, 'error');
          },
          complete: function () {
         	 $.messager.progress('close');
          }
      })
  }
 /**
  * ajax 回调统一处理
  */
 function doAjaxResult(data, controlType, gridId,windowId, callback){
	    if (data) {
			if (data.appCode == "1") {
				if( typeof(callback) === "function"){
					callback(data);
			    }else{
			    	showMsg('提示', data.appMsg);
					if (webplus.isNotEmpty(gridId)) {
						if (controlType == '1') {

 	 						$('#' + gridId).treegrid({});
 	 					} else {
 	 						$('#' + gridId).datagrid({});
 	 					}
					}
					
			    }
				if(webplus.isNotEmpty(windowId)){
					$('#' + windowId).window('close');
				}
			} else if (data.appCode == "0") {
				$.messager.alert('警告信息', data.appMsg, 'warning');
			} else if (data.appCode == '-2' || data.appCode == '-3') {
				$.messager.alert('错误信息', '访问令牌非法，系统将重置到登陆页面，错误信息：' + data.appMsg, 'error');
                setTimeout(function () {
                    if (self != top) {
                        parent.parent.location.href = cxt+'/logout?token='+webplus.getToken();
                    } else {
                        window.location.href = cxt+'/logout?token='+webplus.getToken();
                    }


                }, 1000)
            } else {
				$.messager.alert('错误信息', data.appMsg, 'error');
			}
			
		} else {
			$.messager.alert('错误信息', '操作失败', 'error');
		}
 }

