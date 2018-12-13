function format(val, row, index) {
	var value = row.deleteFlag;
	if (value == 1) {
		value = '不启用';
	} else if (value == 0) {
		value = '启用';
	} else {
		value = '';
	}
	return value;
}
// 分页查询
function searchReview() {
	setFirstPage("#dg");
	$('#dg')
			.datagrid(
					{
						url : '/dictionary/findpagelist.ajax',
						queryParams : {
							"valueSetId" : "order_source",
							'valueMeaning' : $('#orderSourceName_search').val(),
							'value' : $('#orderSourceNo_search').val(),
							'deleteFlag' : $('#issend_search').combobox('getText') != '-请选择-' ? $('#issend_search')
											.combobox('getValue') : "",
						},
						loadMsg : '数据装载中......',
						onLoadSuccess : function(data) {
							// 当没有返回数据时提示信息
							if (data.total == 0 && data.rows.length == 0) {
								//$.messager.alert('系统提示', '查询无数据', 'warning');
							}
						}

					});
}
// 用来临时存放添加或修改工单时的地址
var url;
// 添加人员
function newBackList() {
	//从新加载iframe

    $('#addForm').form('reset');
    $('#addDlg').dialog({'title': i18n['message.act.add']}).dialog('open');

	// 设置URL为添加工单地址
	url = '/dictionary/addordersource.ajax';
	QJ_DATAVALUE="";
	$('#deleteFlag').combobox('select', 0);
}
var QJ_DATAVALUE;
//查看
function viewPool() {
	var selRows = $("#dg").datagrid("getSelections");
	if (selRows.length == 1) {
		var row = $('#dg').datagrid('getSelected');
        $('#addDlg').dialog({'title': i18n['message.act.edit']}).dialog('open');
		$('#id').val(row.id);
		$('#addForm').form('load', row);
		QJ_DATAVALUE=serializeObject($('#addForm'));
		url = '/dictionary/updateordersource.ajax';
	} else if(selRows.length == 0){
		$.messager.alert('系统提示', '请选择要操作的数据', 'warning');
	}else if(selRows.length > 1){
		$.messager.alert('系统提示', '请选择一条数据', 'warning');
	}
}
// 添加/修改订单来源
function saveBackList() {
	if (!$('#addForm').form('validate')) {
		return;
	}
	dataValue=serializeObject($('#addForm'));
	if(isObjectValueEqual(QJ_DATAVALUE,dataValue)){
		$('#addDlg').dialog('close');
		return;
	}
	$.ajax({
		type : "post",
		url : url,
		data : dataValue,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.success) {
				$.messager.alert('系统提示', '操作成功!', 'warning');
				$('#addDlg').dialog('close');
				$('#dg').datagrid('reload');
			} else {
				$.messager.alert('系统提示', data.message, 'warning');
			}
		}
	});
	
}


//表单选中后事件
function onClickCell(index, field) {
	return;
}
//将form表单内的所有提交项转成对象
function serializeObject(form) {  
    var o = {};  
    $.each(form.serializeArray(), function(index) {  
        if (o[this['name']]) {  
            o[this['name']] = o[this['name']] + "," + this['value'];  
        } else {  
            o[this['name']] = this['value'];  
        }  
    });  
    return o;  
};  
// 去除空格方法
function fun_trim(obj) {
	if ($.trim($(obj).val()).length <= 0) {
		$(obj).val($.trim($(obj).val()));
	}
}
// 时间转换方法
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
//关闭设置
$('#addDlg').dialog({
    onClose:function(){
//    	$('#orderId').attr('disabled', true);
    	$("#issend").attr("checked",false);
    }
});
//重置
function reset(){
	$("#username_search").val("");
	$("#phone_search").val("");
	$("#email_search").val("");
	$('#type_search').combobox('setValue', "").combobox('setText', '-请选择-');
	$('#issend_search').combobox('setValue', "").combobox('setText', '-请选择-');
}


//将每次点击查询都置到第一页
function setFirstPage(ids){
    var opts = $(ids).datagrid('options');
    var pager = $(ids).datagrid('getPager');
    opts.pageNumber = 1;
    opts.pageSize = opts.pageSize;
    pager.pagination('refresh',{
	    	pageNumber:1,
	    	pageSize:opts.pageSize
	});
}

//判断两个对象是否相等
function isObjectValueEqual(a, b) {
	if(a==null||a==""){
		return false;
	}
    var aProps = Object.getOwnPropertyNames(a);
    var bProps = Object.getOwnPropertyNames(b);
    if (aProps.length != bProps.length) {
        return false;
    }
 
    for (var i = 0; i < aProps.length; i++) {
        var propName = aProps[i];
        if (a[propName] !== b[propName]) {
            return false;
        }
    }
    return true;
}