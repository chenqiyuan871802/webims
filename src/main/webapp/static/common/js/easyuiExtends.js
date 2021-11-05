

//$.fn.datagrid.defaults.view=myview;

//手动输入没选择时清空
$.extend($.fn.combobox.defaults, {
	onHidePanel: function(defVal) {
	    var field = $(this).combobox('options').valueField;
	    var val = $(this).combobox('getValue');
	    var data = $(this).combobox('getData');
	    var result = true;
	    for (var i = 0; i < data.length; i++) {
	        if (val == data[i][field]) {
	            result = false;
	            break;
	        }
	    }
		if (result) {//如果没有找到对应的数据,则清除掉
			if(commonUtils.isNotBlank(defVal)){
				$(this).combobox('setValue',defVal);
			}else{
				$(this).combobox('clear');
			}
		}
}
});

//手动输入没选择时清空
$.extend($.fn.combogrid.defaults, {
	onHidePanel: function() {
	    var opts = $(this).combogrid('options');
	    var value =$(this).combogrid('getValue'); 
	    var data=$(this).combogrid('grid').datagrid('getData').rows;
	    for(var i=0;i<data.length;i++){
	        if(value==data[i][opts.idField]){
	            return;
	        }
	    }
	    $(this).combogrid('clear');
	    $(this).combogrid('grid').datagrid('options').queryParams={};
	   
	 }
});


