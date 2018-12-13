//var datagridData_orderForecastLogs = {
//    'data': {
//        'records': [
//            {
//                'secCode': 'B2B2C', sortable: true,
//                'overTimeType': 'HBM-F25', sortable: true,
//                'finishTime': 'F702R1001', sortable: true,
//                'receivePerson': 'FSWA', sortable: true,
//                'telephone': '', sortable: true,
//                'location': '恒温调奶器', sortable: true,
//                'deliverRight': '额敏县启丰家电维修服务站', sortable: true,
//                'seasonPer': '已占用库存', sortable: true,
//                'sataus': '普通单', sortable: true,
//                'secCode1': '￥558.00', sortable: true,
//                'secName1': '￥0.00', sortable: true,
//                'overTimeType1': '1', sortable: true,
//                'finishTime1': '￥0.00', sortable: true,
//                'receivePerson1': '否', sortable: true,
//                'telephone1': '￥0.00', sortable: true,
//                'location1': '￥0.00', sortable: true,
//                'deliverRight1': '0', sortable: true,
//                'seasonPer1': '￥558.00', sortable: true,
//                'sataus1': '￥558.00', sortable: true,
//                'recepitStatus':'已开票', sortable: true,
//                'fixSave':'￥0.00', sortable: true,
//            }
//        ], 'totalCount': 4
//    }
//};

$(function () {
//    var datagrid = $('#datagrid_orderForecastLogs').datagrid(datagridOptions_orderForecastLogs);
//    datagrid.datagrid('loadData', datagridData_orderForecastLogs);
    // $('.datagrid-view').css("height","auto");datagrid-view
    //console.log($(".tabs-title").length+"===");
    $("#addBtn_orderForecastGoal").on('click', function (event) {
        $('#addForm_orderForecastGoal').form('reset');
        $('#addForm_orderForecastGoal').find('[__actType]').val('add');
        $('#addDlg_orderForecastGoal').dialog({'title': '退换货信息申请'});
        $('#addDlg_orderForecastGoal').dialog('open');
    });
    var opId = $("#opId").val();
    //wa拆单
    $("#addBtn_wa").on('click', function (event) {

        $.ajax({
            url: "/separate/judeInvoice",
            data: {orderProductId:opId},
            success: function(data){
            // var data =    JSON.stringify(data);
            	if(data == "no"){
                    $.messager.alert("操作提示","请联系业务先作废发票");
				}
				if(data == "yes"){
                    $('#addForm_tbgroupbuy').form('reset');
                    $('#addDlg_tbgroupbuy').dialog({'title': 'WA拆单'});
                    $('#addDlg_tbgroupbuy').dialog('open');
				}
            }
        });

    });

    $('#update_wa').click(function(){
        //页面来源订单号
        var num =  $('#num').val();

            $.ajax({
                url      : "/separate/separateBill",
                data     : {		orderProductId:opId,
                    num: num
                },
                success  : function(data) {
                    $('#addDlg_tbgroupbuy').dialog('close');
                    if(data == "no"){
                        $.messager.alert("操作提示","请输入正确数量");
                    }
                    if(data == "yes"){
                        $.messager.alert("操作提示","拆单成功");
                    }
                }
            });

    });
    dataGrid = $("#datagrid_orderForecastLogs").datagrid({
    		striped: true,
    	    pagination: false,
    	    rownumbers: true,
    	    columns : [ [
    	    	{
			width : '120',
			title : 'id',
			field : 'id',
			hidden:'true'
		},
    	{
			width : '120',
			title : '当前物流模式',
			field : 'shippingMode'
		},{
			width : '150',
			title : '商品名称',
			field : 'productName'
		}, {
			width : '120',
			title : '商品编号',
			field : 'sku'
		}, {
			width : '100',
			title : '商品类型',
			field : 'productTypeTs'
		}, {
			width : '130',
			title : '网点',
			field : 'netPointName'
			
		}, {
			width : '100',
			title : '库位',
			field : 'sCode'

		}, {
			width : '100',
			title : '转运库位',
			align : 'right',   
			field : 'tsCode'
		}, {
			width : '100',
			title : '处理状态',
			align : 'right',  
			field : 'seasonPer'
		},
		{
			width : '100',
			title : '日日单状态',
			align : 'right',  
			field : 'pdOrderStatus',
				formatter: function(value,row,index){
				if("0"==value){
					return "否";
				}else if("1"==value){
					return "否";
				}
			}
		}, 
		{
			width : '100',
			title : '开票状态',
			align : 'right',  
			field : 'isMakeReceiptStatus'
		}, {
			width : '90',
			title : '价格',
			align : 'right',  
			field : 'price'
		},{
			width : '100',
			title : '配送费',
			field : 'shippingFee'
		}, {
			width : '80',
			title : '数量',
			align : 'right',  
			field : 'number'
		}, {
			width : '80',
			title : '商城优惠券',
			align : 'right',  
			field : 'couponAmount'
		},{
			width : '80',
			title : '节能补贴',
			align : 'right',  
			field : 'esamount'
		}, {
			width : '80',
			title : '下单立减',
			field : 'orderpromotionamount'
		}, {
			width : '350',
			title : '金额(数量×商品价格+运费-外部优惠金额-节能补贴)',
			field : 'money'
		}] ],
	toolbar: '#datagridToolbar_orderForecastLogs'
	}); 
	
     $("#OperationHistory").datagrid({
    		striped: true,
    	    pagination: false,
    	    rownumbers: true,
    	    columns : [ [
    	{
			width : '180',
			title : '操作时间',
			field : 'logTime',
			formatter: function(value,row,index){
				return timestampToTime(value);
			}
		},{
			width : '150',
			title : '订单号',
			field : 'cOrderSn'
		}, {
			width : '150',
			title : '操作人',
			field : 'operator'
		}, {
			width : '200',
			title : '更改内容',
			field : 'changeLog'
		}, {
			width : '250',
			title : '备注',
			field : 'remark'
			
		}] ],
	toolbar: '#datagridToolbar_orderForecastLogs'
	}); 
	
    queryDetailed();
    OperationHistory();
});
var dataGrid = null;
function queryDetailed(){
	$.ajax({
        url      : "/operationArea/PrudevtDatail?cOrderSn="+$("#cOrderSn").val(),
        type     : 'get',
        async: false,
         success  : function(data) {
var row = {
         		    'data': {
         		        'records':
         		        [eval('('+data+')')]
         	}
         	
         	}
       	 
       	  dataGrid.datagrid('loadData', row);
       	  $("#datagrid_orderForecastLogs").datagrid('refreshRow');
        }
	})
}

function OperationHistory(){
	var rows=$("#datagrid_orderForecastLogs").datagrid("getRows")
	$.ajax({
        url      : "/operationArea/OperationHistory",
        type     : 'post',
        data:{
        	productId:rows[0].id
        },
         success  : function(data) {
var row = {
         		    'data': {
         		        'records':
         		        data
         	}
         	
         	}
       	 
       	  $("#OperationHistory").datagrid('loadData', row);
       	  $("#datagrid_orderForecastLogs").datagrid('refreshRow');
        }
	})
}

function timestampToTime(timestamp) {
        var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
				var Y = date.getFullYear() + '-';
				var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
				var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
				var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
				var m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
				var s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());
        return Y+M+D+h+m+s;
    }


//var datagridOptions_orderForecastLogs = {
//    fit: true,
//    fitColumns: true,
//    singleSelect: true,
////    url: '/operationArea/PrudevtDatail?PrudevtDatail='+'',
//    /*frozenColumns: [[{
//     field: 'id', checkbox: true
//     }]],*/
//    columns: [[
//        {title: '当前物流模式', field: 'shippingmode', sortable: true,},
//        {title: '商品名称', field: 'productname', sortable: true},
//        {title: '商品编号', field: 'sku', sortable: true},
//        {title: '商品类型', field: 'productType', sortable: true},
//        {title: '网点', field: 'netpointid', sortable: true},
//        {title: '库位', field: 'scode', sortable: true},
//        {title: '转运库位', field: 'tscode', align: "center"},
//
//        {title: '处理状态', field: 'seasonPer', sortable: true},
//        {title: '日日单状态', field: 'pdorderstatus', sortable: true},
//        {title: '开票状态', field: 'ismakereceipt', sortable: true},
//
//        {title: '价格', field: 'price', sortable: true,},
//        {title: '配送费', field: 'shippingfee', sortable: true},
//        {title: '数量', field: 'number', sortable: true},
//        {title: '商城优惠券', field: 'couponcodevalue', sortable: true},
//        {title: '节能补贴', field: 'esamount', sortable: true},
//        {title: '下单立减', field: 'orderpromotionamount', align: "center"},
//        {title: '金额(数量×商品价格+运费-外部优惠金额-节能补贴)', field: 'money', sortable: true}
//    ]],
//    //autoRowHeight: true,
//    //nowrap: false,
//    toolbar: '#datagridToolbar_orderForecastLogs',
//    striped: true,
//    pagination: false,
//    rownumbers: true,
//};

var goback = function () {
    window.history.back();
};
/**
 * 保存退货信息
 * @returns
 */
function save(){
	var params = $('#addForm_orderForecastGoal').serializeObject();
    var typeFlag = $("#typeFlag").combobox("getValue");
    var cOrderSnId = $("#cOrderSnId").textbox("getValue");
    var contactName = $("#contactName").textbox("getValue");
    var contactMobile = $("#contactMobile").textbox("getValue");
    var count = $("#count").textbox("getValue");
    var reason = $("#reason").combobox("getValue");
    var description = $("#description").val();
    if (typeFlag == null || typeFlag == ""){
    	alert("退/换货类型不能为空");
    	return false;
	}
    if (cOrderSnId == null || cOrderSnId == ""){
        alert("网单号不能为空");
        return false;
    }
    if (contactName == null || contactName == ""){
        alert("联系人不能为空");
        return false;
    }
    if (contactMobile == null || contactMobile == ""){
        alert("手机不能为空");
        return false;
    }
    if (count == null || count == ""){
        alert("数量不能为空");
        return false;
    }
    if (reason == null || reason == ""){
        alert("退换货原因不能为空");
        return false;
    }
    if (description == null || description == ""){
        alert("退换货描述不能为空");
        return false;
    }
    $.messager.confirm('询问','确定保存吗？',function(r){
     	if(r){
        $.messager.progress({title:'执行中...', msg:'执行中...'});
        $.ajax({
            url         : '/operationArea/SaveRepairs',
            type        : "POST",
            dataType : 'json',
            contentType : "application/json;charset=utf-8",
            data        : JSON.stringify(params),
            success:function(data){
                $.messager.progress('close');
                $.messager.alert('提示', data.msg, 'info', function(){
                    if(data.success){
                    	window.location.href='/operationArea/ReturnEdit?id='+data.obj;
                    }
                });
            },
            error:function(XMLHttpRequest, textStatus, errorThrow, data){
                $.messager.progress('close');
                $.messager.alert('提示', "数据保存错误", 'error');
            }
       });
        	}
        });
}
function closeOrder() {
    var cOrderSn = document.getElementById("cOrderSn").value;
    var mark = $("#mark").textbox('getValue');
    mark = mark.trim();
    if (mark == null || mark ==''){
        alert("网单关闭理由不能为空");
        return false;
	}
	var sCode =  $("#sCode").val();
    if (sCode == 'CT01') {
        alert("海鹏暂时不能关闭网单");
        $('#oDialogclo').dialog('close');
        return false;
	}
	if (sCode == 'JS01') {
        alert("净水暂时不能关闭网单");
        $('#oDialogclo').dialog('close');
        return false;
    }
   	$.ajax({
        url: '/operationArea/closeOrder',
		data:{
            cOrderSn:cOrderSn,
			mark:mark
		},
        success  : function(data){
            $.messager.alert('提示',data.msg);
            $('#oDialogclo').dialog('close');
            $.ajax({
                url: '/operationArea/ProductView',
				async:true,
                data:{
                    cOrderSn:cOrderSn
                },
			})
		}
	})
}
function opencloseOrder() {
    $("#mark").textbox('setValue', '');
    $('#oDialogclo').dialog('open');

}

function upsCode() {
	var sCode = $('#sCode').val();
	var newsCode =$("#newsCode").textbox("getValue");
	if (newsCode == '' || newsCode == null){
        alert("更改的库位不能为空");
        return false;
	}
	var cOrderSn = $('#cOrderSn').val();
	if (sCode == newsCode){
        alert("输入的新旧库位不能相同");
		return false;
	}
    $.ajax({
        url: '/occupyStockFailController/updatesCode',
        data:{
            cOrderSn : cOrderSn,
            newsCode : newsCode
        },
        success  : function(data){
            $('#oDialog4').dialog('close');
            alert(data);
            queryDetailed();
            OperationHistory();
        },
		error : function(data){
            $('#oDialog4').dialog('close')
            alert(data);
        }
    })
}