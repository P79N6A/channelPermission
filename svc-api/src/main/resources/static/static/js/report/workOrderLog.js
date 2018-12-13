//var img=[];
var ordersource=[];
//分页查询
function searchReview() {
	setFirstPage("#dg");
	$('#dg')
			.datagrid(
					{
						url : '/log/reviewlogpage.ajax',
						queryParams : {
							'username_search' : $('#username_search').val(),

							'mk_search' : $('#mk_search').combobox('getText') != '-请选择-' ? $('#mk_search')
									.combobox('getValue') : "",
							'startTime_search' : $('#startTime_search')
									.combobox('getValue'),
							'endTime_search' : $('#endTime_search').combobox(
									'getValue'),
							'id_search':$('#id_search').val()
//							'issend_search' : $('#issend_search').combobox('getText') != '-请选择-' ? $('#issend_search')
//											.combobox('getValue') : "",
						},
						loadMsg : '数据装载中......',
                        pageSize:50,
                        pageList:[50,100,200],
						onLoadSuccess : function(data) {
							// 当没有返回数据时提示信息
							if (data.total == 0 && data.rows.length == 0) {
								//$.messager.alert('系统提示', '查询无数据', 'warning');
							}
						}

					});
}

//'模块名 1：人员信息管理  2：人员责任位配置 3：工单',
function format(val, row, index) {
	var value = row.mkname;
	if (value == "1") {
		value = '人员信息管理';
	} else if (value == "2") {
		value = '人员责任配置管理';
	}else if (value == "3"){
		value = '工单管理';
	}else if(value=="4"){
		value= '订单来源管理';
	}else if(value=="5"){
		value= '区县工贸管理';
	}
	return value;
}

//1:人员添加 2:人员修改 3:人员删除 4:人员责任位配置添加 5:人员信息修改 6:人员责任位配置删除 
//* 7:责任位添加 8:责任位修改 9:责任位删除 10:工单添加 11:工单修改 12:反馈内容添加 
//* 13:中间结果添加 14:最终结果添加 15:更改责任位 16:工单导出 17:责任位统计导出18:人员统计导出 
function status_format(val, row, index) {
	var value = row.type;
	if (value == "1") {
		value = '人员添加';
	} else if (value == "2") {
		value = '人员修改';
	} else if (value == "3") {
		value = '人员删除';
	} else if (value == "4") {
		value = '人员责任位配置添加';
	} else if (value == "5") {
		value = '人员信息修改';
	} else if (value == "6") {
		value = '人员责任位配置删除 ';
	} else if (value == "7") {
		value = '责任位添加';
	} else if (value == "8") {
		value = '责任位修改';
	} else if (value == "9") {
		value = '责任位删除';
	} else if (value == "10") {
		value = '工单添加';
	} else if (value == "11") {
		value = '工单修改';
	} else if (value == "12") {
		value = '反馈内容添加';
	} else if (value == "13") {
		value = '中间结果添加';
	} else if (value == "14") {
		value = '最终结果添加';
	} else if (value == "15") {
		value = '更改责任位';
	} else if (value == "16") {
		value = '工单导出';
	} else if (value == "17") {
		value = '责任位统计导出';
	} else if (value == "18") {
		value = '人员统计导出 ';
	} 
	else if (value == "19") {
		value = '人员信息管理导出 ';
	} 
	else if (value == "20") {
		value = '人员责任位配置管理导出 ';
	} else if (value == "21") {
		value = '订单来源管理添加 ';
	} else if (value == "22") {
		value = '订单来源管理修改';
	} else if (value == "23") {
		value = '区县添加';
	} else if (value == "24") {
		value = '区县工贸关系修改';
	}else if (value == "25") {
        value = '400工单列表导出';
    }
	return value;
}


// 用来临时存放添加或修改工单时的地址
var url;

//关闭设置
$('#dlg').dialog({
    onClose:function(){
//    	$('#orderId').attr('disabled', true);
    	$("#issend").attr("checked",false);
    }
});


function deltr(clickTd){  
    var tr = $(clickTd).parent().parent();  
    tr.remove();  
}  
//重置
function reset(){
	$("#username_search").val("");
	$('#mk_search').combobox('setValue', "").combobox('setText', '-请选择-');
	$("#id_search").val("");
	$('#startTime_search').datetimebox("clear");
	$('#endTime_search').datetimebox("clear");
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

//日期控件精确到小时
function ww4(date){
    var y = date.getFullYear();  
    var m = date.getMonth()+1;  
    var d = date.getDate();  
    var h = date.getHours();  
    return  y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':00:00';  

}  