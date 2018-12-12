var dataGrid = null;
var datagrid_Operating_history=null;
var  datagrid_Coupon=null;
function queryDetailed(){
    $.ajax({
        url      : "/operationArea/selectPrudevtDatail?orderSn="+$("#orderSn").val(),
        type     : 'POST',
        success  : function(data) {
            var row = {
                'data': {
                    'records':
                        data.obj
                }
            }
//       	var dataGrid = $('#datagrid_orderForecastLogs').datagrid(datagridOptions_orderForecastLogs);
            dataGrid.datagrid('loadData', row);
            $("#datagrid_orderForecastLogs").datagrid('refreshRow');
        }
    })
}

$(function () {
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
                title : '网单号',
                field : 'cOrderSn'
            }, {
                width : '120',
                title : '商品名称',
                field : 'productName'
            }, {
                width : '100',
                title : '商品编号',
                field : 'sku'
            }, {
                width : '130',
                title : '库位',
                field : 'scode'

            }, {
                width : '100',
                title : '转运库位',
                field : 'tscode'

            }, {
                width : '100',
                title : '商品类型',
                align : 'right',
                field : 'productType'
            }, {
                width : '100',
                title : '网点',
                align : 'right',
                field : 'netpointid'
            }, {
                width : '100',
                title : '处理状态',
                align : 'right',
                field : 'seasonPer'
            }, {
                width : '100',
                title : '日日单状态',
                align : 'right',
                field : 'pdorderstatus'
            }, {
                width : '90',
                title : '价格',
                align : 'right',
                field : 'price'
            },{
                width : '100',
                title : '配送费',
                field : 'shippingfee'
            }, {
                width : '80',
                title : '数量',
                align : 'right',
                field : 'number'
            }, {
                width : '80',
                title : '商城优惠券',
                align : 'right',
                field : 'couponcodevalue'
            },{
                width : '80',
                title : '自营转单Id',
                align : 'right',
                field : 'customer_id'
            }, {
                width : '80',
                title : '下单立减',
                field : 'orderpromotionamount'
            }, {
                width : '80',
                title : '金额',
                field : 'money'
            },
        ] ],
        toolbar: '#datagridToolbar_orderForecastLogs'
    });
    queryDetailed();
});
var goback = function () {
    window.history.back();
};

/*
* 操作历史
* */

function Operating_history(){
    $.ajax({
        url      : "/operationArea/selectOrderOperateLogs?orderSn="+$("#orderSn").val(),
        type     : 'POST',
        success  : function(data) {
            var row = {
                'data': {
                    'records':
                    data.obj
                }
            }
//       	var dataGrid = $('#datagrid_orderForecastLogs').datagrid(datagridOptions_orderForecastLogs);
            datagrid_Operating_history.datagrid('loadData', row);
            $("#datagrid_Operating_history").datagrid('refreshRow');
        }
    })
}

$(function () {
    datagrid_Operating_history = $("#datagrid_Operating_history").datagrid({
        striped: true,
        pagination: false,
        rownumbers: true,
        columns : [ [
            {
                width : '120',
                title : '操作时间',
                field : 'logTime'
            },{
                width : '150',
                title : '网单号',
                field : 'cOrderSn'
            }, {
                width : '120',
                title : '操作人',
                field : 'operator'
            }, {
                width : '100',
                title : '更改内容',
                field : 'changeLog'
            }, {
                width : '130',
                title : '备注',
                field : 'remark'

            }
        ] ],
        toolbar: '#datagrid_Operating_history'
    });
    Operating_history();
});
/*
*
* 优惠券信息
* */
function datagrid_Coupon1(){
    $.ajax({
        url      : "/operationArea/selectCoupon?orderSn="+$("#orderSn").val(),
        type     : 'POST',
        success  : function(data) {
            var row = {
                'data': {
                    'records':
                    data.obj
                }
            }
//       	var dataGrid = $('#datagrid_orderForecastLogs').datagrid(datagridOptions_orderForecastLogs);
            datagrid_Coupon.datagrid('loadData', row);
            $("#datagrid_Coupon").datagrid('refreshRow');
        }
    })
}

$(function () {
    datagrid_Coupon = $("#datagrid_Coupon").datagrid({
        striped: true,
        pagination: false,
        rownumbers: true,
        columns : [ [
            {
                width : '320',
                title : '商品名称',
                field : 'productName'
            },{
                width : '250',
                title : '优惠券金额',
                field : 'couponCodeValue'
            }, {
                width : '220',
                title : '优惠券文件编号',
                field : 'couponCode'
            }, {
                width : '100',
                title : '备注',
                field : 'no'
            }
        ] ],
        toolbar: '#datagrid_Coupon'
    });
    datagrid_Coupon1();
});
