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
var dataGrid = null;
function queryDetailed(){
	$.ajax({
        url      : "/operationArea/PrudevtDatail?cOrderSn="+$("#cOrderSn").val(),
        type     : 'POST',
         success  : function(data) {
       	  var row = {
         		    'data': {
         		        'records':
         		        [data.obj]
         		            }
       	  }
//       	var dataGrid = $('#datagrid_orderForecastLogs').datagrid(datagridOptions_orderForecastLogs);
       	  dataGrid.datagrid('loadData', row);
       	  $("#datagrid_orderForecastLogs").datagrid('refreshRow');
        }
	})
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
    dataGrid = $("#datagrid_orderForecastLogs").datagrid({
    		striped: true,
    	    pagination: false,
    	    rownumbers: true,
    	    columns : [ [
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
			field : 'netPointId'
			
		}, {
			width : '100',
			title : '库位',
			field : 'sCode'

		}, {
			width : '100',
			title : '转运库位',
			align : 'right',   
			field : 'tscode'
		}, {
			width : '100',
			title : '处理状态',
			align : 'right',  
			field : 'seasonPer'
		}, {
			width : '100',
			title : '日日单状态',
			align : 'right',  
			field : 'pdOrderStatus'
		}, {
			width : '100',
			title : '开票状态',
			align : 'right',  
			field : 'isMakeReceipt'
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
			field : 'couponCodeValue'
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
    queryDetailed();
});
var goback = function () {
    window.history.back();
};
/**
 * 保存退货信息
 * @returns
 */
function save(){
	var params = $('#addForm_orderForecastGoal').serializeObject();
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