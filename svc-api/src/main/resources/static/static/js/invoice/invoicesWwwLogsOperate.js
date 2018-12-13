
var datagrid = null;  //显示界面标签
var queryParameters; //查询参数

$(function () {
    datagrid = {
        fit: true,
        fitColumns: true,
        toolbar: '#datagridToolbar_invoice3wLogs',
        striped: true,
        singleSelect: true,
        pagination: true,
        columns: [[
            // {title: "操作", field: "field0", sortable: false, formatter: go_detail},
            {title: "序号", field: "id", hidden: true},
            {title: "订单号", field: "orderSn", sortable: false},
            {title: "网单编号", field: "cOrderSn", sortable: false},
            {title: "处理状态", field: "success",sortable: false},
            {title: "订单来源", field: "source", sortable: false},
            {title: "添加时间", field: "addTime", sortable: false},
            {title: "处理时间", field: "processTime", sortable: false},
            {title: "操作", field: "caozuo"}
            ]],
        pageSize: 50,
        pageList: [50,100,200],
        rownumbers: true
    }

    $('#datagrid_invoice3wLogs').datagrid(datagrid);
});

//查询
$('#searchBtn_invoice3wLogs').click(function () {
    //来源订单号
    var sourceSn = $("#sourceSn").val();
    //处理状态
    var success = $("#success").combobox("getValue");
    //订单来源
    var source = $("#source").combobox("getValue");

    if($('#addTimeMin').datetimebox('getValue') && $('#addTimeMax').datetimebox('getValue')){
        if($('#addTimeMin').datetimebox('getValue')>$('#addTimeMax').datetimebox('getValue')){
            $.messager.alert('错误','时间区间不正确','error');
            return false;
        }
    }

    queryParameters = {
        sourceSn:$("#sourceSn").val(),
        cOrderSn:$("#cOrderSn").val(),
        success:$("#success").combobox("getValue"),
        source:$("#source").combobox("getValue"),
        addTimeMin:$("#addTimeMin").datetimebox('getValue'),
        addTimeMax:$("#addTimeMax").datetimebox('getValue')
    };

    datagrid = $('#datagrid_invoice3wLogs').datagrid({
        url: "/invoice/findInvoicesWwwLogs",
        fit: true,
        pagination: true,
        singleSelect: true,
        loadMsg:"正在努力加载中",
        idField : 'orderSn',
        pageSize: 50,
        pageList: [50,100,200],
        toolbar: '#datagridToolbar_invoice3wLogs',
        nowrap: true,
        rownumbers: true,
        queryParams:queryParameters,
        columns : [ [
            {
                field: 'id',
                title: '序号',
                align: 'center',
                hidden:true
            },{
                
                title : '订单号',
                align : 'center',
                field : 'orderSn',
                formatter: function(value,row,index){
                    if(value != null){
                        var returnOrderProduct = "<a href='javascript:void(0);' onclick='addTab3(" + '"' + row.orderSn + '"'+ ");return false;'>"+row.orderSn+"</a><br/>";
                        return returnOrderProduct;
                    }

                }
            },
            {
                title: '网单编号 ',
                field: 'cOrderSn',
                align: 'center',
                formatter: function(value,row,index){
                    if(value != null){
                        var returnOrderProduct = "<a href='javascript:void(0);' onclick='addTab2(" + '"' + row.cOrderSn + '"'+ ");return false;'>"+row.cOrderSn+"</a><br/>";
                        return returnOrderProduct;
                    }
                }
            },
            {
                
                title : '处理状态',
                field : 'success',
                align : 'center',
                formatter: function(value,row,index){
                    var dispValue="";
                    if(row.success=='0'){
                        dispValue="待处理";
                    }
                    if (row.success=='1'){
                        dispValue="已处理";
                    }
                    if (row.success=='2'){
                        dispValue="无需处理";
                    }
                    return dispValue ;
                }
            }, {
                
                title : '订单来源',
                field : 'source',
                align : 'center',
                formatter: function(value,row,index){
                    var dispValue="";
                    if(row.source=='TBSC'){
                        dispValue="淘宝海尔官方旗舰店";
                    }
                    if (row.source=='TOPDHSC'){
                        dispValue="海尔生活家电旗舰店";
                    }
                    if (row.source=='TOPFENXIAO'){
                        dispValue="淘宝海尔官方旗舰店分销平台";
                    }
                    if (row.source=='TOPFENXIAODHSC'){
                        dispValue="淘宝海尔生活家电旗舰店分销平台";
                    }
                    if (row.source=='TOPBUYBANG'){
                        dispValue="淘宝海尔买帮专卖店";
                    }
                    if (row.source=='TOPBOILER'){
                        dispValue="淘宝海尔热水器专卖店";
                    }
                    if (row.source=='TOPSHJD'){
                        dispValue="淘宝海尔生活电器专卖店";
                    }
                    if (row.source=='TOPMOBILE'){
                        dispValue="淘宝海尔手机专卖店";
                    }
                    if (row.source=='TONGSHUAI'){
                        dispValue="统帅日日顺乐家专卖店";
                    }
                    if (row.source=='TONGSHUAIFX'){
                        dispValue="统帅日日顺分销平台";
                    }
                    if (row.source=='TOPXB'){
                        dispValue="海尔新宝旗舰店";
                    }
                    if (row.source=='TOPFENXIAOXB'){
                        dispValue="淘宝海尔新宝旗舰店分销平台";
                    }
                    if (row.source=='WASHER'){
                        dispValue="海尔洗衣机官方旗舰店";
                    }
                    if (row.source=='FRIDGE'){
                        dispValue="海尔冰冷官方旗舰店";
                    }
                    if (row.source=='AIR'){
                        dispValue="淘宝空调旗舰店";
                    }
                    if (row.source=='TMMKFX'){
                        dispValue="天猫模卡分销";
                    }
                    if (row.source=='GQGYS'){
                        dispValue="生态授权店";
                    }
                    if (row.source=='TBCT'){
                        dispValue="淘宝村淘";
                    }
                    if (row.source=='TBQYG'){
                        dispValue="天猫企业购";
                    }
                    if (row.source=='TMST'){
                        dispValue="天猫生态";
                    }
                    if (row.source=='FLW'){
                        dispValue="商城PC-返利网";
                    }
                    if (row.source=='YHDQWZY'){
                        dispValue="电商平台-1号店全网自营";
                    }
                    if (row.source=='TMKSD'){
                        dispValue="卡萨帝官方旗舰店";
                    }
                    if (row.source=='TMTV'){
                        dispValue="天猫海尔电视";
                    }
                    if (row.source=='TBCFDD'){
                        dispValue="淘宝海尔厨房大电旗舰店";
                    }
                    if (row.source=='TBXCR'){
                        dispValue="天猫小超人旗舰店";
                    }
                    if (row.source=='TMMK'){
                        dispValue="mooka模卡官方旗舰店";
                    }
                    return dispValue ;
                }
            },
            {
                title : '添加时间',
                field : 'addTime',
                align : 'center'
            },{
                title : '处理时间',
                field : 'processTime',
                align : 'center'
            },{
                
                title : '操作',
                field : 'caozuo',
                align : 'center',
                formatter:function(value,rowData,rowIndex){
                    return "<a href='javascript:void(0);' onclick='modifyMemberInvoices(" + '"' + 1 + '", "' + rowData.orderId + '", "' + rowData.orderSn + '"' + ");return false;'>开增票</a> "
                        + "<a href='javascript:void(0);' onclick='invoiceOperate(" + '"' + 2 + '", "' + rowData.orderId + '", "' + rowData.orderProductId + '"' + ");return false;'>开电子票</a> "
                        + "<a href='javascript:void(0);' onclick='invoiceOperate(" + '"' + 3 + '", "' + rowData.orderId + '", "' + rowData.orderProductId + '"' + ");return false;'>不开票</a>";
                }
            }
        ] ]
    });
});

function addTab2(corderSn) {
    window.top.addTab("网单详情和订单详情", "/operationArea/ProductView?cOrderSn=" + corderSn, true);
}
function addTab3(orderSn) {
    window.top.addTab("订单查询网单", "/operationArea/orderNumberSelect?orderSn=" + orderSn, true);
}
//导出
$('#exportBtn_invoice3wLogs').click(function (){
    if(!datagrid){
        $.messager.alert('提示','请查询！','info');
        return;
    }
    $.messager.confirm('确认','确定要导出吗？', function(r){
        if (r){
            $('#exportData').val(JSON.stringify(queryParameters));
            $('#paramForm_dmmtlPbcsMtlMeasure').attr("action", '/invoice/exportInvoicesWwwLogs');
            $('#paramForm_dmmtlPbcsMtlMeasure').submit();
        }
    });
});

//人工处理发票操作
function invoiceOperate(flag, orderId, orderProductId){
    $("#handleResultDetail").attr("style", "display:none;padding:5px;width:800px;height:400px;");
    jQuery.ajax({
        url: "/invoice/invoicesWwwLogsOperate",
        data:{
            "flag": flag,
            "orderId": orderId,
            "orderProductId": orderProductId
        },
        type: "GET",
        success: function (data) {
            $("#handleResultDetail").html(data.message);
            $("#handleResultDetail").show();
            $("#handleResultDetail").dialog({
                collapsible: true,
                minimizable: false,
                maximizable: false
            });
        }
    });
}
//修改发票信息
function modifyMemberInvoices(modifyFlg, id, orderSn){
    window.top.addTab("编辑或查看发票信息", "/invoice/modifyMemberInvoicesBy3wInvoiceLog?modifyFlg="+modifyFlg+"&id="+id+"&orderSn="+orderSn, true);
}

/*function modifyMemberInvoices(modifyFlg, id, orderSn){
	$("#memberInvoiceDetail").attr("style", "display:none;padding:5px;width:800px;height:400px;");
	if (orderSn == undefined){
		orderSn = "";
	}
	jQuery.ajax({
         url: "/invoice/modifyMemberInvoicesBy3wInvoiceLog",
         data:{
            "modifyFlg": modifyFlg,
            "id": id,
            "orderSn": orderSn
         },
         type: "GET",
         success: function (data) {
            $("#memberInvoiceDetail").html(data);
            $("#memberInvoiceDetail").show();
            $("#memberInvoiceDetail").dialog({
                collapsible: true,
                minimizable: false,
                maximizable: false
            });
        }
    });
}*/

