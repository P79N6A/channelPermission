var datagridOptions_orderForecastLogs = {
    fit: true,
    // fitColumns: true,
    singleSelect: true,
    url: '/pop/findCommodityList',
    /*frozenColumns: [[{
        field: 'id', checkbox: true
    }]],*/
    columns: [[
//        {title: '当前物流模式', field: 'energysubsidyamount', sortable: true},
        {title: '网单号', field: 'cOrderSn', sortable: true},
        {title: '商品名称', field: 'productName', sortable: true},
        {title: '商品编号', field: 'sku', sortable: true},
//        {title: '库位', field: 'scode', sortable: true},
//        {title: '转运库位', field: 'telephone', align: "center"},
        {title: '商品分类', field: 'typeName', sortable: true},
        {title: '品牌', field: 'brandName', sortable: true},
//        {title: '网点', field: 'deliverRight', sortable: true},
//        {title: '处理状态', field: 'status', sortable: true,
//        	formatter:function(val,rec){ 
//            	if (val == "200"||val == "0"){ 
//            			return "未确认"; 
//            		} else if(val == "204"){ 
//            			return "部分缺货"; 
//            		} else if(val == "201"){
//            			return "已确认";
//            		} else if(val == "203"){
//            			return "已完成";
//            		} else if(val == "202"){
//            			return "已取消";
//            		}
//           		}
//        },
//        {title: '日日单状态', field: 'sataus', sortable: true},

        {title: '价格', field: 'price', sortable: true,align:'right'},
//        {title: '配送费', field: 'secName1', sortable: true},
        {title: '数量', field: 'number', sortable: true,align:'right'},
//        {title: '天猫子订单号', field: 'oid', sortable: true},
//        {title: '商城优惠券', field: 'finishTime1', sortable: true},
//        {title: '自营转单', field: 'receivePerson1', sortable: true},
//        {title: '下单立减', field: 'telephone1', align: "center"},
//        {title: '使用礼品卡', field: 'location1', sortable: true},
//        {title: '使用积分', field: 'deliverRight1', sortable: true},
//        {title: '使用H码', field: 'seasonPer1', sortable: true},
//        {title: '金额', field: 'seasonPer1', sortable: true}
//        {title: '操作', field: 'operation', sortable: true}
    ]],
//    autoRowHeight: true,
    nowrap: false,
    toolbar: '#datagridToolbar_orderForecastLogs',
    striped: true,
    pagination: false,
    rownumbers: true,
};

var datagrid_operateHistory = {
	    fit: true,
	    fitColumns: true,
	    singleSelect: true,
	    url: '/pop/findOperateHistory',
	    columns: [[
            {title: '操作时间', field: 'operateTime', sortable: true},
	        {title: '订单号', field: 'ordersn', sortable: true},
	        {title: '操作人', field: 'operator', sortable: true},
	        {title: '变更项目', field: 'changeitem', sortable: true},
	        {title: '变更内容', field: 'changecontent', sortable: true},
	    ]],
//	    autoRowHeight: true,
	    nowrap: false,
	    toolbar: '#datagridToolbar_orderForecastLogs',
	    striped: true,
	    pagination: false,
	    rownumbers: true,

		
}

$(function () {
    var datagrid = $('#datagrid_orderForecastLogs').datagrid(datagridOptions_orderForecastLogs);
    var data = $("#datagrid_operateHistory").datagrid(datagrid_operateHistory); 
//    $('#dlg').dialog('close');
//    datagrid.datagrid('loadData', datagridData_orderForecastLogs);
    // $('.datagrid-view').css("height","auto");datagrid-view
});
var fromWhere = document.referrer;

/**
 * 修改订单备注信息
 */
$("#updateRemark").on('click', function (event) {
    $('#updateRemark_form').form('reset');
    $('#updateRemark_dialog').dialog('open');
    var d = document.getElementById("remarkId");  
	var nodeList = d.getElementsByTagName("span");
	var rm = nodeList[1].innerHTML;
	$("#codConfirmRemark").textbox("setValue",rm);
	$("#codConfirmRemark").textbox('textbox').attr('maxlength',125);
	
});
$("#save_updateRemark").on('click', function () {
	var orderSn = $("#orderSn").val();
	var codConfirmRemark = $("#codConfirmRemark").val();
	$.post("/pop/updateRemark",{orderSn:orderSn,codConfirmRemark:codConfirmRemark},function(data){
		if(data=="success"){
			$('#updateRemark_dialog').dialog('close');
			console.info(codConfirmRemark);
			window.location.href ="/pop/orderDetailFromP?orderSn="+orderSn;
		}
	});
});

/**
 * 修改收货人信息
 */
$("#updateOrign").on('click', function (event) {
    $('#updateOrigin_form').form('reset');
    $('#updateOrigin_dialog').dialog('open');
    var d = document.getElementById("consigneeId");  
	var nodeList = d.getElementsByTagName("span");
	var aa = nodeList[1].innerHTML;
	$("#consignee").textbox("setValue",aa);
	$("#consignee").textbox('textbox').attr('maxlength',30);
	
	var d = document.getElementById("mobileId");  
	var nodeList = d.getElementsByTagName("span");
	var aa = nodeList[1].innerHTML;
	$("#mobile").textbox("setValue",aa);
	$("#mobile").textbox('textbox').attr('maxlength',15);
	
	var d = document.getElementById("originRegionNameId");  
	var nodeList = d.getElementsByTagName("span");
	var aa = nodeList[1].innerHTML;
	$("#originRegionName").textbox("setValue",aa);
	$("#originRegionName").textbox('textbox').attr('maxlength',125);
	
	var d = document.getElementById("originAddressId");  
	var nodeList = d.getElementsByTagName("span");
	var aa = nodeList[1].innerHTML;
	$("#originAddress").textbox("setValue",aa);
	$("#originAddress").textbox('textbox').attr('maxlength',125);
});

$("#save_updateOrigin").on('click', function () {
//	var originAddress = $(".originAddress").val();
	console.log("originAddress"+originAddress);
	var orderSn = $("#orderSn").val();
	var consignee = $("#consignee").val();
	var mobile = $("#mobile").val();
	var originRegionName = $("#originRegionName").val();
	var originAddress = $("#originAddress").val();
	var params = {
			orderSn:orderSn,
			consignee:consignee,
			mobile:mobile,
			originRegionName:originRegionName,
			originAddress:originAddress
	}
	$.post("/pop/updateOrigin",params,function(data){
		if(data=="success"){
			$('#updateOrigin_dialog').dialog('close');
//			$("#updateOrigin_form").form("clear");
			console.info(originAddress);

			window.location.href ="/pop/orderDetailFromP?orderSn="+orderSn;
			// $(".consignee").html(consignee);
			// $(".mobile").html(mobile);
			// $(".originRegionName").html(originRegionName);
			// $(".originAddress").html(originAddress);
		}				
	}); 
});

/**
 * 修改关联订单号信息
 */
$("#updateOid").on('click', function (event) {
    $('#updateOid_form').form('reset');
    $('#updateOid_dialog').dialog('open');
    var rm = $("#oid").val();
    $("#codConfirmOid").textbox("setValue", rm);
    $("#codConfirmOid").textbox('textbox').attr('maxlength', 50);

});
$("#save_updateOid").on('click', function () {
    clickCount();
    var orderSn = $("#orderSn").val();
    var codConfirmOid = $("#codConfirmOid").val();
    if (codConfirmOid == "" || codConfirmOid == null) {
        $.messager.alert('提示', "关联订单号不能为空");
        return;
    }
    $.post("/pop/updateOid", {orderSn: orderSn, oid: codConfirmOid}, function (data) {
        if (data == "success") {
            $('#updateOid_dialog').dialog('close');
            console.info(codConfirmOid);
            window.location.href = "/pop/orderDetail?orderSn=" + orderSn;
        } else {
            $.messager.alert('提示', "该关联订单号已存在，请重新输入！");
        }
    });
});

/**
 * 修改物流编号信息
 */
$("#updateExpressNo").on('click', function (event) {
    $('#updateExpressNo_form').form('reset');
    $('#updateExpressNo_dialog').dialog('open');
    var expressNo = $("#expressNo").val();
    $("#codConfirmExpressNo").textbox("setValue", expressNo);
    $("#codConfirmExpressNo").textbox('textbox').attr('maxlength', 30);

});
$("#save_updateExpressNo").on('click', function () {
    clickCount();
    var orderSn = $("#orderSn").val();
    var codConfirmExpressNo = $("#codConfirmExpressNo").val();
    if (codConfirmExpressNo == "" || codConfirmExpressNo == null) {
        $.messager.alert('提示', "物流编号不能为空");
        return;
    }
    $.post("/pop/updateExpressNo", {orderSn: orderSn, expressNo: codConfirmExpressNo}, function (data) {
        if (data == "success") {
            $('#updateExpressNo_dialog').dialog('close');
            console.info(codConfirmExpressNo);
            window.location.href = "/pop/orderDetail?orderSn=" + orderSn;
        } else {
            $.messager.alert('提示', "该物流编号已存在，请重新输入！");
        }
    });
});
/**
 *修改订单状态：已完成---->已取消
 */
$("#cancelOrder").on('click', function () {
    var orderSn = $("#orderSn").val();
    $.messager.confirm('提示', '您确定取消吗?', function (r) {
        if (r) {
            $.post("/pop/finishToCancel", {orderSn: orderSn}, function (data) {
                if (data == "success") {
                    window.location.href = "/pop/orderDetailFromP?orderSn=" + orderSn;
                } else {
                    $.messager.alert('提示', "取消失败");
                }
            });
        }
    })

});
function goback(){
	if (fromWhere.indexOf("orderDetail") > 0 ){
		window.location.href = "/pop/orderProductList";
	}else {
        window.history.back();
	}
};