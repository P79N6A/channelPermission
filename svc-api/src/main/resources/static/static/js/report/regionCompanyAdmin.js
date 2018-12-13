//获取工贸
$(function() {
	
	$.ajax({
		type : "get",
		url : "/dictionary/getdictionary.ajax",
		data : {
			"value_set_id" : "company"
		},
		dataType : "json",
		success : function(data) {
			//复制对象
			tmp = data.slice(0);
			$('#companyId').combobox('loadData', tmp);
			$('#companyId_upd').combobox('loadData', tmp);
			a = [];
			a.value = "";
			a.valueMeaning = "-请选择-";
			data.unshift(a);
			b=[];
			b.value = "0";
			b.valueMeaning = "其他";
			data.push(b);
			company_hidedata=data;
			$('#companyId_search').combobox('loadData', data);
		}
	});
	
});

//界面工贸
$('#companyId_search').combobox(
		{
			valueField : 'value',
			textField : 'valueMeaning',
			onLoadSuccess : function(data) {
				$('#companyId_search').combobox('setValue', "").combobox(
						'setText', '-请选择-');
			},
			onSelect : function(record) {
				
			}
		});
//添加窗口工贸
$('#companyId').combobox(
		{
			valueField : 'value',
			textField : 'valueMeaning',
			onLoadSuccess : function(data) {
			},
			onSelect : function(record) {
			}
		});
//修改窗口工贸
$('#companyId_upd').combobox(
		{
			valueField : 'value',
			textField : 'valueMeaning',
			onLoadSuccess : function(data) {
			},
			onSelect : function(record) {
			}
		});
//获取省
$(function() {
	
	$.ajax({
		type : "get",
		url : "/dictionary/getdictionary.ajax",
		data : {
			"value_set_id" : "province"
		},
		dataType : "json",
		success : function(data) {
			//复制对象
			tmp = data.slice(0);
			$('#provinceId').combobox('loadData', tmp);
			a = [];
			a.value = "";
			a.valueMeaning = "-请选择-";
			data.unshift(a);
			company_hidedata=data;
			$('#provinceId_search').combobox('loadData', data);
		}
	});
	
});

//界面省
$('#provinceId_search').combobox(
		{
			valueField : 'value',
			textField : 'valueMeaning',
			onLoadSuccess : function(data) {
				$('#provinceId_search').combobox('setValue', "").combobox(
						'setText', '-请选择-');
			},
            onChange : function(record) {

				if ($('#provinceId_search').combobox('getText') == '-请选择-') {
					$('#cityId_search').combobox('setValue', '');
					$('#cityId_search').combobox('setText', '');
					$('#cityId_search').combobox('loadData', {});
					$('#regionId_search').combobox('setValue', '');
					$('#regionId_search').combobox('setText', '');
					$('#regionId_search').combobox('loadData', {});
					return;
				}

				var n = $('#provinceId_search').combobox('getValue');
				$.ajax({
					type : "GET",
					url : "/dictionary/gettwomenu.ajax",
					data : {
						"value_set_id":"city",
						"parent_value" : n
					},
					dataType : "json",
					success : function(data) {
						a = [];
						a.value = "";
						a.valueMeaning = "-请选择-";
						data.unshift(a);
						$('#cityId_search').combobox('loadData', data);
						
					}
				});
			}
		});

//添加修改省
$('#provinceId').combobox(
		{
			valueField : 'value',
			textField : 'valueMeaning',
			onLoadSuccess : function(data) {
			},
            onSelect : function(record) {
                $('#cityId').combobox('setValue', '');
                $('#cityId').combobox('setText', '');
                $('#cityId').combobox('loadData', {});
//				$('#regionId').combobox('setValue', '');
//				$('#regionId').combobox('setText', '');
//				$('#regionId').combobox('loadData', {});
                var n = record.value;
				$.ajax({
					type : "GET",
					url : "/dictionary/gettwomenu.ajax",
					data : {
						"value_set_id":"city",
						"parent_value" : n
					},
					dataType : "json",
					success : function(data) {
//						a = [];
//						a.value = "";
//						a.valueMeaning = "-请选择-";
//						data.unshift(a);
						$('#cityId').combobox('loadData', data);
						
					}
				});
			}
		});


//界面市
$('#cityId_search').combobox(
		{
			valueField : 'value',
			textField : 'valueMeaning',
			onLoadSuccess : function(data) {
				if ($('#provinceId_search').combobox('getText') != '-请选择-') {
					$('#cityId_search').combobox('setValue', "").combobox(
							'setText', '-请选择-');
				}
				$('#regionId_search').combobox('setValue', '');
				$('#regionId_search').combobox('setText', '');
				$('#regionId_search').combobox('loadData', {});
			},
            onChange : function(record) {
				if ($('#cityId_search').combobox('getText') == '-请选择-'||$('#cityId_search').combobox('getText') == '') {
					$('#regionId_search').combobox('setValue', '');
					$('#regionId_search').combobox('setText', '');
					$('#regionId_search').combobox('loadData', {});
					return;
				}
                var n = $('#cityId_search').combobox('getValue');
				$.ajax({
					type : "GET",
					url : "/dictionary/gettwomenu.ajax",
					data : {
						"value_set_id":"region",
						"parent_value" : n
					},
					dataType : "json",
					success : function(data) {
						a = [];
						a.value = "";
						a.valueMeaning = "-请选择-";
						data.unshift(a);
						$('#regionId_search').combobox('loadData', data);
						
					}
				});
			}
		});


//添加修改市
$('#cityId').combobox(
		{
			valueField : 'value',
			textField : 'valueMeaning',
			onLoadSuccess : function(data) {
			},
			onSelect : function(record) {
//				$('#regionId').combobox('setValue', '');
//				$('#regionId').combobox('setText', '');
//				$('#regionId').combobox('loadData', {});
//				var n = record.value;
//				$.ajax({
//					type : "GET",
//					url : "/dictionary/gettwomenu.ajax",
//					data : {
//						"value_set_id":"region",
//						"parent_value" : n
//					},
//					dataType : "json",
//					success : function(data) {
////						a = [];
////						a.value = "";
////						a.valueMeaning = "-请选择-";
////						data.unshift(a);
//						$('#regionId').combobox('loadData', data);
//					}
//				});
			}
		});

//界面区县
$('#regionId_search').combobox(
		{
			valueField : 'value',
			textField : 'valueMeaning',
			onLoadSuccess : function(data) {
//				$('#regionId_search').combobox('setValue', "").combobox(
//						'setText', '-请选择-');
			},
			onSelect : function(record) {
				
			}
		});

// 分页查询
function searchReview() {
	setFirstPage("#dg");
	$('#dg')
			.datagrid(
					{
						url : '/dictionary/findpageregioncompanylist.ajax',
						queryParams : {
							"provinceId" : $('#provinceId_search').combobox('getText') != '-请选择-' ? $('#provinceId_search')
									.combobox('getValue') : "",
							'cityId' : $('#cityId_search').combobox('getText') != '-请选择-' ? $('#cityId_search')
									.combobox('getValue') : "",
							'companyId' : $('#companyId_search').combobox('getText') != '-请选择-' ? $('#companyId_search')
									.combobox('getValue') : "",
							'regionId' : $('#regionId_search').combobox('getText') != '-请选择-' ? $('#regionId_search')
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
    $('#addForm').form('reset');
	$('#addDlg').dialog({'title': i18n['message.act.add']}).dialog('open');

	QJ_DATAVALUE="";
}
var QJ_DATAVALUE;
//查看
function look() {

	var selRows = $("#dg").datagrid("getSelections");
	console.log(selRows);
	if (selRows.length == 1) {
		var row = $('#dg').datagrid('getSelected');
		$('#viewDlg').dialog({'title': i18n['message.act.edit']}).dialog('open');
//		$('#fm').form('load', row);
		$('#provinceId_upd').combobox('setValue', row.provinceId).combobox('setText', row.provinceName);
		$('#cityId_upd').combobox('setValue', row.cityId).combobox('setText', row.cityName);
		$('#regionId_upd').combobox('setValue', row.regionId).combobox('setText', row.regionName);
		$('#companyId_upd').combobox('setValue', row.companyId).combobox('setText', row.companyName);
		$("#oldCompanyId").val(row.companyId);//旧工贸信息
		$("#oldCompanyName").val(row.companyName);//旧工贸名称信息

		QJ_DATAVALUE=serializeObject($('#fm'));
		QJ_DATAVALUE.newCompanyId=QJ_DATAVALUE.companyId_upd;
		QJ_DATAVALUE.regionId=QJ_DATAVALUE.regionId_upd;
		$("#companyId_upd + .combo").children("input").addClass("white");
	} else if(selRows.length == 0){
		$.messager.alert('系统提示', '请选择要操作的数据', 'warning');
	}else if(selRows.length > 1){
		$.messager.alert('系统提示', '请选择一条数据', 'warning');
	}
}
// 修改窗口保存
function saveBackList() {
	if (!$('#viewForm').form('validate')) {
		return;
	}
	dataValue=serializeObject($('#viewForm'));
	dataValue.newCompanyId=dataValue.companyId_upd;
	dataValue.regionId=dataValue.regionId_upd;
	dataValue.newCompanyName=$("#companyId_upd").combobox("getText");
	dataValue.regionName=$("#regionId_upd").combobox("getText");
	dataValue.oldCompanyName=$("#oldCompanyName").val();
	if(isObjectValueEqual(QJ_DATAVALUE,dataValue)){
		$('#viewDlg').dialog('close');
		return;
	}
	$.ajax({
		type : "post",
		url : '/dictionary/addcompanyofregion.ajax',
		data : dataValue,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.success) {
				$.messager.alert('系统提示', '操作成功!', 'warning');
				$('#viewDlg').dialog('close');
				$('#dg').datagrid('reload');
			} else {
				$.messager.alert('系统提示', data.message, 'warning');
			}
		}
	});
	
}

//添加窗口保存
function saveAddBackList() {

	if (!$('#addForm').form('validate')) {
		return;
	}
	dataValue=serializeObject($('#addForm'));

	
	dataValue.companyName=$("#companyId").combobox("getText");
	dataValue.cityName=$("#cityId").combobox("getText");
	dataValue.provinceName=$("#provinceId").combobox("getText");
    console.log(dataValue);
	$.ajax({
		type : "post",
		url : '/dictionary/addregion.ajax',
		data : dataValue,
		dataType : "json",
		async : false,
		success : function(data) {
            console.log(data);
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
$('#dlg').dialog({
    onClose:function(){
//    	$('#orderId').attr('disabled', true);
    	$("#issend").attr("checked",false);
    }
});
//重置
function reset(){
	$('#provinceId_search').combobox('setValue', "").combobox('setText', '-请选择-');
	$('#cityId_search').combobox('setValue', '').combobox('setText', '');
	$('#cityId_search').combobox('loadData', {});
	$('#regionId_search').combobox('setValue', '').combobox('setText', '');
	$('#regionId_search').combobox('loadData', {});
	$('#companyId_search').combobox('setValue', "").combobox('setText', '-请选择-');
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