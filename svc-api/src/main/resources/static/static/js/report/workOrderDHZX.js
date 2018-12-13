//var img=[];
var ordersource=[];
//导出工单EXCEL参数
var WORK_ORDER_EXCEL={
		'Q_username_search':null,
		'Q_phone1_search':null,
		'Q_phone2_search':null,
		'Q_phone3_search':null,
	    'Q_messageNum_search':null,
	    'Q_type_search':null,
	    'Q_store_search':null,
	    'Q_storeType_search':null,
    	'Q_workStatus_search':null,
	    'Q_startTime_search':null,
	    'Q_endTime_search':null
};
var VARS={
	'A':null
};
var LEVEL1_QJ={};
var LEVEL2_QJ={};

$(function() {

	var workStatus='[{"id":"-请选择-","text":"-请选择-"},{"id":"未处理","text":"未处理"},{"id":"中间结果","text":"中间结果"},{"id":"最终结果","text":"最终结果"}]';
	var data=$.parseJSON(workStatus);
	$('#workStatus_search').combobox("loadData", data);
	$('#workStatus_search').combobox('setValue', "").combobox('setText', '-请选择-');


	var type='[{"id":"-请选择-","text":"-请选择-"},{"id":"物流类","text":"物流类工单"},{"id":"店铺类","text":"店铺类工单"},{"id":"售后类","text":"售后类工单"}]';
	var data=$.parseJSON(type);
	$('#type_search').combobox("loadData", data);
	$('#type_search').combobox('setValue', "").combobox('setText', '-请选择-');



})
$(function(){

    $.ajax({
        type : "get",
        url : "/dictionary/getdictionary.ajax",
        data : {
            "value_set_id" : "qd_1"
        },
        dataType : "json",
        success : function(data) {
            a = [];
            a.value = "";
            a.valueMeaning = "-请选择-";
            data.unshift(a);


            tmp = data.slice(0);


            LEVEL1_QJ=tmp;

            $('#storeType_search').combobox("loadData", data);

        }
    });

})

//获取二级责任位
$(function() {

    $.ajax({
        type : "get",
        url : "/dictionary/getdictionary.ajax",
        data : {
            "value_set_id" : "duty_2"
        },
        dataType : "json",
        success : function(data) {
            //复制对象
            tmp = data.slice(0);
            LEVEL2_QJ=tmp;
            // a = [];
            // a.value = "";
            // a.valueMeaning = "-请选择-";
            // data.unshift(a);
            // $('#level1_search').combobox('loadData', data);
            // $('#level1_search2').combobox('loadData', data);
        }
    });

});


$('#storeType_search2').combobox(
    {
        valueField : 'value',
        textField : 'valueMeaning',
        onLoadSuccess : function(data) {
            $('#storeType_search2').combobox('setValue', "").combobox(
                'setText', '-请选择-');

        },
    });

$('#storeType_search3').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
		onLoadSuccess : function(data) {
			$('#storeType_search3').combobox('setValue', "").combobox(
				'setText', '-请选择-');

		},
	});

//界面责任位1
$('#storeType_search').combobox(
    {
        valueField : 'value',
        textField : 'valueMeaning',
        onLoadSuccess : function(data) {

            $('#storeType_search').combobox('setValue', "").combobox(
                'setText', '-请选择-');
            $('#storeType_search2').combobox('setValue', "").combobox('setText', '');
        },
        onChange : function(record) {


            var n = $('#storeType_search').combobox('getValue');
            $.ajax({
                type : "GET",
                url : "/dictionary/gettwomenu.ajax",
                data : {
                    "value_set_id":"qd_2",
                    "parent_value" : n
                },
                dataType : "json",
                success : function(data) {

                    a = [];
                    a.value = "";
                    a.valueMeaning = "-请选择-";
                    data.unshift(a);
                    $('#storeType_search2').combobox('loadData', data);

                }
            });

        }
    });













 // 分页查询
function searchReview() {

	 var startTime= $('#startTime_search').combobox('getValue');
	var endTime= $('#endTime_search').combobox('getValue');
	if(startTime!=''&&endTime!=''){
		if(startTime>endTime){
		$.messager.alert('系统提示', '开始时间不能大于结束时间', 'warning');
		return;
		}
	}


	setFirstPage("#dg");


	 WORK_ORDER_EXCEL.Q_username_search= $('#username_search').val();
	WORK_ORDER_EXCEL.Q_phone1_search= $('#phone1_search').val();
	WORK_ORDER_EXCEL.Q_phone2_search= $('#phone2_search').val();
	WORK_ORDER_EXCEL.Q_phone3_search= $('#phone3_search').val();
	WORK_ORDER_EXCEL.Q_messageNum_search= $('#messageNum_search').val();
	WORK_ORDER_EXCEL.Q_workStatus_search= $('#workStatus_search').combobox(
		'getText') != '-请选择-' ? $('#workStatus_search')
		.combobox('getText') : "";
	WORK_ORDER_EXCEL.Q_type_search=  $('#type_search').combobox(
		'getText') != '-请选择-' ? $('#type_search')
		.combobox('getText') : "";
	WORK_ORDER_EXCEL.Q_store_search= $('#storeType_search').combobox(
		'getText') != '-请选择-' ? $('#storeType_search')
		.combobox('getText') : "";
	WORK_ORDER_EXCEL.Q_storeType_search= $('#storeType_search2').combobox(
		'getText') != '-请选择-' ? $('#storeType_search2')
		.combobox('getText') : "";
	WORK_ORDER_EXCEL.Q_startTime_search=	 $('#startTime_search')
		.combobox('getValue'),
	WORK_ORDER_EXCEL.Q_endTime_search=$('#endTime_search').combobox(
		'getValue')



	$('#dg')
			.datagrid(
					{
						url : '/workOrderDHZX/getReviewPoolForDhzxList',
						queryParams : {
							'username_search' : $('#username_search').val(),
							'phone1_search' : $('#phone1_search').val(),
							'phone2_search' : $('#phone2_search').val(),
							'phone3_search' : $('#phone3_search').val(),
							'messageNum_search' : $('#messageNum_search').val(),
							'workStatus_search' : $('#workStatus_search').combobox(
								'getText') != '-请选择-' ? $('#workStatus_search')
								.combobox('getText') : "",
							'type_search' : $('#type_search').combobox(
								'getText') != '-请选择-' ? $('#type_search')
								.combobox('getText') : "",
							'store_search' : $('#storeType_search').combobox(
								'getText') != '-请选择-' ? $('#storeType_search')
								.combobox('getText') : "",
							'startTime_search' : $('#startTime_search')
								.combobox('getValue'),
							'endTime_search' : $('#endTime_search').combobox(
								'getValue'),
							'storeType_search' : $('#storeType_search2').combobox(
								'getText') != '-请选择-' ? $('#storeType_search2')
								.combobox('getText') : ""

						},
						onClickRow: function (index, row) { //easyui封装好的时间（被单机行的索引，被单击行的值）
							if(VARS.A=="TRUE"){
								//调用查看

								viewPool(row);
								VARS.A=null;
							}


						},
						loadMsg : '数据装载中......',
                        nowrap: false,
                        singleSelect: true,
						pageSize:50,
                        pageList:[50,100,200],
						onLoadSuccess : function(data) {

							var panel = $(this).datagrid('getPanel');
							var tr = panel.find('div.datagrid-body tr');
							tr.each(function() {
								var td0 = $(this).children(
									'td[field="messageNum"]');
								var textValue0 = td0.children("div").text();
								td0.children("div").html(
									'<a href="javascript:void(0)" onclick="viewPools()">'
									+ textValue0 + '</a>');
								var td3 = $(this).children(
									'td[field="workStatus"]');

								var value = td3.children("div").text();

								if (value == 0) {
									value = '未处理';
								} else if (value == 1) {
									value = '已确认';
								} else if (value == 2) {
									value = '中间结果';
								} else if (value == 3) {
									value = '最终结果';
								} else {
									value = '';
								}
								td3.children("div").html(value);




							});






							// 当没有返回数据时提示信息
							if (data.total == 0 && data.rows.length == 0) {
								//$.messager.alert('系统提示', '查询无数据', 'warning');
							}
						}


					});

}
function viewPools(){
	VARS.A="TRUE";
}

// 用来临时存放添加或修改工单时的地址
var url;
// 添加人员
function newBackList() {
	//从新加载iframe

	$('#dlg').dialog('open').dialog('setTitle', '人员添加');
	$('#fm').form('clear');
	QJ_DATAVALUE="";
	// 设置URL为添加工单地址
	url = '/user/addReviewUserPeople.ajax';

	$('#type').combobox('setValue', "").combobox(
	'setText', '-请选择-');
}
var QJ_DATAVALUE;
//查看
function viewPool() {

	$('#store').combobox('disable');
	$('#storeType_search3').combobox('disable');

	//从新加载iframe
	$("#contextDiv2").html("");
    $("#backContext3").val("");
    $('#backContext3').attr('readonly', false);
	var selRows = $("#dg").datagrid("getSelections");
	if (selRows.length == 1) {

		var row = $('#dg').datagrid('getSelected');

		$('#dlg').dialog('open').dialog('setTitle', '工单明细');
		$('#id').val(row.id);
		$("#contextDiv2").html(row.problemMessage);
		$("#problem1").val("");
		$('#fm').form('load', row);
		$("#backDiv").html("");
		$("#backDiv2").html("");
		$("#backDiv3").html("");



//			// 设置URL为修改工单地址
			url = 'workorder/updateReviewPoolForDhzx',
			QJ_DATAVALUE=serializeObject($('#fm'));

	} else if(selRows.length == 0){
		$.messager.alert('系统提示', '请选择要操作的数据', 'warning');
	}else if(selRows.length > 1){
		$.messager.alert('系统提示', '请选择一条数据', 'warning');
	}

	$.ajax({
		type : "post",
		url : '/middleresult/selectmiddleresult.ajax',
		data : {
			reviewId : row.id
		},
		dataType : "json",
		async : false,
		success : function(result) {
			var newText = $("#backDiv3").html();

			for (var i = 0; i < result.length; i++) {
				var oldText = $("#backDiv").html();
				if (i > 0) {
					oldText += '<br/>';
				} else {
					oldText = '';
				}
				$("#backDiv").html(
					oldText + result[i].addtime + "  "
					+ result[i].adduser + "  "
					+ result[i].middleContext);
				if(result[i]!=null)
				{
					$("#backDiv3").html(
						newText + "("+result[0].addtime + "  "
						+ result[0].adduser + "  "
						+ result[0].middleContext+")");
				}
				if(i+1==3)
				{
					if(null!=row.workOrderTo&&row.workOrderTo=='HP')
					{
						break;
					}
				}
			}
		}
	});



	$.ajax({
		type : "post",
		url : '/middleresult/findFinalResults.ajax',
		data : {
			reviewId : row.id
		},
		dataType : "json",
		async : false,
		success : function(result) {
			for (var i = 0; i < result.length; i++) {
				var findOldText = $("#backDiv2").html();
				if (i > 0) {
					findOldText += '<br/>';
				} else {
					findOldText = '';
				}
				$("#backDiv2").html(
					findOldText + result[i].addtime + "  "
					+ result[i].adduser + "  "
					+ result[i].middleContext);

				if(result[i]!=null&&result[i]!='')
				{

					$("#backContext3").val(
						result[0].addtime + "  " + result[0].adduser + "  "
						+ result[0].middleContext);
				}
			}
		}
	});

    zr1=row.store;
    question1Level2ValueCheck="";
    for(var i=0;i<LEVEL1_QJ.length;i++){
        if(LEVEL1_QJ[i].valueMeaning==zr1){
            question1Level2ValueCheck=LEVEL1_QJ[i].value;
            break;
        }
    }


    $.ajax({
        type : "GET",
        url : "/dictionary/gettwomenu.ajax",
        data : {
            "value_set_id":"qd_2",
            "parent_value" : question1Level2ValueCheck
        },
        dataType : "json",
        success : function(data) {
            $('#storeType_search3').combobox('loadData', data);
            $('#storeType_search3').combobox('setText', row.storeType);
        }
    });





	$(function(){

		$.ajax({
			type : "get",
			url : "/dictionary/getdictionary.ajax",
			data : {
				"value_set_id" : "qd_1"
			},
			dataType : "json",
			success : function(data) {


				$('#store').combobox("loadData", data);
				$('#store').combobox('setText', row.store);

			}
		});

	})







    if($("#backContext3").val()!=""){
        $('#backContext3').attr('readonly', true);
    } if($("#workStatus").val()!="3"){
		$('#backContext3').attr('readonly', false);
		$("#backContext3").val("");
	}





}





$('#store').combobox(
	{
		valueField : 'value',
		textField : 'valueMeaning',
		onLoadSuccess : function(data) {
/*
			$('#store').combobox('setValue', "").combobox(
				'setText', '-请选择-');
			$('#storeType_search3').combobox('setValue', "").combobox('setText', '');*/
		},
		onSelect : function(record) {

			if ($('#store').combobox('getText') == '-请选择-') {
				$('#storeType_search3').combobox('setValue', '');
				$('#storeType_search3').combobox('setText', '');
				$('#storeType_search3').combobox('loadData', {});
				return;
			}
			var n = record.value;
			$.ajax({
				type : "GET",
				url : "/dictionary/gettwomenu.ajax",
				data : {
					"value_set_id":"qd_2",
					"parent_value" : n
				},
				dataType : "json",
				async: true,
				success : function(data) {

					a = [];
					a.value = "";
					a.valueMeaning = "-请选择-";
					data.unshift(a);
					$('#storeType_search3').combobox('loadData', data);

				}
			});

		}
	});




// 添加/修改人员
function saveBackList() {

    if($("#workStatus").val()=="3"){
        $.messager.alert('系统提示', '该信息已做过最终结果', 'warning');
        return;
    }
if( $('#storeType_search3').combobox('getText') == '-请选择-' ){
	$.messager.alert('系统提示', '请选择店铺', 'warning');
	return;
}
    $.ajax({
		type : "post",
		url : '/workOrderDHZX/updateReviewPoolForDhzxs.ajax',
		data : {
			"id" : $("#id").val(),
			"problem" : $("#problem1").val(),
			"backContext3" : $("#backContext3").val(),
            "storeType":  $('#storeType_search3').combobox(
				'getText') != '-请选择-' ? $('#storeType_search3')
				.combobox('getText') : "",
			"store":  $('#store').combobox('getText')
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.success) {
				$.messager.alert('系统提示', data.result, 'warning');
				$('#dlg').dialog('close');
				$('#dg').datagrid('reload');
			} else {
				$.messager.alert('系统提示', data.message, 'warning');
			}
			// }
			// if (result.succesinfo){
			// $.messager.alert('系统提示',result.succesinfo,'warning');
			// $('#dlg').dialog('close');
			// $('#dg').datagrid('reload');
			// }
		}
	});



	
}
//删除信息
function deleteuser(){
	//dataValue=serializeObject($('#fm'));
	dataValue = $('#dg').datagrid('getSelections');
	if(dataValue.length == 0){
		$.messager.alert('系统提示', '请选择要操作的数据', 'warning');
		return;
	}
	$.messager.confirm('系统提示', '</br>是否确认删除</br></br>',function(r){
	if(r){ 
		$.ajax({
			type : "post",
			url : '/user/deleteuser.ajax',
			data : {values:JSON.stringify(dataValue)},
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.result, 'warning');
					$('#dlg').dialog('close');
					$('#dg').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.message, 'warning');
				}
				
					}
				});
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
//下载图片
$("#btnDownload").click(function(){
	
	var row = $('#dg').datagrid('getSelected');
	window.open('image/downloadimage.ajax?reviewId='+row.id);
});

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
//日期控件精确到小时
function ww4(date){
    var y = date.getFullYear();  
    var m = date.getMonth()+1;  
    var d = date.getDate();  
    var h = date.getHours();  
    return  y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':00:00';  
}  
//是否咨询选中可编辑
//$(".ynzx").click(function(){
//	//alert($('.ynzx').attr('checked'))
//	if ($('.ynzx').attr('checked'))  {  
//		$('#orderId').attr('disabled', false);
//		$('#productName').attr('disabled', false);
//	}else{
//		//不可用
//		$('#orderId').attr('disabled', true);
//	}
//	
//	})
//关闭设置
$('#dlg').dialog({
    onClose:function(){
//    	$('#orderId').attr('disabled', true);
    	$("#issend").attr("checked",false);
    }
});

//责任位
function addTr(tab, row, trHtml){
    //获取table最后一行 $("#tab tr:last")
    //获取table第一行 $("#tab tr").eq(0)
    //获取table倒数第二行 $("#tab tr").eq(-2)
    var $tr=$("#"+tab+" tr").eq(row);
    if($tr.size()==0){
       alert("指定的table id或行数不存在！");
       return;
    }
    $tr.after(trHtml);
 }

function addTr2(tab, row){
    var trHtml="<tr><td><a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='addTr2('addList', -1)'></a></td><td><a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='removeTr2('addList', -1)'></a></td><td style='width:75px' align='right' valign='middle'>责任位:</td><td><select  class='easyui-combobox' data-options='editable:false'  style='width:150px' id='question1Level1' name='question1Level1'></select></td><td style='width:75px' align='right' valign='middle'>责任位2:</td><td><select  class='easyui-combobox' data-options='editable:false'  style='width:150px' id='question1Level2' name='question1Level2' data-options='valueField: 'value',textField: 'valueMeaning''></select></td><td style='width:75px' align='right' valign='middle'>订单来源:</td><td><select  class='easyui-combobox' style='width:180px;' id='remark6_search' name='remark6_search' data-options='editable:false'></select></td><td style='width:100px' align='right' valign='middle'>工贸:</td><td ><input id='company' name='company' class='easyui-validatebox textbox' style='width:148px;' ></input></td></tr>";
    addTr(tab, row, trHtml);
  }

function deltr(clickTd){  
    var tr = $(clickTd).parent().parent();  
    tr.remove();  
}  
//重置
function reset(){
	$("#messageNum_search").val("");
	$("#phone1_search").val("");
	$("#phone2_search").val("");
	$("#phone3_search").val("");
	$("#username_search").val("");

}
//导出
function exp() {
	var data=$('#dg').datagrid('getData');
	if(data.rows.length<=0){
		$.messager.alert('系统提示', "没有数据,无法导出!", 'warning');
		return;
	}


	//var selectedvalue=$("input[name='tab']:checked").val();
		window.location.href="/export/exportdhzx?"+
				'username_search='+WORK_ORDER_EXCEL.Q_username_search
				+'&phone1_search=' + WORK_ORDER_EXCEL.Q_phone1_search
				+'&phone2_search='+WORK_ORDER_EXCEL.Q_phone2_search
				+'&phone3_search='+WORK_ORDER_EXCEL.Q_phone3_search
	            +'&messageNum_search='+WORK_ORDER_EXCEL.Q_messageNum_search
			+'&workStatus_search='+WORK_ORDER_EXCEL.Q_workStatus_search
            +'&type_search='+WORK_ORDER_EXCEL.Q_type_search

	+'&store_search='+WORK_ORDER_EXCEL.Q_store_search
			+'&storeType_search='+WORK_ORDER_EXCEL.Q_storeType_search
			+'&startTime_search='+WORK_ORDER_EXCEL.Q_startTime_search
			+'&endTime_search='+WORK_ORDER_EXCEL.Q_endTime_search;


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