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

            $("#ddAmount").html(data.obj[0].money);
            $("#ddccv").html(data.obj[0].couponcodevalue);
            $("#ddpaid").html(data.obj[0].paidAmount);
            $("#ddAmount2").html(data.obj[0].money);
            $("#ddpoint").html(data.obj[0].points);
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
                width : '220',
                title : '网单号',
                field : 'cOrderSn',
                align : 'center',
                hidden : true
                // formatter: function(value,row,index){
                //     return '<a href="../operationArea/ProductView?cOrderSn='+row.cOrderSn+'">'+row.cOrderSn+'</a>';
                // }

            },  {
                width : '280',
                title : '网单号',
                align : 'center',
                //退货状态
                field : 'handleStatus',
                formatter: function(value,row,index){
                    var op = '<a href="../operationArea/ProductView?cOrderSn='+row.cOrderSn+'">'+row.cOrderSn+'</a>';
                    if (row.handleStatus == 1){
                        return op + '&nbsp;&nbsp;<a style="color:red;font-size:12px;" href="/operationArea/ReturnEdit?id=' + row.repairsId +'">(退货状态：审核中)</a>';
                    }
                    if (row.handleStatus == 2){
                        return op + '&nbsp;&nbsp;<a style="color:red;font-size:12px;" href="/operationArea/ReturnEdit?id=' + row.repairsId +'">(退货状态：进行中)</a>';
                    }
                    if (row.handleStatus == 3){
                        return op + '&nbsp;&nbsp;<a style="color:red;font-size:12px;" href="/operationArea/ReturnEdit?id=' + row.repairsId +'">(退货状态：受理完成)</a>';
                    }
                    if (row.handleStatus == 4){
                        return op + '&nbsp;&nbsp;<a style="color:red;font-size:12px;" href="/operationArea/ReturnEdit?id=' + row.repairsId +'">(退货状态：已完结)</a>';
                    }
                    if (row.handleStatus == 7){
                        return op + '&nbsp;&nbsp;<a style="color:red;font-size:12px;" href="/operationArea/ReturnEdit?id=' + row.repairsId +'">(退货状态：线下已退款)</a>';
                    }
                    if (row.handleStatus == 8){
                        return op + '&nbsp;&nbsp;<a style="color:red;font-size:12px;" href="/operationArea/ReturnEdit?id=' + row.repairsId +'">(退货状态：等待确认终止)</a>';
                    }
                    if (row.handleStatus == 9){
                        return op + '&nbsp;&nbsp;<a style="color:red;font-size:12px;" href="/operationArea/ReturnEdit?id=' + row.repairsId +'">(退货状态：等待HP确认拒收)</a>';
                    }
                    if (row.handleStatus == 5 || value == 6 || value == '' || value == null){
                        return op + '&nbsp;&nbsp; <span style="color:red;font-size:12px;">(退货状态：无)</span>';
                    }

                }

            },{
                width : '120',
                title : 'repairsId',
                field : 'repairsId',
                hidden : true
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
                width : '130',
                title : '网点',
                align : 'right',
                field : 'netPointName'
            }, {
                width : '100',
                title : '处理状态',
                align : 'right',
                field : 'status',
                formatter: function(value,row,index){
                    if("0"==value){
                        return "处理中";
                    }else if("1"==value){
                        return "已占用库存";
                    }else if("2"==value){
                        return "同步到HP";
                    }else if("3"==value){
                        return "同步到EC";
                    }else if("4"==value){
                        return "已分配到网点";
                    }else if("8"==value){
                        return "待出库";
                    }else if("10"==value){
                        return "待审核";
                    }else if("11"==value){
                        return "待转运入库";
                    }else if("12"==value){
                        return "待转运出库";
                    }else if("40"==value){
                        return "待发货";
                    }else if("150"==value){
                        return "网点拒单";
                    }else if("70"==value){
                        return "待交付";
                    }else if("130"==value){
                        return "完成关闭";
                    }else if("140"==value){
                        return "用户签收";
                    }else if("160"==value){
                        return "用户拒收";
                    }else if("110"==value){
                        return "取消关闭";
                    }else if("-100"==value){
                        return "未定义";
                    }
                }
            },{
                width : '100',
                title : '开票状态',
                align : 'right',
                field : 'isMakeReceipt',
                formatter: function(value,row,index){
                    if("1"==value){
                        return "未开票";
                    }else if("2"==value){
                        return "已开票";
                    }else if("3"==value){
                        return "作废发票";
                    }else if("4"==value){
                        return "冲红发票";
                    }else if("5"==value){
                        return "开票中";
                    }else if("6"==value){
                        return "开票失败";
                    }else if("9"==value){
                        return "待开票";
                    }else if("10"==value){
                        return "取消开票";
                    }else if("20"==value){
                        return "期初数据封存";
                    }
                }

            }, {
                width : '100',
                title : '日日单状态',
                align : 'right',
                field : 'pdorderstatus',
                formatter: function(value,row,index){
                    if("0"==value){
                        return "否";
                    }else if("1"==value){
                        return "是";
                    }
                }
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
                field : 'couponAmount'
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
        fitColumns: true,
        striped: true,
        pagination: false,
        rownumbers: true,
        nowrap: false,
        columns : [ [
            {
                width : '170',
                title : '操作时间',
                field : 'logTime'
            },{
                width : '170',
                title : '网单号',
                field : 'cOrderSn'
            }, {
                width : '120',
                title : '操作人',
                field : 'operator'
            }, {
                width : '400',
                title : '更改内容',
                field : 'changeLog'
            }, {
                width : '600',
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
